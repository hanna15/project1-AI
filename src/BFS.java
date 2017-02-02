package src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BFS {
    private Node initial_node;
    private Queue<Node> frontier;
    private HashMap<State, Node> explored = new HashMap<State, Node>();
    private Environment e;
    private Statistics stats;

    public BFS(Environment env){
    	e = env;
    	initial_node = new Node(e.initial_state);
    	frontier = new LinkedList<Node>();
    	stats = new Statistics();
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
            if (!explored.containsKey(s)) {
                ArrayList<Action> actions = s.legalActions(e);
                //System.out.println("list of actions");
            	//System.out.println(actions.toString());
            	for (Action a : actions) {
            		State newState = s.successorState(a);
                    Node childNode = new Node(newState, 0, n, a);
                    stats.incrementExpansions();
                    if (newState.isGoalState(e.home_pos)) {
                    	System.out.println("Found goal State. " + newState);
                    	return childNode.getPathFromRoot(stats);
                    }
                    frontier.add(childNode);
            	}
                explored.put(s, n);
            }
            //System.out.println("Frontier size: " + frontier.size());
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
