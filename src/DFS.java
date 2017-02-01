package src;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS {
	public Environment e;
    public Node initial_node;
    private Set<State> explored = new HashSet<State>();
    
    DFS(Environment env) {
    	this.e = env;
        this.initial_node = new Node(e.initial_state, 0, null, null);
    }
    
    Stack<Action> findPath() {
    	Node final_node = findPath(initial_node);
    	return final_node.getPathFromRoot();
    }
    
    public Node findPath(Node n) {
    	State curr_state = n.getState();
    	
    	if(curr_state.isGoalState(e.home_pos, e.home_orient)) {
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
}
