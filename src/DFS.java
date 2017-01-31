package src;


import java.util.ArrayList;
import java.util.Stack;

public class DFS {
	public Environment e;
    public Node initial_node;
    
    DFS(Environment env) {
    	this.e = env;
        initial_node = new Node(e.initial_state, 0, null, null);
    }
    
    Stack <Action> findPath() {
    	Node final_node = findPath(initial_node);
    	return final_node.getPathFromRoot();
    }
    
    public Node findPath(Node n) {
    	State curr_state = n.getState();
    	
    	if(curr_state.isGoalState(e.home_pos, e.home_orient)) {
    		return n;
    	}
    	// Node goal_node = null; // ath
    	ArrayList<Action> actions = curr_state.legalActions(e);
    	for(Action a: actions) {
    		// child <- child-node(problem, node, action)
    		State new_state = curr_state.successorState(a);
    		Node child_node = new Node(new_state, 0, n, a);
    		Node goal_node = findPath(child_node);  // stack overflow
    		if (goal_node != null)
    			return goal_node;
    	}
    	return null;
    }    
}
