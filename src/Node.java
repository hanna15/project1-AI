package src;

import java.util.ArrayList;
import java.util.Queue;

public class Node {
	State state; //the state that corresponds to the node
	Node parent; //to trace
	int pathCost; //or double??
	Action action; //the action that was used to reach this node
	
	public Node(State state) {
        this.state = state;
        this.pathCost = 0;
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

    public ArrayList<Node> getPathFromRoot() {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currNode = this;
        while (!currNode.isRootNode()) {
            path.add(0, currNode);
            currNode = currNode.parent;
        }
        path.add(0, currNode);
        return path;
    }

    public String toString() {
        return "[paren:" + parent + ", action:" + action + ", state:"
                + getState() + ", pathCost:" + pathCost + "]";
    }

    public String pathToString() {
        String s = "";
        ArrayList<Node> nodes = getPathFromRoot();
        for (Node node : nodes) {
            System.out.println("Action : " + node.getAction());
            System.out.println("State  : " + node.getState());
        }
        return s;
    }
}

