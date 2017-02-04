package src;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class UniformCost {
	// mjog svipad bfs
	// tharf ad athuga hvort thad er odyrara ad slokkava a ser en ad saekja allt dirt
	private Node initial_node;
	private PriorityQueue<Node> frontier;
    private Set<State> explored = new HashSet<State>();
    private Environment e;
    private Statistics stats;
    private int initial_capacity = 11;

    public UniformCost(Environment env){
    	e = env;
    	initial_node = new Node(e.getInitState(), 0);
    	this.stats = new Statistics();
    	frontier = new PriorityQueue<Node>(initial_capacity, new Comparator<Node>() {
    	    @Override
    	    public int compare(Node n1, Node n2) {
    	        return ((Integer)n1.getPathCost()).compareTo(n2.getPathCost());
    	    }});
    }

    public Stack <Action> findPath() {
        /*
    	if (initial_node.getState().isGoalState(e.home_pos)) {
            System.out.println("First was goal");
        	return initial_node.getPathFromRoot();
        }
        */
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.remove();
            State s = n.getState();
            if (s.isGoalState(e.getHomePos())) {
            	System.out.println("Goal path cost: " + n.getPathCost());
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
                    Node childNode = new Node(newState, step_cost, 0, n, a); //lata herna inn rettan kostnad i stadinn fyrir 0
                    stats.incrementExpansions();
                    frontier.add(childNode);
            	}
                explored.add(s);
            }
        }
        System.out.println("No goal node found");
        return failure();
    }
    
    public Stack<Action> failure() {
    	Stack<Action> temp = new Stack<Action>();
        temp.add(Action.TURN_OFF);
        temp.add(Action.TURN_ON);
        return temp;
    }
	
}
