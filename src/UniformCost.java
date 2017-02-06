package src;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class UniformCost {
	private Node initial_node;
	private PriorityQueue<Node> frontier;
    private Set<State> explored;
    private Environment e;
    private Statistics stats;
    private int initial_capacity = 11;

    public UniformCost(Environment env){
    	this.e = env;
    	this.initial_node = new Node(e.getInitState(), 0);
    	this.explored = new HashSet<State>();
    	this.stats = new Statistics();
    	this.frontier = new PriorityQueue<Node>(initial_capacity, new Comparator<Node>() {
    	    @Override
    	    public int compare(Node n1, Node n2) {
    	        return ((Integer)n1.getPathCost()).compareTo(n2.getPathCost());
    	    }});
    }

    public Stack <Action> findPath() {
    	//if there is no dirt this would accelerate the process
    	/*if (initial_node.getState().isGoalState(e.getHomePos())) {
            System.out.println("First was goal");
        	return initial_node.getPathFromRoot(stats);
        }*/
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
