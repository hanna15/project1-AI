package src;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class UniformCost {
	// mjog svipad bfs
	// tharf ad athuga hvort thad er odyrara ad slokkava a ser en ad saekja allt dirt
	private Node initial_node;
	private PriorityQueue<Node> frontier; //þurfum að skilgr comparator
    private Set<State> explored = new HashSet<State>();
    private Environment e;

    public UniformCost(Environment env){
    	e = env;
    	initial_node = new Node(e.initial_state, 0, null, null);
    	frontier = new PriorityQueue<Node>(); //skilgr eigin comparator í sviganum
        //System.out.println("printing env info:");
        //System.out.println("init state " + e.initial_state);
        //System.out.println("x sixe " + e.sizeX);
        //System.out.println("pos of dirts: ");
        //for (Position p : e.dirt) {
        //	System.out.print(p + " ");
        //}
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
            if (!explored.contains(s)) {
                ArrayList<Action> actions = s.legalActions(e);
                System.out.println("list of actions");
            	System.out.println(actions.toString());
            	for (Action a : actions) {
            		State newState = s.successorState(a);
                    Node childNode = new Node(newState, 0, n, a); //lata herna inn rettan kostnad i stadinn fyrir 0
                    if (newState.isGoalState(e.home_pos, e.home_orient)) {
                    	System.out.println("Found goal State. " + newState);
                    	return childNode.getPathFromRoot();
                    }
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
