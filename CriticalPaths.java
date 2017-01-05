import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CriticalPaths {
    Graph inputG;
    int noOfCriticalnodes;
    int noOfCriticalPaths;
    int glabali;
    List<List<Vertex>> listOfAllCriticalPAths;
    CriticalPaths(Graph g) {
       inputG=g;
       glabali=0;
       noOfCriticalnodes=-2;
       noOfCriticalPaths=0;
       listOfAllCriticalPAths=new ArrayList<>();
    }

    void earliestTimeCalculation()
    {
    	List<Vertex> litop=inputG.TopologicalOrdering1();
    	//src.earliest , d=0 by default, vertex.earlies,lateest =0 by default
    	for(Vertex u:litop)
    	{
    		for(Edge e:u.adj)
    		{
    			Vertex v=e.otherEnd(u);
    			if(v.earliestTime<u.earliestTime+v.d)
    			{
    				v.earliestTime=u.earliestTime+v.d;
    			}
    			
    		}
    		
    	}
    	//System.out.println("wd");
    }
    void latestTimeCalculation()
    {
    	Vertex target=inputG.dest;
    	target.latestTime=target.earliestTime;
    	for(Vertex u:inputG.v)
    	{
    		if(u!=null)  //arraylist(0) ==null
    		{
    		u.latestTime=target.latestTime;
    		}
    	}
    	List<Vertex> top2=inputG.TopologicalOrdering2();
    		Object[]	arr= top2.toArray();
    		Vertex[] verarr=new Vertex[arr.length];
        	for(int i=0;i<arr.length;i++)
    		{
    			verarr[i]=(Vertex) arr[i];
    		}
    		
    	for(int i=verarr.length-1;i>=0;i--)
    	{
    		Vertex u=verarr[i];
    		u.slack=u.latestTime-u.earliestTime;
			if(u.slack==0)
			{
				noOfCriticalnodes++;
			}
    		for(Edge e:verarr[i].revAdj)
    		{
    			Vertex v=e.otherEnd(u);
    			/*if(v==inputG.getVertex(29)||u==inputG.getVertex(29))
    			{
    				System.out.println("");
    			}*/
    			if(v.latestTime>u.latestTime-u.d)
    			{
    				v.latestTime=u.latestTime-u.d;
    				//v.slack=v.latestTime-v.earliestTime;
    			}
    		}
    	}
    	//System.out.println("end");
    	/*Vertex first=top2.get(0);
    	while(first!=null)
    	{
    	
    	}*/
    }
    void printAllTheVerticeDetails()
    {
    //	System.out.println("printing all the paths");
    //
    	System.out.println("Task	EC	LC    Slack");
    	for(Vertex v:inputG)
		{
			if (v != inputG.src && v != inputG.dest) {

				System.out.println(v.name + "   " + v.earliestTime + "    " + v.latestTime + "    " + v.slack);

			}
		}
    }

    void findCriticalPaths() {/*
    	List<List<Vertex>> an array of vertices is used to 
    	work on all possible paths. But, since array is not dynamic for larger inputs.*/
    	//List<Vertex>[] pathCritical=new LinkedList[900];
    	List<List<Vertex>> pathCritical=new ArrayList<>();
    	int i=0;//each of the above list if path is done added to overall listofallcriticalpaths.
    	for(Vertex u:inputG)
    	{
    		u.seen=false;
    		//pathCritical[i]=new LinkedList<>();
    		//pathCritical.get(i)=new LinkedList<>();
    		i++;
    	}/*
    	for(int k=inputG.size;k<900;k++)
    	{
    		pathCritical[k]=new LinkedList<>();
    	}*/
    	i=0;
    	Vertex dummySrc=inputG.src;//path can travers only from a src so only src.adj is used
    	for(Edge e:dummySrc.adj)
    	{
    		Vertex u=e.otherEnd(dummySrc);
    		//if it is critical 
    		if(!u.seen&&u.slack==0&&u!=inputG.src)
    		{
    			//adding a linked list object whenever golbali is incremented
    			//to create list objects only when needed 
    			pathCritical.add(glabali, new LinkedList<>());
    			//pathCritical[glabali].add(u);
    			// u is added to path
    			pathCritical.get(glabali).add(u);
    			
    		//	System.out.println("path is "+u);
    			DfsCritical5(u,pathCritical);
    		/*	if(pathCritical[i].size()>0)
    			{
    				//listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		
    		}
    		i++;
    	}
    	//System.out.println("end");
    }
    //at any point in DFS criticalPAth.get(globali) is the path that is being processed now
    void DfsCritical5(Vertex v,List<List<Vertex>> criticalpath)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;  //to know whether it is used in anny critical path
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
 //{ A path is critical if it contains all critical nodes(slack=0)
    		//and TightEdges e(u,v) {u.latestTime=v.latesttime-v.d}}.
    		if(u.slack==0&&v.latestTime==u.latestTime-u.d)
    		{
    			flagToathwithv=true;
    		//	System.out.println("path is "+u);
    			if(u==inputG.dest) //one critical is done
    			{
    			//System.out.println("extis a crictical apth");
    				
    				// this is added to overall list
    			this.listOfAllCriticalPAths.add(criticalpath.get(glabali));
    			
    			//criticalpath.remove(criticalpath.size()-1);
    			//now 
    			/*Now DFS starts at 15 and look for a path to dest; //by examination if a path is
    			 * 1 3 25 30 12 7 26 10 15 14 are all critical so these are already seen in one DFS.
    			 *  They are added to the criticalPAth.get(1). THey are added to next possible list
    			 * 
    			 * */
    			criticalpath.add(glabali+1, new LinkedList<>());
    		
    			criticalpath.get(glabali+1).addAll(criticalpath.get(glabali));
    			this.glabali++;
    			}
    			else{
    				criticalpath.get(glabali).add(u);		
    			}
    			DfsCritical5(u,criticalpath);
    		}
    	}//if a path is constructed using v, then here all its DFS traversals
    	//are done. so the Possible list nolonger goes from u so it is removed.
    	if(flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath.get(glabali).remove(criticalpath.get(glabali).size()-1);
    	}
    	//return criticalpath;
    }
    public void printallCriticalPAths()
    {
    //	System.out.println();
    	/*for(Vertex u:inputG)
    	{
    		if(u.slack==0&&u!=inputG.src&&u!=inputG.dest)
    		{
    			noOfCriticalnodes++;
    		}
    	}*/
    //	System.out.println(noOfCriticalnodes);
    	//System.out.println(this.listOfAllCriticalPAths.size());
    	for(List<Vertex> h:this.listOfAllCriticalPAths)
    	{
    		for(Vertex v:h)
    		{
    			System.out.print(v+" ");
    		}
    		System.out.println();
    	}
    }

	public void lengthOFCriticalPAth() {
		// TODO Auto-generated method stub
	System.out.println(inputG.dest.earliestTime);	
	}

	public void printoneCriticalPAth() {
		// TODO Auto-generated method stub
		if(this.listOfAllCriticalPAths.size()>0)
		{
			for(Vertex h:this.listOfAllCriticalPAths.get(0))
			{
				System.out.print(h+" ");
			}
		System.out.println();
		}
		else{
			System.out.println("no critical paths");
		}
	}
}
