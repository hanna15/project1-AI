package src;

import java.lang.*;
import java.util.*;

public class BFS {
    public Environment env;
    public Node initial_node;
    public Deque<Node> frontier;
    public Set<State> explored;
    public int num_dirt;
    Stack<Action> bfsRun;
    
    BFS(Environment e) {
        this.env = e;
        initial_node = new Node(env.initial_state);
        frontier = new LinkedList<Node>();
        explored = new HashSet<State>();
        num_dirt = env.num_dirt;
        System.out.println("BFsssssssssssssssssssssssssssssssssssssssssssssssssssS num_dirt " + num_dirt);
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
            return initial_node.getPathFromRoot();
        }
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node parent = frontier.pop();
            explored.add(parent.getState());
            
            for(Action a : parent.getState().legalActions(env)) {
                State child_state = parent.getState().successorState(a, env);
                if (a == Action.SUCK) {
                    num_dirt--;
                }
                Node child = new Node(child_state, parent, a, a.getCost(a));
                
                if (!explored.contains(child_state) && !frontier.contains(child)) {
                    if (isGoalState(child_state)) {
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
        if (s.position.equals(env.home_pos) && s.orientation.equals(env.home_orient) && num_dirt <= 0) {
            //turnOff();
            return true;
        }
        return false;
    }
    
    public Action turnOff() {
        return Action.TURN_OFF;
    }
}
