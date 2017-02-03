package src;
import java.util.HashSet;
import java.util.ArrayList;


public class State {
    private Orientation orientation;
    private Position position;
    private HashSet<Position> dirt;


    public State(Position position, Orientation orientation, HashSet<Position> dirt) {
        this.position = position;
        this.orientation = orientation;
        this.dirt = dirt;
    }

    public Position getPosition() { 
    	return this.position; 
    }
    
    public HashSet<Position> getDirt() {
    	return dirt;
    }

    public boolean isGoalState(Position home) {
    	if (dirt.size() == 0 && this.getPosition().equals(home)) {
            return true;
        }
        return false;
    }

    public ArrayList<Action> legalActions(Environment e) {
        ArrayList<Action> legalActions = new ArrayList<Action>();

        if (dirt.contains(position)) {
            legalActions.add(Action.SUCK);
        }
        else {
        	// Try to go forward 
            Position nextPos = position.goOneStep(orientation);
            if (isPositionLegal(nextPos, e.getSizeX(), e.getSizeY()) && !e.containsObstacle(nextPos)) {
                legalActions.add(Action.GO);
            }
        	
            // try to turn right
            Orientation rOrient = turnRight();
            if (isPositionLegal(position.goOneStep(rOrient), e.getSizeX(), e.getSizeY())) {
                legalActions.add(Action.TURN_RIGHT);
            }

            // try to turn left
            Orientation lOrient = turnLeft();
            if (isPositionLegal(position.goOneStep(lOrient), e.getSizeX(), e.getSizeY())) { 
                legalActions.add(Action.TURN_LEFT);
            }
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
     
    private HashSet<Position> copyAndChangeDirt() {
        HashSet<Position> set = new HashSet<Position>();
        for (Position p : dirt) {
        	if (!p.equals(this.position)) {
        		set.add(p);
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