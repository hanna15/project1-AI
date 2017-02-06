package src;
import java.util.Stack;

public class Node {
	private State state; // the state that corresponds to the node
    private Node parent; // to trace
    private int path_cost;
    private Action action; // the action that was used to reach this node
    private int estimated_cost_to_goal;
    
    public Node(State state, int estimated_cost) {
    	this.state = state;
    	this.parent = null;
    	this.action = null;
    	this.path_cost = 0;
    	this.estimated_cost_to_goal = estimated_cost;
    }

    public Node(State state, int stepCost, int estimated_cost_to_goal, Node parent, Action action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.path_cost = parent.path_cost + stepCost;
        this.estimated_cost_to_goal = estimated_cost_to_goal;
    }

    public State getState() {
        return this.state;
    }
    
    public int getAStarTotalCost() {
    	return estimated_cost_to_goal + path_cost;
    }

    public Node getParent() {
        return this.parent;
    }
    
    public boolean isRootNode() {
        return parent == null;
    }
    
    public Stack<Action> getPathFromRoot(Statistics stats) {
    	 Stack<Action> path = new Stack<Action>();
         Node currNode = this;
         path.push(Action.TURN_OFF);
         while (!currNode.isRootNode()) {
             path.push(currNode.action);
             currNode = currNode.parent;
         }
         path.push(Action.TURN_ON);
         stats.setCost(path.size());
         stats.printStatistics();
         return path;
    }

    public Action getAction() { 
    	return this.action; 
    }

    public int getPathCost() { 
    	return this.path_cost; 
    }
}
