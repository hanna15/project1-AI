package src;
import java.util.HashSet;

public class Environment {
	public boolean obstacles[][]; 
    public HashSet<Position> dirt;
    //public Position dirt[];
    //public boolean dirt2[][];
    public Position home_pos;
    public Orientation home_orient;
    public State initial_state;
    public int sizeX;
    public int sizeY;
    
    Environment(int x, int y) {
    	setSize(x,y);
//    	home_pos = new Position(0, 0); // no
//    	home_orient = Orientation.NORTH; // no
    	obstacles = new boolean[sizeX][sizeY];
    	dirt = new HashSet<Position>();
    }
    
    private void setSize(int x, int y) { // ath, switch?
        sizeX = x;
        sizeY = y;
    }
    
    public void setHomePos(int x, int y) {
        home_pos = new Position(x - 1, y - 1);
    }
    
    public void setHomeOrient(Orientation o) {
        home_orient = o;
    }
    
    public void setInitialState() {
        State s = new State(home_pos, home_orient, true, dirt);
        // System.out.println(s);
        initial_state = s;
    }
  
    public boolean containsObstacle(Position p) {
        int x = p.x;
        int y = p.y;
        if (obstacles[x][y]) {
            return true;
        }
        return false;
    }
     
    public void addObstacle(int x, int y) {
        obstacles[x-1][y-1] = true;
    }
    
    public void addDirt(int x, int y) {
    	Position p = new Position(x - 1, y - 1);
    	dirt.add(p);
    }

}
