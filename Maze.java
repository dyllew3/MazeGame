import java.util.*;
import java.lang.Integer;


public class Maze{

    public static final int BLACK = 0;
    public static final int WHITE = 1;
    private int[][] board;
    private Graph build;
    public static final int squareLen = 1;
    private int x;
    private int y;
    
    public Maze(int x,int y){
	
	this.x = x;
	this.y = y;
	board = new int[x][y];
	build = new Graph(x * y);
	
    }

    
    
    
    public void create(){
	Graph newBoard = new Graph(this.x * this.y);
	int dim = this.x*this.y;
	Random weight = new Random();
	Random edge = new Random();
	for(int i = 0; i < this.x; i++){
	    for(int j = 0; j < this.y; j++ ){
		int graphPos = (i * this.y) + j;
		if(i + 1 < this.x)
		    newBoard.addEdge(graphPos,graphPos+y,weight.nextInt(dim));
		if(j + 1 < this.y)
		    newBoard.addEdge(graphPos,graphPos+1,weight.nextInt(dim));
	    }
	}
	build = newBoard.prim(0,newBoard);
	System.out.println("It is connected: " + build.isConnected());
	
	
    }
    
    public void play(){
	StdDraw.setCanvasSize(500,500);
	StdDraw.setXscale(0,this.y*2);
	StdDraw.setYscale(0,this.x*2);
	StdDraw.clear(StdDraw.BLACK);
	this.create();
	StdDraw.setPenColor(StdDraw.WHITE);
	for(int i = 0; i < this.x*this.y; i++){
	    int y = i % this.y;
	    int x = i/this.y;
	    StdDraw.filledSquare( (2*x) + 0.5,(2*y) + 0.5,0.5);
	    Iterator<Integer> adjTest =  build.adj(i).iterator();
	    
	    while(adjTest.hasNext()){
		int pos  = adjTest.next();
		int posX =  (pos / this.y) - x;
		
		int posY = (pos % this.y) - y;
		StdDraw.filledSquare((posX + (2*x)) + 0.5,(posY+ 2*y) + 0.5,0.5);
	    }
	    
	}
    }
    public Graph getGraph(){
	return this.build;
    }
    
    public static void main(String [] args){
	Maze testMaze = new Maze(15,15);
	//testMaze.create();
	testMaze.play();
    }
}
