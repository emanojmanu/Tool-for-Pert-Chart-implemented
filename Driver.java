/** Driver program for MP4
 *  
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        Graph g = Graph.readDirectedGraph(in);
	for(Vertex u: g) {
	   if(in.hasNextInt())
	   {
		u.d = in.nextInt();
	   }
	}
//g.TopologicalOrdering2();
	Timer timer = new Timer();
	CriticalPaths cp = new CriticalPaths(g);
	cp.earliestTimeCalculation();
	cp.latestTimeCalculation();
	cp.lengthOFCriticalPAth();
	cp.findCriticalPaths();
	cp.printoneCriticalPAth();
	
	System.out.println();
	
	cp.printAllTheVerticeDetails();
	System.out.println();
	System.out.println(cp.noOfCriticalnodes);
	System.out.println(cp.listOfAllCriticalPAths.size());
	cp.printallCriticalPAths();
	System.out.println();
	System.out.println(timer.end());
    }
}

