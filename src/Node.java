package src;
import java.util.Stack;

public class Node {
	private State state; //the state that corresponds to the node
    private Node parent; //to trace
    private int path_cost; //or double??
    private Action action; //the action that was used to reach this node
    
    public Node(State state) {
    	this.state = state;
    	this.parent = null;
    	this.action = null;
    	this.path_cost = 0;
    }

    public Node(State state, int stepCost, Node parent, Action action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.path_cost = parent.path_cost + stepCost;
    }

    public State getState() {
        return this.state;
    }

    public Node getParent() {
        return this.parent;
    }
    
    public boolean isRootNode() {
        return parent == null;
    }
    
    public Stack<Action> getPathFromRoot() {
    	 Stack<Action> path = new Stack<Action>();
         Node currNode = this;
         path.push(Action.TURN_OFF);
         while (!currNode.isRootNode()) {
             path.push(currNode.action);
             currNode = currNode.parent;
         }
         path.push(Action.TURN_ON);
         return path;
    }

    public Action getAction() { 
    	return this.action; 
    }

    public int getPathCost() { 
    	return this.path_cost; 
    }
}
