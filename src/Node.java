package src;
import java.util.Stack;

public class Node {
	private State state; //the state that corresponds to the node
    private Node parent; //to be able to trace the path
    private int path_cost; //for UCS and ASTAR
    private Action action; //the action that led to this node
    private int estimated_cost_to_goal; //used in ASTAR only
    
    //for initializing the first node of each search
    //for searches other than A*, the estimated cost sent in is always 0
    public Node(State state, int estimated_cost) { 
    	this.state = state;
    	this.parent = null;
    	this.action = null;
    	this.path_cost = 0;
    	this.estimated_cost_to_goal = estimated_cost;
    }
    
    //initializing nodes 
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
    
    //Astar uses f(n) = g(n) [path cost] + h(n) [heuristics]
    public int getAStarTotalCost() {
    	return estimated_cost_to_goal + path_cost;
    }

    public Node getParent() {
        return this.parent;
    }
    
    public boolean isRootNode() {
        return parent == null;
    }
    
    //This makes up the action list, received by the remoteAgent
    public Stack<Action> getPathFromRoot(Statistics stats) {
    	 Stack<Action> path = new Stack<Action>();
         Node currNode = this;
         path.push(Action.TURN_OFF); //manually adding turn_off as the last action
         while (!currNode.isRootNode()) {
             path.push(currNode.action);
             currNode = currNode.parent;
         }
         path.push(Action.TURN_ON); //manually adding turn_on as the first action
         stats.setCost(path.size());
         stats.printStatistics();  //for measuring time and other statistics
         return path;
    }

    public Action getAction() { 
    	return this.action; 
    }

    public int getPathCost() { 
    	return this.path_cost; 
    }
}
