package ath;
import java.util.Stack;

public class Node {
	private State state; //the state that corresponds to the node
    private Node parent; //to trace
    private int pathCost; //or double??
    private Action action; //the action that was used to reach this node

    public Node(State state, int stepCost, Node parent, Action action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = stepCost;
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
             System.out.println("get path from root: ");
             System.out.println(currNode.toString());
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
    	return this.pathCost; 
    }
}
