package src;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS {
	public Environment e;
    public Node initial_node;
    private Set<State> explored = new HashSet<State>();
    private Stack<Node> frontier;
    
    DFS(Environment env) {
    	this.e = env;
    	this.frontier = new Stack<Node>();
        this.initial_node = new Node(e.initial_state);
    }
    /*
    
    Stack<Action> findPath() {
    	Node final_node = findPath(initial_node);
    	return final_node.getPathFromRoot();
    }
    
    public Node findPath(Node n) {
    	State curr_state = n.getState();
    	
    	if(curr_state.isGoalState(e.home_pos)) {
    		return n;
    	}
    	
    	if (!explored.contains(curr_state)) {
    		explored.add(curr_state);
	    	ArrayList<Action> actions = curr_state.legalActions(e); 
	    	//System.out.println("list of actions");
	    	//System.out.println(actions.toString());
	    	for(Action a: actions) {
	    		//System.out.println(a.toString());
	    		State new_state = curr_state.successorState(a);
	    		//System.out.println(new_state.toString());
	    		Node child_node = new Node(new_state, 0, n, a);
	    		Node goal_node = findPath(child_node);
	    		if (goal_node != null)
	    			return goal_node;
	    	}
    	}
    	//System.out.println("repeated state");
    	return null;
    }    
    */
    public Stack <Action> findPath() {

        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.pop();
            State s = n.getState();
            if (!explored.contains(s)) {
                ArrayList<Action> actions = s.legalActions(e);
                //System.out.println("list of actions");
            	//System.out.println(actions.toString());
            	for (Action a : actions) {
            		State newState = s.successorState(a);
                    Node childNode = new Node(newState, 0, n, a);
                    if (newState.isGoalState(e.home_pos)) {
                    	System.out.println("Found goal State. " + newState);
                    	return childNode.getPathFromRoot();
                    }
                    frontier.push(childNode);
            	}
                explored.add(s);
            }
            System.out.println("Frontier size: " + frontier.size());
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
