package src;

public class Environment {
    public Obstacle obstacles[]; //betra a� hafa lista, �v� vitum ekki fyrir fram size?
    public boolean dirt[][]; //ATH!
    public Position home_pos;
    public Orientation home_orient;
    public int num_dirt;
    
    Environment(Obstacle[] o, boolean[][] d, Position home_p, Orientation home_o, int initial_num_dirt) {
    	obstacles = o;
    	dirt = d;
    	home_pos = home_p;
    	home_orient = home_o;
    	num_dirt = initial_num_dirt;
    }
}
