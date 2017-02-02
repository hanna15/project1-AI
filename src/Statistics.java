package src;

public class Statistics {
	 private Integer expansions;
	    private Integer cost;
	    private Long startTime;

	    public Statistics() {
	        expansions = 0;
	        cost = 0;
	    	startTime = System.currentTimeMillis();
	    }

	    public void incrementExpansions() {
	        expansions++;
	    }

	    public void setCost(int cost) {
	        this.cost = cost;
	    }
	    
	    public void printStatistics() {
	        System.out.println("------printing statistics-----");
	        System.out.println("Cost: " + cost.toString());
	        Long calculatedTime = System.currentTimeMillis() - startTime;
	        System.out.println("Time: " + calculatedTime.toString() + " ms");
	        System.out.println("Number of state expantions: " + expansions);
	    }
}
