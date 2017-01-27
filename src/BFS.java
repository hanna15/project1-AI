package src;
import java.lang.*;
import java.util.*;

public class BFS {
	public Environment env;
	public Node initial_node; 
	public Deque<Node> frontier;
	public Set<State> explored;
	
	BFS(Environment e) {
		this.env = e;
		initial_node = new Node(env.initial_state);
		frontier = new LinkedList<Node>();
		explored = new HashSet<State>();
		if (runBFS()) {
			System.out.println("JAY!");
		}
		else {
			System.out.println("Failure.");
		}
	}
	public boolean runBFS() {
		
		// TODO: kannski hafa isGoalState i Environment
		if (initial_node.getState().isGoalState(env)) {
			return true;
		}
		frontier.add(initial_node);
		while (!frontier.isEmpty()) {
			Node parent = frontier.pop();
			explored.add(parent.getState());
			
			for(Action a : parent.getState().legalActions(env)) {
				State child_state = parent.getState().successorState(a, env);
				Node child = new Node(child_state, parent, a, a.getCost(a));
				
				if (!explored.contains(child_state) && !frontier.contains(child)) {
					if (child_state.isGoalState(env)) {
						return true;
					}
					frontier.add(child);
				}
				System.out.println("FOR");
			}
			System.out.println("WHILE: " + env.num_dirt);
			
		}
		return false;
	}
}
