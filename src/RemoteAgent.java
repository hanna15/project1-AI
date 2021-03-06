package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoteAgent implements Agent {
    public Environment e;
    Stack<Action> actions;
    
    @Override
    public void init(Collection<String> percepts) {
        ArrayList<String> a = new ArrayList<String>(percepts);
        String size = a.get(a.size()-1);
        
        Matcher m0 = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(size);
        if (m0.matches()) {
            //System.out.println("size is " + m0.group(1) + "," + m0.group(2));
            e = new Environment(Integer.valueOf(m0.group(1)), Integer.valueOf(m0.group(2)));
            //System.out.println(e.sizeX);
        }
        
        Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
        for (String percept:percepts) {
            Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
            if (perceptNameMatcher.matches()) {
                String perceptName = perceptNameMatcher.group(1);
                if (perceptName.equals("HOME")) {
                    Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        //System.out.println("robot is at " + m.group(1) + "," + m.group(2));
                        e.setHomePos(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)));
                    }
                }
                else if (perceptName.equals("AT")) {
                    Matcher m2 = Pattern.compile("\\(\\s*AT\\sDIRT\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    Matcher m3 = Pattern.compile("\\(\\s*AT\\sOBSTACLE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m2.matches()) {
                        //System.out.println("dirt is at " + m2.group(1) + "," + m2.group(2));
                        e.addDirt(Integer.valueOf(m2.group(1)), Integer.valueOf(m2.group(2)));
                    }
                    
                    else if (m3.matches()) {
                        //System.out.println("obstacle is at " + m3.group(1) + "," + m3.group(2));
                        e.addObstacle(Integer.valueOf(m3.group(1)), Integer.valueOf(m3.group(2)));
                    }
                }
                else if (perceptName.equals("ORIENTATION")) {
                    Matcher m4 = Pattern.compile("\\(\\s*ORIENTATION\\s+([A-Z]+)\\)").matcher(percept);
                    if (m4.matches()) {
                        //System.out.println("orientation is " + m4.group(1));
                        Orientation hO = Orientation.valueOf(m4.group(1)); // cast to Orientation...
                        e.setHomeOrient(hO);
                    }
                }
                else {
                    //System.out.println("other percept:" + percept);
                }
            } else {
                System.err.println("strange percept that does not match pattern: " + percept);
            }
        }
        //System.out.println(Arrays.deepToString(e.dirt));
        e.setInitialState();
    	//e.printEnvironment();
        // BFS bfs = new BFS(e);
        //actions = bfs.bfsRun;
//        for(Action ax: actions) {
//            System.out.print(ax + " ");
//        }
//        System.out.println(" ");
    }
    
    @Override
    public String nextAction(Collection<String> percepts) {
        // TODO Auto-generated method stub
        // TODO: TURN ROBOT ON
        if (!actions.empty() && actions != null) {
            Action a = actions.pop();
            if (a == null) {
                return "TURN_ON";
            }
            else {
               // System.out.println(a.toString());
                return a.toString();
            }
        }
        return "TURN_OFF"; //fix later
    }
    
}


