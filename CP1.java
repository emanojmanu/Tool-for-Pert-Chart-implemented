import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CP1 {
    Graph inputG;
    int noOfCriticalnodes;
    int noOfCriticalPaths;
    int glabali;
    List<List<Vertex>> listOfAllCriticalPAths;
    CP1(Graph g) {
       inputG=g;
       glabali=0;
       noOfCriticalnodes=-2;
       noOfCriticalPaths=0;
       listOfAllCriticalPAths=new LinkedList<>();
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
    void findCriticalPaths() {
    	List<Vertex>[] pathCritical=new LinkedList[inputG.size];
    	int i=0;
    	for(Vertex u:inputG)
    	{
    		u.seen=false;
    		pathCritical[i]=new LinkedList<>();
    		i++;
    	}
    	i=0;
    	for(Vertex u:inputG)
    	{
    		
    		if(!u.seen&&u.slack==0)
    		{
    			pathCritical[i].add(u);
    			System.out.println("path is "+u);
    			BfsCritical(u,pathCritical[i]);
    		/*	if(pathCritical[i].size()>0)
    			{
    				//listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		
    		}
    		i++;
    	}
    //	System.out.println("end");
    }
    void BfsCritical(Vertex v,List<Vertex> criticalpath)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
    		if(!u.seen&&u.slack==0)
    		{
    			flagToathwithv=true;
    			System.out.println("path is "+u);
    			criticalpath.add(u);
    			if(u==inputG.dest)
    			{
    		//	System.out.println("extis a crictical apth");
    			this.listOfAllCriticalPAths.add(criticalpath);
    			}
    			BfsCritical(u,criticalpath);
    		}
    	}
    	if(!flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath.remove(criticalpath.size()-1);
    	}
    }
    

    void findCriticalPaths1() {
    	List<Vertex>[] pathCritical=new LinkedList[inputG.size];
    	int i=0;
    	for(Vertex u:inputG)
    	{
    		u.seen=false;
    		pathCritical[i]=new LinkedList<>();
    		i++;
    	}
    	i=0;
    	for(Vertex u:inputG)
    	{
    		
    		if(!u.seen&&u.slack==0)
    		{
    			pathCritical[i].add(u);
    			System.out.println("path is "+u);
    			BfsCritical1(u,pathCritical[i]);
    		/*	if(pathCritical[i].size()>0)
    			{
    				//listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		
    		}
    		i++;
    	}
    //	System.out.println("end");
    }
    void BfsCritical1(Vertex v,List<Vertex> criticalpath)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
    		if(u.slack==0)
    		{
    			flagToathwithv=true;
    			System.out.println("path is "+u);
    			criticalpath.add(u);
    			if(u==inputG.dest)
    			{
    	//		System.out.println("extis a crictical apth");
    			this.listOfAllCriticalPAths.add(criticalpath);
    			
    			}
    			BfsCritical1(u,criticalpath);
    		}
    	}
    	if(!flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath.remove(criticalpath.size()-1);
    	}
    }
    
    void findCriticalPaths3() {
    	List<Vertex>[] pathCritical=new LinkedList[inputG.size];
    	int i=0;
    	for(Vertex u:inputG)
    	{
    		u.seen=false;
    		pathCritical[i]=new LinkedList<>();
    		i++;
    	}
    	i=0;
    	for(Vertex u:inputG)
    	{
    		
    		if(!u.seen&&u.slack==0&&u!=inputG.src)
    		{
    			pathCritical[i].add(u);
    			System.out.println("path is "+u);
    			BfsCritical3(u,pathCritical[i]);
    		/*	if(pathCritical[i].size()>0)
    			{
    				//listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		
    		}
    		i++;
    	}
   // 	System.out.println("end");
    }
    void/*List<Vertex>*/ BfsCritical3(Vertex v,List<Vertex> criticalpath)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
    		if(u.slack==0)
    		{
    			flagToathwithv=true;
    	//		System.out.println("path is "+u);
    			if(u==inputG.dest)
    			{
    			System.out.println("extis a crictical apth");
    			this.listOfAllCriticalPAths.add(criticalpath);
    			//criticalpath.remove(criticalpath.size()-1);
    			}
    			else{
    				criticalpath.add(u);		
    			}
    			BfsCritical3(u,criticalpath);
    		}
    	}
    	if(flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath.remove(criticalpath.size()-1);
    	}
    	//return criticalpath;
    }


    
    void findCriticalPaths4() {
    	List<Vertex>[] pathCritical=new LinkedList[90];
    	int i=0;
    	for(Vertex u:inputG)
    	{
    		u.seen=false;
    		pathCritical[i]=new LinkedList<>();
    		i++;
    	}
    	for(int k=inputG.size;k<90;k++)
    	{
    		pathCritical[k]=new LinkedList<>();
    	}
    	i=0;
    	for(Vertex u:inputG)
    	{
    		
    		if(!u.seen&&u.slack==0&&u!=inputG.src)
    		{
    			pathCritical[glabali].add(u);
    	//		System.out.println("path is "+u);
    			BfsCritical4(u,pathCritical);
    		/*	if(pathCritical[i].size()>0)
    			{
    				//listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		
    		}
    		i++;
    	}
 //   	System.out.println("end");
    }
    void BfsCritical4(Vertex v,List<Vertex>[] criticalpath)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
    		if(u.slack==0)
    		{
    			flagToathwithv=true;
    			System.out.println("path is "+u);
    			if(u==inputG.dest)
    			{
    			System.out.println("extis a crictical apth");
    			this.listOfAllCriticalPAths.add(criticalpath[glabali]);
    			//criticalpath.remove(criticalpath.size()-1);
    			
    			criticalpath[glabali+1].addAll(criticalpath[glabali]);
    			this.glabali++;
    			}
    			else{
    				criticalpath[glabali].add(u);		
    			}
    			BfsCritical4(u,criticalpath);
    		}
    	}
    	if(flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath[glabali].remove(criticalpath[glabali].size()-1);
    	}
    	//return criticalpath;
    }



    

    void findCriticalPaths5() {
    	//List<Vertex>[] pathCritical=new LinkedList[900];
    	List<List<Vertex>> pathCritical=new ArrayList<>();
    	int i=0;
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
    	Vertex dummySrc=inputG.src;
    	for(Edge e:dummySrc.adj)
    	{
    		Vertex u=e.otherEnd(dummySrc);
    		
    		if(!u.seen&&u.slack==0&&u!=inputG.src)
    		{
    			//adding a linked list object whenever golbali is incremented
    			pathCritical.add(glabali, new LinkedList<>());
    			//pathCritical[glabali].add(u);
    			
    			pathCritical.get(glabali).add(u);
    			
    		//	System.out.println("path is "+u);
    			BfsCritical5(u,pathCritical);
    		/*	if(pathCritical[i].size()>0)
    			{
    				//listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		
    		}
    		i++;
    	}
    	//System.out.println("end");
    }
    void BfsCritical5(Vertex v,List<List<Vertex>> criticalpath)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
    		if(u.slack==0&&v.latestTime==u.latestTime-u.d)
    		{
    			flagToathwithv=true;
    		//	System.out.println("path is "+u);
    			if(u==inputG.dest)
    			{
    			//System.out.println("extis a crictical apth");
    				
    				
    			this.listOfAllCriticalPAths.add(criticalpath.get(glabali));
    			
    			//criticalpath.remove(criticalpath.size()-1);
    			criticalpath.add(glabali+1, new LinkedList<>());
    		
    			criticalpath.get(glabali+1).addAll(criticalpath.get(glabali));
    			this.glabali++;
    			}
    			else{
    				criticalpath.get(glabali).add(u);		
    			}
    			BfsCritical5(u,criticalpath);
    		}
    	}
    	if(flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath.get(glabali).remove(criticalpath.get(glabali).size()-1);
    	}
    	//return criticalpath;
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    void findCriticalPaths2() {
    	List<Vertex>[] pathCritical=new LinkedList[inputG.size];
    	int i=0;
    	for(Vertex u:inputG)
    	{
    		u.seen=false;
    		pathCritical[i]=new LinkedList<>();
    		i++;
    	}
    	i=0;
    	for(Vertex u:inputG)
    	{
    		
    		if(!u.seen&&u.slack==0)
    		{
    			pathCritical[i].add(u);
    			System.out.println("path is "+u);
    			BfsCritical2(u,pathCritical,i);
    		/*if(pathCritical[i].size()>0)
    			{
    				// listOfAllCriticalPAths.add(pathCritical[i]);
    			}*/
    		}
    		i++;
    	}
    	System.out.println("end");
    }//if single criticalpth is used for same source we can have multiple paths sso the paths are overriden
    void BfsCritical2(Vertex v,List<Vertex> criticalpath[],int i)
    {
    	v.seen=true;
    	boolean flagToathwithv=false;
    	for(Edge e:v.adj)
    	{
    		Vertex u=e.otherEnd(v);
    		if(u.slack==0)
    		{
    			flagToathwithv=true;
    			//System.out.println("path is "+u);
    			criticalpath[i].add(u);
    			if(u==inputG.dest)
    			{
    		//	System.out.println("extis a crictical apth");
    			this.listOfAllCriticalPAths.add(criticalpath[i]);
    			
    			i++;
    			criticalpath[i].addAll(criticalpath[i-1]);
    			}
    			BfsCritical2(u,criticalpath,i);
    		}
    	}
    	if(!flagToathwithv)
    	{
    		//criticalpath.removeAll(criticalpath);
    		criticalpath[i].remove(criticalpath[i].size()-1);
    	}
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
