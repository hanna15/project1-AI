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
    private Statistics stats;
    
    DFS(Environment env) {
    	this.e = env;
    	this.frontier = new Stack<Node>();
        this.initial_node = new Node(e.getInitState(), 0);
        this.stats = new Statistics();
    }
    public Stack <Action> findPath() {

        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.pop();
            State s = n.getState();
            if(explored.contains(s)) {
            	stats.incrementNotVisited();
            }
            if (!explored.contains(s)) {
                ArrayList<Action> actions = s.legalActions(e);
            	for (Action a : actions) {
            		State newState = s.successorState(a);
                    Node childNode = new Node(newState, 0, 0, n, a);
                    stats.incrementExpansions();
                    if (newState.isGoalState(e.getHomePos())) {
                    	System.out.println("Found goal State. " + newState);
                    	return childNode.getPathFromRoot(stats);
                    }
                    frontier.push(childNode);
            	}
                explored.add(s);
            }
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
