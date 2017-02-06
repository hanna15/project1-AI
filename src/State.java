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
    
    //We have reached the goal if all dirt has been cleared and we are back home
    public boolean isGoalState(Position home) {
    	return (dirt.size() == 0 && this.getPosition().equals(home));
    }
    
    //Returns list of all the legal actions from the state
    //We restrict the legal actions to reduce states explored
    public ArrayList<Action> legalActions(Environment e) {
        ArrayList<Action> legalActions = new ArrayList<Action>();
        
        //only allow one action, suck, if the state contains dirt
        if (dirt.contains(position)) {
            legalActions.add(Action.SUCK);
        }
        else {
        	// Try to go forward, only allow if it will not result in bumping to an obstacle or the walls
            Position nextPos = position.goOneStep(orientation);
            if (legalPos(nextPos, e.getSizeX(), e.getSizeY()) && !e.containsObstacle(nextPos)) {
                legalActions.add(Action.GO);
            }
        	
            // try to turn right, only allow if "GO" in the new direction will not result in bumping to an obstacle or walls
            Orientation rOrient = turnRight();
            Position newPosR = position.goOneStep(rOrient);
            if (legalPos(newPosR, e.getSizeX(), e.getSizeY()) && !e.containsObstacle(newPosR)) {
                legalActions.add(Action.TURN_RIGHT);
            }

            // try to turn left, only allow if "GO" in the new direction will not result in bumping to an obstacle or walls
            Orientation lOrient = turnLeft();
            Position newPosL = position.goOneStep(lOrient);
            if (legalPos(newPosL, e.getSizeX(), e.getSizeY()) && !e.containsObstacle(newPosL)) { 
                legalActions.add(Action.TURN_LEFT);
            }
            
            if(legalActions.size() == 0) {
            	//System.out.println("In exception");
            	legalActions.add(Action.TURN_LEFT);
            }
        }
        return legalActions;
    }
    
    //checks if position is whithin grid
    private boolean legalPos(Position pos, int xSize, int ySize) {
        return (pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < xSize && pos.getY() < ySize);
    }

    //returns the state that results from a given action on the current state
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
    
    //updates the dirt in the environment for a state that resulted from dirty position being sucked
    private HashSet<Position> copyAndChangeDirt() {
        HashSet<Position> set = new HashSet<Position>();
        //copying the previous dirt set... 
        for (Position p : dirt) {
        	if (!p.equals(this.position)) { //...but not adding the current dirt that is being sucked
        		set.add(p);
        	}
        }
        return set;
    }
    
    //for UCS and ASTAR
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