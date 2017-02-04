package src;
import java.util.HashSet;

public class Environment {
	private boolean obstacles[][]; 
    private HashSet<Position> dirt;
    private Position home_pos;
    private Orientation home_orient;
    private State initial_state;
    private int sizeX;
    private int sizeY;
    
    Environment(int x, int y) {
    	setSize(x,y);
    	obstacles = new boolean[sizeX][sizeY];
    	dirt = new HashSet<Position>();
    }
    
    public Position getHomePos() {
    	return home_pos;
    }
    
    public State getInitState() {
    	return initial_state;
    }
    
    public int getSizeX() {
    	return sizeX;
    }
    
    public int getSizeY() {
    	return sizeY;
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
        State s = new State(home_pos, home_orient, dirt);
        initial_state = s;
    }
  
    public boolean containsObstacle(Position p) {
        int x = p.getX();
        int y = p.getY();
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
