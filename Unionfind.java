public class Unionfind{

    private int size;
   
    private int[] id;
    private int[] rootSize;
    private boolean isEmpty;
    private boolean isFull;
    
    public Unionfind(int size){
	this.size = size;
	id = new int[size];
	rootSize = new int[size];
	for(int i =0; i < this.size; i++){
	    id[i] = i;
	    rootSize[i] = 0;
	}
	
	isEmpty = true;
	
    }

    public void join(int vert1,int vert2){
	isEmpty = false;
	int i = find(vert1);
	int j = find(vert2);
	if(rootSize[i] == 0)
	    rootSize[i] = 1;
	if(rootSize[j] == 0)
	    rootSize[j] = 1;
	if(i == j)return ;
	if(rootSize[i] < rootSize[j]){
	    id[i] = j;
	    rootSize[j] += rootSize[i];
	}
	else{
	    id[j] = i;
	    rootSize[i] += rootSize[j];
	}
	this.size--;
    }

    public int find(int i){
	while(i != id[id[i]]){
	    i = id[i];
	}
	return i;
    }
    
    public int numberOfCircles(){
	if(isEmpty)return id.length;
	else return  this.size;
    }
}
