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
    	//Stack<Action> actionList = new Stack<Action>();
        
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
            	State child_state = parent.getState().successorState(a, env);
            	
            	System.out.println("current legal action of the state " + parent.getState().toString() + "is : " + a);
                
            	if (a == Action.SUCK) {
                	System.out.println("Action was suck");
                    //num_dirt--;
                    //env.updateDirt(parent.getState().position); 
                    System.out.println();
                    //actionList.addAll(parent.getPathFromRoot())
                    System.out.println("Parent: ");
                    for(int i = 0; i < parent.getState().dirt.length; i++) {
                        for (int j = 0; j < parent.getState().dirt[0].length; j++) {
                            System.out.print( " [" + parent.getState().dirt[i][j] + "] ");
                        }
                        System.out.println("");
                    }
                    System.out.println("Child: ");
                    for(int i = 0; i < child_state.dirt.length; i++) {
                        for (int j = 0; j < child_state.dirt[0].length; j++) {
                            System.out.print( " [" + child_state.dirt[i][j] + "] ");
                        }
                        System.out.println("");
                    }
                }
            	
                Node child = new Node(child_state, parent, a, a.getCost(a));
                System.out.println("The successor state is " + child_state.toString());
                
                if (!explored.contains(child) && !frontier.contains(child)) {
                	System.out.println("Check if goal state");
                    if (isGoalState(child_state)) {
                    	System.out.println("Found goal state, it is: " + child.toString());
                        return child.getPathFromRoot();
                    }
                    //System.out.println("CHIIILD ADDDDEEEDDD TOOOOO FRONTIERRRRRRRRRRRRR");
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
        int dirts = 0;
    	for (int i = 0; i < s.dirt.length; i++ ) { // if this works, exchange for a variable num_dirt
        	for (int j = 0; j < s.dirt[0].length; j++) {
        		if(s.dirt[i][j] == true ) {
        			dirts++;
        		}
        	}
        }
    	
    	if (dirts <= 1 ){ //&& s.position.equals(env.home_pos) && s.orientation.equals(env.home_orient)) {
            //turnOff();
            return true;
        }
        return false;
    }
    
    public Action turnOff() {
        return Action.TURN_OFF;
    }
}
