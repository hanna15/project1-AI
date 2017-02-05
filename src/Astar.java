package src;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class Astar {
	private Node initial_node;
	private PriorityQueue<Node> frontier;
    private Set<State> explored = new HashSet<State>();
    private Environment e;
    private Statistics stats;
    private int initial_capacity = 11;
    private HashSet<Position> dirt;
    private List<Position> dirt_distance;

    
    public Astar(Environment env){
    	e = env;
    	dirt_distance = new ArrayList<Position>();
    	int initial_estimated_cost = heuristicFunction(e.getInitState());
    	initial_node = new Node(e.getInitState(), initial_estimated_cost);
    	this.stats = new Statistics();
    	this.dirt = initial_node.getState().getDirt();
    	
    	for(Position p: dirt) {
    		dirt_distance.add(p);
    	}
    	

    	Collections.sort(dirt_distance, new Comparator<Position>() {
			@Override
			public int compare(Position p1, Position p2) {
		        int dist1= distanceToHome(p1);
		        int dist2= distanceToHome(p2);
		        //System.out.println(p1 + "dist: " + dist1 + " " + p2 + "dist: " + dist2);
		        if (dist2 > dist1) {
		        	return -1;
		        }
		        else if (dist1 == dist2) {
		        	return 0;
		        }
		        return 1;
		}});
    	
    	frontier = new PriorityQueue<Node>(initial_capacity, new Comparator<Node>() {
    	    @Override
    	    public int compare(Node n1, Node n2) {
    	        return ((Integer)n1.getAStarTotalCost()).compareTo(n2.getAStarTotalCost()); //�yrfti a� vera a�eins ��ruv�si compartor, taka lika inn estimated cost
    	                                                                        //spurning um a� geyma total cost (lika � node), sem er estimated+path
    	    }});
    	
    	//System.out.println(dirt_distance.toString());
    }

    
    public int distanceToHome(Position p) {
    	Position home = e.getHomePos();
    	int x = (home.getX() - p.getX());
    	int y = (home.getY() - p.getY());
    	//Double sum = Math.pow(x, 2) + Math.pow(y,2);
    	//Double res = Math.sqrt(sum);
    	//System.out.println(p + " " + res);
    	int res = Math.abs(x) + Math.abs(y);
    	return res;
    }
    
    
    public Stack <Action> findPath() {
        frontier.add(initial_node);
        while (!frontier.isEmpty()) {
            Node n = frontier.remove();
            State s = n.getState();
            if (s.isGoalState(e.getHomePos())) {
            	//System.out.println("Goal path cost: " + n.getPathCost());
                return n.getPathFromRoot(stats);
            }
            if(explored.contains(s)) {
            	stats.incrementNotVisited();
            }
            else {
                ArrayList<Action> actions = s.legalActions(e);
            	for (Action a : actions) {
            		State newState = s.successorState(a);
            		if (a == Action.SUCK) {
            			dirt_distance.remove(newState.getPosition());  // does this make sense?
            		}
            		int step_cost = s.calculateCost(a, e.getHomePos());
            		int estimated_cost_to_goal = heuristicFunction(newState); // ath, thetta int cast er frekar iffy
                    Node childNode = new Node(newState, step_cost, estimated_cost_to_goal, n, a); //lata herna inn rettan kostnad i stadinn fyrir 0
                    //�yrftum moulega ad lata node like taka inn estimated cost
                    stats.incrementExpansions();
                    frontier.add(childNode);
            	}
                explored.add(s);
            }
        }
        System.out.println("No goal node found");
        return failure();
    }

    int heuristicFunction(State s) { //the shit
//    	return s.getDirt().size(); //hehe, thetta fall ekki malid, en her kemur sweet formula
    	Position furthestDirt = new Position(0, 0);
    	if (dirt_distance.size() > 1) 
    		furthestDirt = dirt_distance.remove(dirt_distance.size()-1); //passa að remova úr þessu ef gerum Suck
    	return distanceToHome(furthestDirt) + dirt_distance.size();
    }
    
    public Stack<Action> failure() {
    	Stack<Action> temp = new Stack<Action>();
        temp.add(Action.TURN_OFF);
        temp.add(Action.TURN_ON);
        return temp;
    }
    
}
