package src;
public enum Action {
	TURN_ON, TURN_OFF, TURN_RIGHT, TURN_LEFT, GO, SUCK;
	
	public int getCost(Action a) {
//		switch(a) {
//			case TURN_ON:
//				return 1;
//			break;
//			case GO:
//				return 1;
//			break;
//			
//		}
		return 1;
	}
};