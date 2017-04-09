import java.util.*;
import java.lang.Integer;

public class Graph{

    //private double tolerance;
    
    private static class DFS{
	private boolean[] visited;
	private int edgeTo[];
	private int root;
	
	public DFS(Graph g, int root){
	    this.root = root;
	    visited = new boolean[g.getSize()];
	    edgeTo = new int[g.getSize()];
	    this.dfs(g,this.root);
	}
	private void dfs(Graph g, int v){
	    visited[v] = true;
	    for(int adj : g.adj(v)){
		if(!visited[adj]){
		    this.dfs(g,adj);
		    edgeTo[adj] = v;
		}

	    }
	}
	public boolean allVisited(){
	    for(int i = 0; i < this.visited.length; i++){
		if(!visited[i]) return false;
	    }
	    return true;
	    
	}

    }

    
    private int edges;
    private int  vertices;
    private Bag<Integer>[] adjacent;
    private Integer [][] weights;
    public PriorityQueue<Weight> order;
    private Unionfind connect;

    public Graph(int numVertices){
	//this.tolerance = 0;
	this.vertices = numVertices;
	order = new PriorityQueue<Weight>();
	connect = new Unionfind(numVertices);
	weights = new Integer[this.vertices][this.vertices];
	adjacent = (Bag<Integer>[])new Bag[this.vertices];
	for (int i = 0; i < this.vertices ;i++){
	    adjacent[i] = new Bag<Integer>();
	    for(int j =0 ; j < this.vertices;j++){
		weights[i][j] = null;
		weights[j][i] = null;
	    }
	}
	edges = 0;
		
    }

    

    //applies depth first search to a graph and returns the DFS class
    public static DFS depthFirstSearch(Graph g, int root){
	return new DFS(g,root);
	
    }
    //adds an edge to the graph
    public void addEdge(int v,int w, int weight){
	order.add(new Weight(v,w,weight));
	connect.join(v,w);
        weights[v][w] = weight;
	weights[w][v] = weight;
	adjacent[v].add(w);
	adjacent[w].add(v);
	this.edges++;
    }

    //returns the Number of edges in the graph
    public int numEdges(){
	return this.edges;
    }

    //returns all weights of the graph
    public Integer[][] getWeights(){
	return this.weights;
    }
    
    //gets number of vertices in the graph
    public int getSize(){

	return vertices;
    }
    public boolean isAdjacent(int v, int w){
	return weights[v][w] != null || weights[w][v] != null;
	
    }
    

    //returns all adjacent vertices for a vertex
    public Iterable<Integer> adj(int vertex){

	return this.adjacent[vertex];
    }
    public boolean isConnected(){
	int id = connect.find(0);
	for(int  i = 1; i < this.vertices; i++){
	    if(id != connect.find(i))
		return false;
	}
	return true;
    }
    
