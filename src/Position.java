package src;


public class Position {
	
	public int x;
	public int y;
	
	public Position(int x, int y) {
		this.x = x; this.y = y;
	}
	
	public Position goOneStep(Orientation orient) {
		Position newPos = new Position(x,y);
		switch(orient) {
			case NORTH:
				newPos.y++;
				break;
			case SOUTH:
				newPos.y--;
				break;
			case WEST:
				newPos.x--;
			case EAST:
				newPos.x++;
		}
		return newPos;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
    @Override 
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Position that = (Position) other;
        return this.x == that.x && this.y == that.y;
    }
    

}
