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
    private int initial_capacity = 11;

    public Astar(Environment env){
    	e = env;
    	int initial_estimated_cost = heuristicFunction(e.getInitState());
    	initial_node = new Node(e.getInitState(), initial_estimated_cost);
    	this.stats = new Statistics();
    	frontier = new PriorityQueue<Node>(initial_capacity, new Comparator<Node>() {
    	    @Override
    	    public int compare(Node n1, Node n2) {
    	        return ((Integer)n1.getAStarTotalCost()).compareTo(n2.getAStarTotalCost()); //�yrfti a� vera a�eins ��ruv�si compartor, taka lika inn estimated cost
    	                                                                        //spurning um a� geyma total cost (lika � node), sem er estimated+path
    	    }});
    }

    public Stack <Action> findPath() {
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
            		int estimated_cost_to_goal = heuristicFunction(newState);
                    Node childNode = new Node(newState, step_cost, estimated_cost_to_goal, n, a); //lata herna inn rettan kostnad i stadinn fyrir 0
                    //�yrftum moulega ad lata node like taka inn estimated cost
                    stats.incrementExpansions();
                    frontier.add(childNode);
            	}
                explored.add(s);
            }
        }
        System.out.println("No goal node found");
        return failure();
    }

    int heuristicFunction(State s) { //the shit
    	return s.getDirt().size(); //hehe, thetta fall ekki malid, en her kemur sweet formula
    }
    
    public Stack<Action> failure() {
    	Stack<Action> temp = new Stack<Action>();
        temp.add(Action.TURN_OFF);
        temp.add(Action.TURN_ON);
        return temp;
    }
}
