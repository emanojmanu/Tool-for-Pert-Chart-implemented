/**
 * Class to represent a graph
 *  @author rbk
 *
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Graph implements Iterable<Vertex> {
    List<Vertex> v; // vertices of graph
    int size; // number of verices in the graph
    boolean directed;  // true if graph is directed, false otherwise
    Set<Vertex> withoutincomingedges;
    Set<Vertex> withoutgoingedges;

    Vertex src;
    Vertex dest;
    /**
     * Constructor for Graph
     * 
     * @param size
     *            : int - number of vertices
     */
    Graph(int size) {
	this.size = size;
	this.v = new ArrayList<>(size + 1);
	
	this.v.add(0, null);  // Vertex at index 0 is not used
	this.directed = false;  // default is undirected graph
	// create an array of Vertex objects
	withoutincomingedges=new HashSet<>(size+1);
	withoutgoingedges=new HashSet<>(size+1);
	for (int i = 1; i <= size; i++)
	{
		Vertex ver=new Vertex(i);
	    this.v.add(i,ver);
    	this.withoutgoingedges.add(ver);
    	this.withoutincomingedges.add(ver);
	}
	src=this.v.get(size-1);
	dest=this.v.get(size);
	
/*	this.v.add(new Vertex(size+1));
	this.v.add(new Vertex(size+2));*/
	
	
    }

    /**
     * Find vertex no. n
     * @param n
     *           : int
     */
    Vertex getVertex(int n) {
	return this.v.get(n);
    }
    
    /**
     * Method to add an edge to the graph
     * 
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    void addEdge(Vertex from, Vertex to, int weight) {
	Edge e = new Edge(from, to, weight);
	if(this.directed) {
	//	this.withoutgoingedges.remove(from);
	    from.adj.add(e);
	  //  this.withoutincomingedges.remove(to);
            to.revAdj.add(e);
	} else {
	    from.adj.add(e);
	    to.adj.add(e);
	}
    }

    /**
     * Method to create iterator for vertices of graph
     */
    public Iterator<Vertex> iterator() {
	Iterator<Vertex> it = this.v.iterator();
	it.next();  // Index 0 is not used.  Skip it.
	return it;
    }

    // Run BFS from a given source node
    // Precondition: nodes have already been marked unseen
    public void bfs(Vertex src) {
	src.seen = true;
	src.d = 0;
	Queue<Vertex> q = new LinkedList<>();
	q.add(src);
	while(!q.isEmpty()) {
	    Vertex u = q.remove();
	    for(Edge e: u.adj) {
		Vertex v = e.otherEnd(u);
		if(!v.seen) {
		    v.seen = true;
		    v.d = u.d + 1;
		    q.add(v);
		}
	    }
	}
    }

    // Check if graph is bipartite, using BFS
    public boolean isBipartite() {
	for(Vertex u: this) {
	    u.seen = false;
	}
	for(Vertex u: this) {
	    if(!u.seen) {
		bfs(u);
	    }
	}
	for(Vertex u:this) {
	    for(Edge e: u.adj) {
		Vertex v = e.otherEnd(u);
		if(u.d == v.d) {
		    return false;
		}
	    }
	}
	return true;
    }


    // read a directed graph using the Scanner interface
    public static Graph readDirectedGraph(Scanner in) {
	return readGraph(in, true);
    }
    
    // read an undirected graph using the Scanner interface
    public static Graph readGraph(Scanner in) {
	return readGraph(in, false);
    }
    
    public List<Vertex> TopologicalOrdering1()
    {
    
    	List<Vertex> toplist=new LinkedList<>();
    	for(Vertex u:this)
    	{
    		u.indegree=u.revAdj.size();
    	}
    	Queue<Vertex> q=new LinkedList<>();
         int count=0;
    	for(Vertex u:this)
    	{
    		if(u.indegree==0)
    		{
    			q.add(u);
    		}
    	}
    	Vertex u=null;
    	while(!q.isEmpty()){
    		u=q.remove();
    	toplist.add(u);
    		count++;
    		for(Edge e:u.adj)
    		{
    			Vertex v=e.otherEnd(u);
    			v.indegree--;
    			if(v.indegree==0)
    			{
    				q.add(v);
    			}
    			
    		}
    	}
  //  	System.out.println("asas");
        if(count!=this.size)
         {
	            System.out.println("not a DAG");
         }
    return toplist;
    }
    
    
    public List<Vertex> TopologicalOrdering2()
    {
    
    	for(Vertex u:this)
    	{
    		u.seen=false;
    	}
    	
    	List<Vertex> l=new ArrayList<>();
    	//Vertex u=this.src;
    	for(Vertex u:this)
    	{
    		if(!u.seen)
    		{
    			//System.out.println("vertex now" +u);
    			Dfs_Visit(u,l);
    		}
    	}
   // 	System.out.println("jfnda");
    	return l;
    }
    

    public List<Vertex> TopologicalOrdering3()
    {
    
    	for(Vertex u:this)
    	{
    		u.seen=false;
    	}
    	
    	List<Vertex> l=new ArrayList<>();
    	Vertex u=this.src;
    	while(u!=null)
    	{
    		if(!u.seen)
    		{
    			System.out.println("vertex now" +u);
    			Dfs_Visit(u,l);
    		}
    		for(Vertex v:this)
    		{
    			if(!v.seen)
    			{
    				u=v;
    				break;
    			}
    			else{
    				u=null;
    			}
    		}
    	}
  //  	System.out.println("jfnda");
    	return l;
    }
    
    
    private void Dfs_Visit(Vertex u, List<Vertex> l) {
		// TODO Auto-generated method stub
		
    	//if(!u.seen)
    	{
    		for(Edge e:u.adj)
    		{
    			Vertex v=e.otherEnd(u);
    			if(!v.seen)
    			{
    				Dfs_Visit(v, l);
    			}
    		}
    	}
    	u.seen=true;
    	l.add(0, u);   	
	}
	public static Graph readGraph(Scanner in, boolean directed) {
	// read the graph related parameters
	int n = in.nextInt(); // number of vertices in the graph
	int m = in.nextInt(); // number of edges in the graph

	// create a graph instance
	Graph g = new Graph(n);
	g.directed = directed;
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	    int w = in.nextInt();
	    g.addEdge(g.getVertex(u), g.getVertex(v), w);
	}
	
	
	//to add the edges between source and the the already available vertices and destination and the final verteex
	
	Vertex src=g.getVertex(n-1);
	Vertex dest=g.getVertex(n);
	Iterator<Vertex> nooutedgeiter=g.withoutgoingedges.iterator();
	Iterator<Vertex> noinedgeiter=g.withoutincomingedges.iterator();
	for(Vertex ver:g)
	{
		if(ver.revAdj.isEmpty()&&ver!=src)
		{
			g.addEdge(src, ver, 0);
		}
		else if(ver.adj.isEmpty()&&ver!=dest)
		{
			g.addEdge(ver, dest, 0);
		}
	}
	//System.out.println("ad");
	/*while(noinedgeiter.hasNext())
	{
		Vertex v=noinedgeiter.next();
		g.addEdge(src, v, 0);
	}
	while(nooutedgeiter.hasNext())
	{
		g.addEdge(nooutedgeiter.next(), dest, 0);
	}*/
	return g;
    }

}
