package src;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Node {
    private State state; //the state that corresponds to the node
    private Node parent; //to trace
    private int pathCost; //or double??
    private Action action; //the action that was used to reach this node
    
    public Node(State state) {
        this.state = state;
        this.pathCost = 0;
        this.action = null;
        this.parent = null;
    }
    public Node(State state, Node parent, Action action, int stepCost) {
        this(state);
        this.parent = parent;
        this.action = action;
        this.pathCost = parent.pathCost + stepCost;
    }
    public State getState() {
        return state;
    }
    
    public Action getAction() {
        return action;
    }
    
    public int getPathCost() {
        return pathCost;
    }
    
    public boolean isRootNode() {
        return parent == null;
    }
    
    public Stack<Action> getPathFromRoot() {
        Stack<Action> path = new Stack<Action>();
        Node currNode = this;
        while (!currNode.isRootNode()) {
            System.out.println("get path from root: ");
            System.out.println(currNode.toString());
            path.push(currNode.getAction());
            currNode = currNode.parent;
        }
        path.push(currNode.getAction());
        return path;
    }
    
    public String toString() {
        return "[parent:" + parent + ", action:" + action + ", state:"
        + getState() + ", pathCost:" + pathCost + "]";
    }
    
    public String pathToString() {
        String s = "";
        Stack<Action> actions = getPathFromRoot();
        for (Action action : actions) {
            System.out.println("Action : " + action);
            //System.out.println("State  : " + action.getState());
        }
        return s;
    }
}

