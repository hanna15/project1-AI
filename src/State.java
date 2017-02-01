package src;


import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

public class State {
    private Orientation orientation;
    private Position position;
    //private boolean turned_on;
    public HashSet<Position> dirt;
   // public boolean[][] dirt2;

    public State(Position position, Orientation orientation, HashSet<Position> dirt) { // turned on tekið út.
        this.position = position;
        this.orientation = orientation;
        //this.turned_on = turned_on;
        this.dirt = dirt;
        //this.dirt2 = dirt2;
    }

    public Position getPosition() { 
    	return this.position; 
    }

    public boolean isGoalState(Position home) {
        // Do not check if the bot is turned off since it is added manually
    	//int trueCount = Arrays.deepToString(dirt2).replaceAll("[^t]", "").length();
        if (dirt.size() == 0 && this.getPosition().equals(home)) {
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
        	// Try to go forward 
            Position nextPos = position.goOneStep(orientation);
            if (isPositionLegal(nextPos, e.sizeX, e.sizeY) && !e.containsObstacle(nextPos)) {
            	// System.out.println("in GO");
                legalActions.add(Action.GO);
            }
        	
            // try to turn right
            Orientation rOrient = turnRight();
            if (isPositionLegal(position.goOneStep(rOrient), e.sizeX,e.sizeY)) {
            	// System.out.println("in right");
                legalActions.add(Action.TURN_RIGHT);
            }

            // try to turn left
            Orientation lOrient = turnLeft();
            if (isPositionLegal(position.goOneStep(lOrient), e.sizeX, e.sizeY)) { 
            	// System.out.println("in left");
                legalActions.add(Action.TURN_LEFT);
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
        	Orientation newOri = turnRight();
            return new State(this.position, newOri, this.dirt);
        }
        else if (action.equals(Action.TURN_LEFT)) {
        	Orientation newOri = turnLeft();
            return new State(this.position, newOri, this.dirt);
        }
        else if (action.equals(Action.GO)) {
            Position newPos = position.goOneStep(orientation);
            return new State(newPos, this.orientation, this.dirt);
        }
        else if (action.equals(Action.SUCK)) {
            HashSet<Position> newDirt = copyAndChangeDirt();
            return new State(this.position, this.orientation, newDirt);
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
    
    public Orientation turnRight() {
    	switch(orientation) {
    	case NORTH: 
    		return Orientation.EAST;
		case EAST:
			return Orientation.SOUTH;
		case SOUTH:
			return Orientation.WEST;
		case WEST:
			return Orientation.NORTH;
		default:
			return Orientation.NORTH;
    	}
    }

    public Orientation turnLeft() {
    	switch(orientation) {
    	case NORTH: 
    		return Orientation.WEST;
		case EAST:
			return Orientation.NORTH;
		case SOUTH:
			return Orientation.EAST;
		case WEST:
			return Orientation.SOUTH;
		default:
			return Orientation.NORTH;
    	}
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
        return "State{position: " + position + ", orientation: " + orientation + "}";
    }
    
    public int calculateCost(Action action, Position home) {
    	if (action.equals(Action.TURN_OFF)) {
        	if(this.position.equals(home)) {
        		return 1 + 50 * this.dirt.size();
        	}
        	else {
        		return 100 + 50 * this.dirt.size();
        	}
        }
        if (!this.dirt.contains(this.position) && action.equals(Action.SUCK)) {
            return 5;
        }
        return 1; //all other actions
    }

    @Override
    public int hashCode() {
        int hashCode = 17;

    
        hashCode = hashCode * 31 + orientation.hashCode();
        hashCode = hashCode * 31 + this.position.hashCode();
        Integer dirtSize = (Integer)this.dirt.size();
        hashCode = hashCode * 31 + dirtSize.hashCode();
        
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
    	if (other == this) return true;
    	if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        State that = (State)other;
        return (this.position.equals(that.position) && this.orientation == that.orientation && this.dirt.equals(that.dirt));
    }
}