package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS {
    private Node initial_node;
    private Queue<Node> frontier;
    private HashMap<State, Node> explored;
    private Environment e;
    private Statistics stats;

    public BFS(Environment env){
    	this.e = env;
    	this.initial_node = new Node(e.getInitState(), 0);
    	this.frontier = new LinkedList<Node>();
    	this.explored = new HashMap<State, Node>();
    	this.stats = new Statistics();
    }

    public Stack <Action> findPath() {
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.remove();
            State s = n.getState();
            if (explored.containsKey(s)) {
            	stats.incrementNotVisited();
            }
            else {
                ArrayList<Action> actions = s.legalActions(e);
            	for (Action a : actions) {
            		State newState = s.successorState(a);
                    Node childNode = new Node(newState, 0, 0, n, a);
                    stats.incrementExpansions();
                    if (newState.isGoalState(e.getHomePos())) {
                    	return childNode.getPathFromRoot(stats);
                    }
                    frontier.add(childNode);
            	}
                explored.put(s,n);
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
