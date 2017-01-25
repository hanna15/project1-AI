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

	public ArrayList<Action> legalActions() {
		return null;
		//TODO
		//return list
	}

	public State successorState(Action action)  {
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
			default: //should never happend
				return new State(position, orientation, turned_on, contains_dirt);
		}
	}
	

	public boolean isGoalState() {
		//ATH! NO
		e = new Environment();
		if (position == e.homePos && orientation == e.homeOrient) {
			return true;
		}
		//and if all states are clean
		return false;
	}
}