    /**
     * implements prim's algorithm
     * Prim's algorithm is one algorithm used to find 
     * the minimal spanning tree for a weighted graph
     * The algorithm goes as followed:
     * 1)Initialize a tree with a single vertex,chosen arbitrarily from the graph
     * 2) Grow the tree by one edge: of the edges that connect the tree to 
     *     vertices not yet in the tree, find the minimum-weight edge, 
     *    and transfer it to the tree.
     *
     * 3) Repeat step 2 (until all vertices are in the tree).
     *
     *@param root starting point for the algorithm on the graph 
     *@param g graph that the algorithm will be applied to
     *@return new Graph which is the minimum spanning tree
**/
    public static  Graph  prim(int root,Graph g){
	if(!g.isConnected()) return null;
	//make new graph
	Graph s = new Graph(g.getSize());
	
	MinPQ<Integer,Integer> order = new MinPQ<Integer,Integer>(g.getSize());
	boolean[] inTree = new boolean[g.getSize()]; 
	//root is already in tree
	inTree[root] = true;
	
	int[] edgeWith  = new int[g.getSize()];
	Integer[] weights = new Integer[g.getSize()];
	for(int i = 0; i < g.getSize(); i++){
	    //sets all default edges to no vertex
	    edgeWith[i] = -1;
	    weights[i] = Integer.MAX_VALUE;
	    if(i != root) order.insert(i,Integer.MAX_VALUE);
	    else order.insert(root,0);
	}
	weights[root] = 0;
	Integer[][] values = g.getWeights() ;
	for(int i = 0; i < values.length; i++){
	    //System.out.println("Weights for " + i + " are " + Arrays.toString(values[i] ));  
	}
	//System.out.println("Original ");
	while(!order.isEmpty()){
	    
	    MinPQ<Integer,Integer>.Token vToken = order.delMin();
	    int vertex = vToken.key,weight = vToken.value;
	    Iterator<Integer> adjs =  g.adj(vertex).iterator();
	    int w;
	   
	    while( adjs.hasNext()){
		w = adjs.next();

		//checks if already in tree and less than current min weight to
		//vertex and if it is adjacent
		if(!inTree[w] && values[w][vertex].compareTo(weights[w]) < 0 && values[w][vertex] != null ){
		    
		    edgeWith[w] = vertex;
		    order.decreaseKey(w,values[w][vertex]);
		    weights[w] = values[w][vertex];
		    
		}
		//marks as having been in the tree
		inTree[vertex] = true;
		    
	    }
	     
	    
	}
	for(int i =0 ; i < edgeWith.length; i++){
	    if(i != root)
		s.addEdge(i,edgeWith[i],weights[i]);
	}
	//System.out.println("Weights are " + Arrays.toString(weights));
	return s;

    }

    
    //returns weights in heap form
    public PriorityQueue<Weight> edgeOrder(){
	return this.order;
    } 
    
    
    //this gets the max number of edges for a given number of
    //vertices assuming vertex cannot have edge to itself
    public static int maxEdges(int numVertices){
	if((numVertices/2) >= (Integer.MAX_VALUE/(numVertices - 1)) ){
		return Integer.MAX_VALUE;
	}
	else if (numVertices < 0){
	    return 0;
	}
	else{
	    return (numVertices/2) * (numVertices - 1);
	}
	
    }
    /**
       Kruskal's algorithm is also used to find the minimal spanning tree
       of graph much like prim's algorithm however it doesn't require a root
       and has a smaller time complexity compared to prim's algorithm


       @return minimal spanning tree graph
     **/
    
    public static Graph kruskal(Graph g){
	if(!g.isConnected())return null;
	MinPQ<Integer,Integer> edges = new MinPQ<Integer,Integer>(g.numEdges());
	Graph spanTree = new Graph(g.getSize());
	PriorityQueue<Weight> order = g.edgeOrder();
	Unionfind set = new Unionfind(g.getSize());
	while(order.peek() != null){
	    Weight toInsert = order.poll();
	    if(set.find(toInsert.v) != set.find(toInsert.w)){
		set.join(toInsert.v,toInsert.w);
		spanTree.addEdge(toInsert.v,toInsert.w,toInsert.value);
	    }
	}
	
	return spanTree;
    }
    
    public static void main(String [] args){
	Graph test = new Graph(10) ;
	
	DFS testDFS = test.depthFirstSearch(test,0);
	
	Random weight = new Random();

	Random adj = new Random();
	
	for(int i = 0 ; i < 10 ; i++){
	    for(int j = adj.nextInt(10) ; j < 10 ;j++){
		int vert = adj.nextInt(10);
		if(!test.isAdjacent(i,vert) && i != vert  ){ 
		test.addEdge(i,vert,weight.nextInt(10) + 1);
		}
	    }
	}

	Integer [][] weights = test.getWeights();
	for(int i = 0 ; i < 10; i++){
	    Iterator<Integer> adjTest =  test.adj(i).iterator();
	    String adjacents = "";
	    while(adjTest.hasNext()){
		adjacents += adjTest.next() + " ";
	    }
	    System.out.println(i +  " has " + adjacents);
	}

	System.out.println(" The weights are: ");
	Graph primTree = Graph.kruskal(test);
	for(int i = 0 ; i < weights.length; i++){
	    System.out.println(Arrays.toString(weights[i]));
	}
	for(int i = 0 ; i < 10; i++){
	    Iterator<Integer> adjTest =  primTree.adj(i).iterator();
	    String adjacents = "";
	    while(adjTest.hasNext()){
		adjacents += adjTest.next() + " ";
	    }
	    System.out.println(i +  " has " + adjacents);
	}
	
    }
    
    


}
