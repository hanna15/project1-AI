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
    private Set<State> explored = new HashSet<State>();
    private Environment e;
    private Statistics stats;

    public Astar(Environment env){
    	e = env;
    	initial_node = new Node(e.initial_state);
    	this.stats = new Statistics();
    	frontier = new PriorityQueue<Node>(new Comparator<Node>() {
    	    @Override
    	    public int compare(Node n1, Node n2) {
    	        return ((Integer)n1.getPathCost()).compareTo(n2.getPathCost()); //þyrfti að vera aðeins öðruvísi compartor, taka lika inn estimated cost
    	                                                                        //spurning um að geyma total cost (lika í node), sem er estimated+path
    	    }});
    }

    public Stack <Action> findPath() {
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.remove();
            State s = n.getState();
            if (s.isGoalState(e.home_pos)) {
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
            		int step_cost = s.calculateCost(a, e.home_pos);
            		int estimated_cost_to_end = heuristicFunction();
                    Node childNode = new Node(newState, step_cost, n, a); //lata herna inn rettan kostnad i stadinn fyrir 0
                    //þyrftum moulega ad lata node like taka inn estimated cost
                    stats.incrementExpansions();
                    frontier.add(childNode);
            	}
                explored.add(s);
            }
        }
        System.out.println("No goal node found");
        return failure();
    }
    
    int heuristicFunction() { //the shit
    	return 0;
    }
    
    public Stack<Action> failure() {
    	Stack<Action> temp = new Stack<Action>();
        temp.add(Action.TURN_OFF);
        temp.add(Action.TURN_ON);
        return temp;
    }
}
