package src;

public class Main {
	public static void main(String[] args){
		try{
			// TODO: put in your agent here
			Agent agent = new RemoteAgentGeneral();

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
