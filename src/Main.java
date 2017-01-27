package src;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){
		int W = 10; //width = columns
		int L = 20; //length = rows
		boolean my_dirt[][] = new boolean[L][W]; //initializes to false
		my_dirt[2][3] = true;
		my_dirt[0][0] = true;
		
		boolean my_obstacles[][] = new boolean[L][W];

		Position home_pos = new Position(1,2);
		Environment e = new Environment(my_obstacles, my_dirt, home_pos, Orientation.NORTH, 2);
		System.out.println("Home pos is " + e.home_pos);
		
		State home_s = new State(new Position(1,2), Orientation.NORTH, true, false);
		
		State s = new State(new Position(0,0), Orientation.NORTH, true, true);
		System.out.println("current state: " + s.contains_dirt + " " + s.position + " " + s.orientation);
		
		//Testing successorState function
		State succ_suck = s.successorState(Action.SUCK);
		System.out.println("SuccStateSuck- IsDirt?" + succ_suck.contains_dirt);
		State succ_go = s.successorState(Action.GO);
		System.out.println("SuccStateGo pos: " + succ_go.position);
		State succ_left = s.successorState(Action.TURN_LEFT);
		System.out.println("SuccStateLeft orient: " + succ_left.orientation);
		State succ_right = s.successorState(Action.TURN_RIGHT);
		System.out.println("SuccStateRight orient: " + succ_right.orientation);
		
		//Testing function "legal actions"
		ArrayList<Action> actions = s.legalActions(e);
		if (actions.size() == 0) {
			System.out.println("No legal acctions");
		}
		else {
			System.out.println("Legal actions are: ");
			for (Action a : actions) {
				System.out.print(a + " ");
			}
			System.out.println();
		}
		
		ArrayList<Action> actions2 = succ_suck.legalActions(e);
		if (actions2.size() == 0) {
			System.out.println("No legal acctions");
		}
		else {
			System.out.println("Legal actions are: ");
			for (Action a : actions2) {
				System.out.print(a + " ");
			}
			System.out.println();
		}
		
		
		if(s.isGoalState(e)) {
			System.out.println("s isGoalState");
		}
		else {
			System.out.println("s is not goal state");
		}
		e.num_dirt = 0;
		State shouldBeGoal = new State(new Position(1,2), Orientation.NORTH, true, false);
		
		if(shouldBeGoal.isGoalState(e)) {
			System.out.println("yeah isGoalState");
		}
		else {
			System.out.println("ohh, it is not goal state");
		}
		Node n_root = new Node(home_s);
		Node n = new Node(succ_suck, n_root, Action.SUCK, 5);
		System.out.println(n.getPathFromRoot());
		
		/*try{
			// TODO: put in your agent here
			Agent agent = new RandomAgent();

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}*/
	}
}
