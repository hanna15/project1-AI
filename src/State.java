package src;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

public class State {
    private Orientation orientation;
    private Position position;
    private boolean turned_on;
    public HashSet<Position> dirt;
   // public boolean[][] dirt2;

    public State(Position position, Orientation orientation, boolean turned_on, HashSet<Position> dirt) {
        this.position = position;
        this.orientation = orientation;
        this.turned_on = turned_on;
        this.dirt = dirt;
        //this.dirt2 = dirt2;
    }

    public Position getPosition() { 
    	return this.position; 
    }

    public boolean isGoalState(Position home, Orientation h_orient) {
        // Do not check if the bot is turned off since it is added manually
    	//int trueCount = Arrays.deepToString(dirt2).replaceAll("[^t]", "").length();
        if (dirt.size() == 0 && this.getPosition().equals(home) && this.orientation.equals(h_orient)) {
            return true;
        }
        return false;
    }

    public ArrayList<Action> legalActions(Environment e) {
        ArrayList<Action> legalActions = new ArrayList<Action>();

        if (dirt.contains(position)) {
        	// System.out.println("in suck");
            legalActions.add(Action.SUCK);
        }
        else {
            // try to turn right
            Orientation rOrient = orientation.turnRight(orientation);
            if (isPositionLegal(position.goOneStep(rOrient), e.sizeX,e.sizeY)) {
            	// System.out.println("in right");
                legalActions.add(Action.TURN_RIGHT);
            }

            // try to turn left
            Orientation lOrient = orientation.turnLeft(orientation);
            if (isPositionLegal(position.goOneStep(lOrient), e.sizeX, e.sizeY)) {
            	// System.out.println("in left");
                legalActions.add(Action.TURN_LEFT);
            }

            // Try to go forward 
            Position nextPos = position.goOneStep(orientation);
            if (isPositionLegal(nextPos, e.sizeX, e.sizeY) && !e.containsObstacle(nextPos)) {
            	// System.out.println("in GO");
                legalActions.add(Action.GO);
            }
            // System.out.println("NOTHING");
        }
        return legalActions;
    }

    private boolean isPositionLegal(Position pos, int xSize, int ySize) {
        if (pos.x < 0 || pos.y < 0 || pos.x > xSize - 1 || pos.y > ySize - 1) {
            return false;
        }
        return true;
    }

    public State successorState(Action action) {
        if (action.equals(Action.TURN_RIGHT)) {;
        	Orientation newOri = orientation.turnRight(orientation);
            return new State(this.position, newOri, this.turned_on, this.dirt);
        }
        else if (action.equals(Action.TURN_LEFT)) {
        	Orientation newOri = orientation.turnLeft(orientation);
            return new State(this.position, newOri, this.turned_on, this.dirt);
        }
        else if (action.equals(Action.GO)) {
            Position newPos = position.goOneStep(orientation);
            return new State(newPos, this.orientation, this.turned_on, this.dirt);
        }
        else if (action.equals(Action.SUCK)) {
            HashSet<Position> newDirt = copyAndChangeDirt();
            return new State(this.position, this.orientation, this.turned_on, newDirt);
        }
       /*
        else if (action.equals("TURN_OFF")) {
            return new State(this.position, this.orientation, false, this.dirt);
        }
        else if (action.equals("TURN_ON")) {
            return new State(this.position, this.orientation, true, this.dirt);
        }
        */
        return null;
    }

/*
    private boolean[][] changeDirt() {
    	boolean[][] newBool = new boolean[dirt2.length][];
    	for(int i = 0; i < newBool.length; i++) {
    		newBool[i] = dirt2[i].clone();
    	}
    	newBool[position.x][position.y] = false;
    	return newBool;
    }
*/
    
    private HashSet<Position> copyAndChangeDirt() {
        HashSet<Position> set = new HashSet<Position>();
        for (Iterator<Position> i = this.dirt.iterator(); i.hasNext();) {
            Position item = i.next();
            if (!item.equals(this.position)) {
                set.add(item);
            }
        }
        return set;
    }

    public String toString() {
        return "State{position: " + position + ", orientation: " + orientation + ", on:" + turned_on + "}";
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (this.turned_on) {
            hashCode += 1;
        }
        switch(orientation) {
        case NORTH: hashCode += 1;
        case SOUTH: hashCode += 2;
        case EAST: hashCode += 3;
        case WEST: hashCode += 4;
        }
        hashCode = hashCode + this.position.x << 3;
        hashCode = hashCode + this.position.y << 13;
        hashCode = hashCode + this.dirt.size() << 27;
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
    	if (other == this) return true;
    	if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        State that = (State)other;
        return (this.position.equals(that.position) && this.turned_on == that.turned_on && this.orientation == that.orientation && this.dirt.equals(that.dirt));
    }
}