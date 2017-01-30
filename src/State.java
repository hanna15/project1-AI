package src;

import java.util.ArrayList;
import java.util.Arrays;

public class State {
    public Orientation orientation;
    public Position position;
    public boolean turned_on;
   // public boolean contains_dirt;
    public boolean dirt [][];
    
    
    //viljum vi� a� �a� taki inn "contains_dirt" ?
    public State(Position position, Orientation orientation, boolean turned_on, boolean d[][]) {
        this.position = position;
        this.orientation = orientation;
        this.turned_on = turned_on;
        //this.contains_dirt = contains_dirt;
        this.dirt = new boolean[d.length][d[0].length];
        for (int i = 0; i < d.length; i ++) {
        	for (int j = 0; j < d[0].length; j++) {
        		this.dirt[i][j] = d[i][j];
        	}
        }
        	
    }
    
    public String toString() {
        return "State{position: " + position + ", orientation: " + orientation + ", on:" + turned_on + " dirt: " + "}";
    }
    
  /*   ArrayList<Action>legalA = new ArrayList<Action>();
        
        //if (isGoalState(e)) {  // ath
        // legalA.add(Action.TURN_OFF);
         //}
         //else
        if (this.equals(e.initial_state)) {
            legalA.add(Action.TURN_ON);
        }
        
        else if (this.contains_dirt) {
            legalA.add(Action.SUCK);
        }
        else {
            legalA.add(Action.GO);
            legalA.add(Action.TURN_LEFT);
            legalA.add(Action.TURN_RIGHT);
        }
        return legalA;
    }
       */
    
    public State successorState(Action action, Environment e)  { //eda taka inn environment
        //one example
        //contains_dirt = e.containsDirt(this.position);
        
        switch(action) {
            case GO:
                Position new_pos = position.goOneStep(orientation, e);
                return new State(new_pos, orientation, turned_on, dirt);
            case SUCK:
                //e.num_dirt--;
                //boolean new_dirt = !contains_dirt;
                boolean new_dirt_arr[][] = new boolean [dirt.length][dirt[0].length];
                for (int i = 0; i < dirt.length; i ++) {
                	for (int j = 0; j < dirt[0].length; j++) {
                		new_dirt_arr[i][j] = dirt[i][j];
                	}
                }
                new_dirt_arr[position.x][position.y] = false;
                return new State(position, orientation, turned_on, new_dirt_arr);
            case TURN_LEFT:
                Orientation left_orient = orientation.turnLeft(orientation);
                return new State(position, left_orient, turned_on, dirt);
            case TURN_RIGHT:
                Orientation right_orient = orientation.turnRight(orientation);
                return new State(position, right_orient, turned_on, dirt);
            case TURN_ON:
            	boolean turn_on = !turned_on;
            	return new State(position, orientation, turn_on, dirt);
            case TURN_OFF:
            	boolean turn_off = !turned_on;
            	return new State(position, orientation, turn_off, dirt);
            default: //should never happen
                return new State(position, orientation, turned_on, dirt);
        }
    }
    
    //    public boolean isGoalState(Environment e) {
    //    	//System.out.println(position + " vs " + e.home_pos + " " + orientation + " vs " + e.home_orient + " "  + e.num_dirt);
    //        if (position.equals(e.home_pos) && orientation.equals(e.home_orient) && e.num_dirt == 0) {
    //        	return true;
    //        }
    //        return false;
    //    }
}