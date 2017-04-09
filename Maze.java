import java.util.*;
import java.lang.Integer;
import java.awt.event.*;

public class Maze {

    public static final boolean BLACK = false;
    public static final boolean WHITE = true;
    private boolean[][] board;
    private Graph build;
    public static final int squareLen = 1;
    private final int ROOT = 0;
    public final double SQUARELEN = 0.5;
    private int x;
    private int y;
    
    public Maze(int x,int y){
	
	this.x = x;
	this.y = y;
	board = new boolean[2*x][2*y];
	build = new Graph(x * y);
	
    }

    
    
    //creates the new graph, adds on the edges and devises a minimal spanning
    //tree. This spanning tree is the blueprint for the graph
    public void create(){
	build = new Graph(this.x * this.y);
	int dim = this.x*this.y;
	Random weight = new Random();
	Random edge = new Random();
	for(int i = 0; i < this.x; i++){
	    for(int j = 0; j < this.y; j++ ){
		int graphPos = (i * this.y) + j;
		board[2*i][2*j] = WHITE;
		if(i + 1 < this.x){
		    build.addEdge(graphPos,graphPos+y,weight.nextInt(dim));
		}
		if(j + 1 < this.y){
		    
		    build.addEdge(graphPos,graphPos+1,weight.nextInt(dim));
		}
	    }
	}
	build = Graph.prim(ROOT,build);
	//System.out.println("It is connected: " + build.isConnected());
	
	
    }
    
    //this method draws the maze with the start point and the endpoint
    //as well as making sure that the edges between vertices are draw. The
    // board is 2*x by 2*y where every a vertex is when both the x and y are
    //even and an edge is when either the x or y or both are odd
   public void drawBoard(){
StdDraw.setPenColor(StdDraw.WHITE);
	for(int i = 0; i < this.x*this.y; i++){
	    int y = i % this.y;
	    int x = i/this.y;
	    StdDraw.filledSquare( (2*x) + SQUARELEN,(2*y) +  SQUARELEN, SQUARELEN);
	    Iterator<Integer> adjTest =  build.adj(i).iterator();
	    
	    while(adjTest.hasNext()){
		int pos  = adjTest.next();
		int posX =  (pos / this.y) - x;
		
		int posY = (pos % this.y) - y;
		StdDraw.filledSquare((posX + (2*x)) + SQUARELEN,(posY+ 2*y) +SQUARELEN,SQUARELEN);
		board[2*x + posX][2*y + posY] = WHITE;
	    }
	    
	}
	StdDraw.setPenColor(StdDraw.RED);
	StdDraw.filledSquare( SQUARELEN,SQUARELEN,SQUARELEN);
	StdDraw.setPenColor(StdDraw.GREEN);
	StdDraw.filledSquare( (this.x*2) - 1.5,this.y*2 -  1.5,SQUARELEN);
    }


    //starts game, draws the board and allows the player to move
    //in the maze and determines if they have won or quit the game
    public void play(){
	StdDraw.setCanvasSize(700,700);
	StdDraw.setXscale(0,this.y*2);
	StdDraw.setYscale(0,this.x*2);
	StdDraw.clear(StdDraw.BLACK);
	this.create();
	this.drawBoard();
	int curX = 0;
	int curY = 0;
	boolean finished = false;
	while(!finished){
	    
	    try{
		Thread.sleep(200);
	    }catch(Exception e){
	    }
	    
	    if(StdDraw.isKeyPressed('w')){
		if(move(curX,curY,curX,curY + 1))
		    curY++;
		
	
	    }
	    else if(StdDraw.isKeyPressed('a')){
		if(move(curX,curY,curX-1,curY))
		    curX--;
	    }
	    else if(StdDraw.isKeyPressed('s')){
		
		if(move(curX,curY,curX,curY - 1))
		    curY--;


	    }
	    else if(StdDraw.isKeyPressed('d')){
		if(move(curX,curY,curX + 1,curY))
		    curX++;
	    }
	    else if(StdDraw.isKeyPressed('q')){
		break;
	    }
	    else if(curX == 2*(this.x - 1) && curY == 2 *(this.y - 1))
		finished = true;
	    StdDraw.resetKeyPress();
	    
	}
	if(finished)
	    System.out.println("Congratulations you win!");
	else
	    System.out.println("Sorry, but you lose");
    }

    public boolean move(int prevX,int prevY,int nextX,int nextY){
	int difX = prevX - nextX;
	int difY = prevY - nextY;
	//StdDraw.setPenColor(StdDraw.GREEN);
	//StdDraw.filledSquare( (this.x*2) - 1.5,this.y*2 -  1.5,0.5);
	if(isValid(nextX,nextY) && difX >= -1 && difX <= 1 && difY >= -1 && difY <= 1  ){
	    StdDraw.setPenColor(StdDraw.RED);
	    StdDraw.filledSquare(nextX + 0.5,nextY + 0.5, 0.5);
	    StdDraw.setPenColor(StdDraw.WHITE);
	    StdDraw.filledSquare(prevX + 0.5,prevY + 0.5, 0.5);
	    //System.out.println("X is " + nextX + " Y is " + nextY );
	    return true;
	}
	else{
	    //System.out.println("Invalid move!\n");
	    return false;
	}
	
    }
    private boolean isValid(int x,int y){
	if (x < this.x * 2 && y < this.y * 2  && x >= 0  && y >=0  &&board[x][y] == WHITE)
	    return true;
	return false;
	
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
