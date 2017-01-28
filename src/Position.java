package src;


public class Position {
    
    public int x;
    public int y;
    
    public Position(int x, int y) {
        this.x = x; this.y = y;
    }
    
    public Position goOneStep(Orientation orient, Environment e) {
        Position newPos = new Position(x,y);
        switch(orient) {
            case NORTH:
                if (y < e.sizeY -1) {
                    newPos.y++;
                }
                break;
            case SOUTH:
                if (y > 0) {
                    newPos.y--;
                }
                break;
            case WEST:
                if (x > 0) {
                    newPos.x--;
                }
                break;
            case EAST:
                if (x < e.sizeX -1) {
                    newPos.x++;
                }
                break;
        }
        System.out.println(newPos);
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

