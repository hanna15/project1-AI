package src;
public enum Orientation {
	NORTH, EAST, SOUTH, WEST;
	
	public Orientation turnRight(Orientation prev_orientation) {
		switch(prev_orientation) {
			case NORTH:
				return EAST;
			case EAST:
				return SOUTH;
			case SOUTH:
				return WEST;
			case WEST:
				return NORTH;
			default: //Should never happend
				return NORTH;
		}
	}
	
	public Orientation turnLeft(Orientation prev_orientation) {
		switch(prev_orientation) {
			case NORTH:
				return WEST;
			case EAST:
				return NORTH;
			case SOUTH:
				return EAST;
			case WEST:
				return SOUTH;
			default: //Should never happend
				return NORTH;
		}
	}
	
};
