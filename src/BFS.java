package src;
import java.lang.*;
import java.util.*;

public class BFS {
    public Environment env;
    public Node initial_node;
    public Queue<Node> frontier;
    public Set<Node> explored;
    public int num_dirt;
    Stack<Action> bfsRun;
    
    BFS(Environment e) {
        this.env = e;
        initial_node = new Node(env.initial_state);
        frontier = new LinkedList<Node>();
        explored = new HashSet<Node>();
        num_dirt = env.num_dirt;
        System.out.println("BFssssssssssS num_dirt " + num_dirt);
        bfsRun = runBFS();
        if (bfsRun != null) {
            System.out.println("JAY!");
        }
        else {
            System.out.println("Failure.");
        }
    }
    public Stack<Action> runBFS() {
        
        // TODO: kannski hafa isGoalState i Environment
        if (isGoalState(initial_node.getState())) {
            System.out.println("First was goal");
        	return initial_node.getPathFromRoot();
        }
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node parent = frontier.remove();
            explored.add(parent);
            
            for(Action a : env.legalActions(parent.getState())) {
            	System.out.println("current legal action of the state " + parent.getState().toString() + "is : " + a);
                if (a == Action.SUCK) {
                	System.out.println("Action was suck");
                    num_dirt--;
                    env.updateDirt(parent.getState().position);
                    System.out.println();
                    for(int i = 0; i < env.dirt.length; i++) {
                        for (int j = 0; j < env.dirt[0].length; j++) {
                            System.out.print( " [" + env.dirt[i][j] + "] ");
                        }
                        System.out.println("");
                    }
                }
            	State child_state = parent.getState().successorState(a, env);
                Node child = new Node(child_state, parent, a, a.getCost(a));
                System.out.println("The successor state is " + child_state.toString());
                
                if (!explored.contains(child) && !frontier.contains(child)) {
                	System.out.println("Check if goal state");
                    if (isGoalState(child_state)) {
                    	System.out.println("Found goal state, it is: " + child.toString());
                        return child.getPathFromRoot();
                    }
                    frontier.add(child);
                }
                System.out.println("FOR");
            }
            System.out.println("WHILE: " + num_dirt);
            
        }
        return null;
    }
    
    public boolean isGoalState(State s) {
        //System.out.println(position + " vs " + e.home_pos + " " + orientation + " vs " + e.home_orient + " "  + e.num_dirt);
        if (num_dirt <= 0) {//s.position.equals(env.home_pos) && s.orientation.equals(env.home_orient) && num_dirt <= 0
            //turnOff();
            return true;
        }
        return false;
    }
    
    public Action turnOff() {
        return Action.TURN_OFF;
    }
}
