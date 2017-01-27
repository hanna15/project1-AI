package src;

import java.util.ArrayList;

public class State {
    public Orientation orientation;
    public Position position;
    public boolean turned_on;
    public boolean contains_dirt;
    public Environment e;
    
    public State(Position position, Orientation orientation, boolean turned_on, boolean contains_dirt) {
        this.position = position;
        this.orientation = orientation;
        this.turned_on = turned_on;
        this.contains_dirt = contains_dirt;
    }
    
    public String toString() {
        return "State{position: " + position + ", orientation: " + orientation + ", on:" + turned_on + "}";
    }
    
    public ArrayList<Action> legalActions() {          // eda bara skila array?
        ArrayList<Action>legalA = new ArrayList<Action>();
        
        if (isGoalState(e)) {
            legalA.add(Action.TURN_OFF);
        }
        /*else if (isInitialState(e)) {
         legalA.add(Action.TURN_ON);
         }
         */
        else if (contains_dirt) {
            legalA.add(Action.SUCK);
        }
        else {
            legalA.add(Action.GO);
            legalA.add(Action.TURN_LEFT);
            legalA.add(Action.TURN_RIGHT);
        }
        return legalA;
    }
    
    public State successorState(Action action)  { //eda taka inn environment
        //one example
        switch(action) {
            case GO:
                Position new_pos = position.goOneStep(orientation);
                return new State(new_pos, orientation, turned_on, contains_dirt);
            case SUCK:
                boolean newDirt = !contains_dirt;
                return new State(position, orientation, turned_on, newDirt);
            case TURN_LEFT:
                Orientation left_orient = orientation.turnLeft(orientation);
                return new State(position, left_orient, turned_on, contains_dirt);
            case TURN_RIGHT:
                Orientation right_orient = orientation.turnRight(orientation);
                return new State(position, right_orient, turned_on, contains_dirt);
            default: //should never happen
                return new State(position, orientation, turned_on, contains_dirt);
        }
    }
    
    public boolean isGoalState(Environment e) {
        if (position == e.homePos && orientation == e.homeOrient) { //&& if all states are clean) (&& e.dirt.length == 0)?
            return true;
        }
        return false;
    }
}