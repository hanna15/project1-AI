package src;

import java.util.Arrays;

public class Environment {
    public boolean obstacles[][]; //betra a� hafa lista, �v� vitum ekki fyrir fram size?
    public boolean dirt[][];
    public Position home_pos;
    public Orientation home_orient;
    public State initial_state;
    public int num_dirt;
    public int sizeX;
    public int sizeY;
    
    Environment() {
        num_dirt = 0;
    }
    
    Environment(int sX, int sY) {
        setSize(sX, sY);
        dirt = new boolean[sizeX][sizeY];
        obstacles = new boolean[sizeX][sizeY];
        num_dirt = 0;
    }
    
    Environment(boolean [][] o, boolean[][] d, Position home_p, Orientation home_o, int initial_num_dirt) {
        obstacles = o;
        dirt = d;
        home_pos = home_p;
        home_orient = home_o;
        num_dirt = initial_num_dirt;
    }
    
    private void setSize(int x, int y) { // ath, switch?
        sizeX = x;
        sizeY = y;
    }
    
    public void setHomePos(int x, int y) {
        home_pos = new Position(x-1, y-1);
    }
    
    public void setHomeOrient(Orientation o) {
        home_orient = o;
    }
    
    public void setInitialState() {
    	boolean containsDirt = containsDirt(home_pos);
    	State s = new State(home_pos, home_orient, true, containsDirt);
    	System.out.println(s);
    	initial_state = s;
    }
    
    public boolean containsDirt(Position p) {
    	int x = p.x;
    	int y = p.y;
    	if (dirt[x][y]) {
    		return true;
    	}
    	return false;
    }
    
    public void addObstacle(int x, int y) {
        obstacles[x-1][y-1] = true;
    }
    
    public void addDirt(int x, int y) {
        dirt[x-1][y-1] = true;
        num_dirt ++;
    }
    
    
    public void printEnvironment() {
        System.out.println("sizeX " + sizeX );
        System.out.println("sizeY " + sizeY );
        System.out.println("num_dirt " + num_dirt);
        
        for(int i = 0; i < dirt.length; i++) {
            for (int j = 0; j < dirt[0].length; j++) {
                System.out.print( " [" + dirt[i][j] + "] ");
            }
            System.out.println("");
        }
        System.out.println("");
        for(int i = 0; i < obstacles.length; i++) {
            for (int j = 0; j < obstacles[0].length; j++) {
                System.out.print( " [" + obstacles[i][j] + "] " );
            }
            System.out.println("");
        }
        
        
        System.out.println("obstacles " + Arrays.deepToString(obstacles) );
        System.out.println("home_pos " + home_pos );
        System.out.println("home_orient " + home_orient );
    }
}
