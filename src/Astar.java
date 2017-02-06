package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;


public class Astar {
	private Node initial_node;
	private PriorityQueue<Node> frontier;
    private Set<State> explored;
    private Environment e;
    private Statistics stats;
    private int initial_capacity = 11;
    
    public Astar(Environment env){
    	this.e = env;
    	int initial_estimated_cost = heuristicFunction(e.getInitState());
    	this.initial_node = new Node(e.getInitState(), initial_estimated_cost);
    	this.stats = new Statistics();
    	this.explored = new HashSet<State>();
    	this.frontier = new PriorityQueue<Node>(initial_capacity, new Comparator<Node>() {
    	    @Override
    	    public int compare(Node n1, Node n2) {
    	        return ((Integer)n1.getAStarTotalCost()).compareTo(n2.getAStarTotalCost()); 
    	    }});
    }

    public Stack <Action> findPath() {
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.remove();
            State s = n.getState();
            if (s.isGoalState(e.getHomePos())) {
                return n.getPathFromRoot(stats);
            }
            if(explored.contains(s)) {
            	stats.incrementNotVisited();
            }
            else {
                ArrayList<Action> actions = s.legalActions(e);
            	for (Action a : actions) {
            		State newState = s.successorState(a);
            		int step_cost = s.calculateCost(a, e.getHomePos());
            		int estimated_cost_to_goal = heuristicFunction(newState);
                    Node childNode = new Node(newState, step_cost, estimated_cost_to_goal, n, a); 
                    stats.incrementExpansions();
                    frontier.add(childNode);
            	}
               explored.add(s);
            }
        }
        System.out.println("No goal node found");
        return failure();
    }

    int heuristicFunction(State s) {
    	int furthestDirt = 0;
    	int currentDist = 0;
        for (Position p : s.getDirt()) {
       	 furthestDirt = Math.max(furthestDirt, distanceTo(p, e.getHomePos()));
       	 currentDist = Math.max(currentDist, distanceTo(p, s.getPosition()));
        }
        return s.getDirt().size() + furthestDirt + currentDist;
    }
    
    public int distanceTo(Position p1, Position p2) {
    	int x = (p1.getX() - p2.getX());
    	int y = (p1.getY() - p2.getY());
    	int res = Math.abs(x) + Math.abs(y);
    	return res;
    }
    
    public Stack<Action> failure() {
    	Stack<Action> temp = new Stack<Action>();
        temp.add(Action.TURN_OFF);
        temp.add(Action.TURN_ON);
        return temp;
    }
    
}
