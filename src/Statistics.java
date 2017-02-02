package src;

public class Statistics {
	 private int expansions;
	    private Integer cost;
	    private Long startTime;
	    private int notVisited;

	    public Statistics() {
	        expansions = 0;
	        cost = 0;
	    	startTime = System.currentTimeMillis();
	    	notVisited = 0;
	    }

	    public void incrementExpansions() {
	        expansions++;
	    }

	    public void setCost(int cost) {
	        this.cost = cost;
	    }
	    
	    public void incrementNotVisited() {
	    	notVisited ++;
	    }
	    
	    public void printStatistics() {
	        System.out.println("------printing statistics-----");
	        System.out.println("Cost: " + cost.toString());
	        Long calculatedTime = System.currentTimeMillis() - startTime;
	        System.out.println("Time: " + calculatedTime.toString() + " ms");
	        System.out.println("Number of state expantions: " + expansions);
	        System.out.println("Not visited states because of detection of revisited states: " + notVisited);
	    }
}
