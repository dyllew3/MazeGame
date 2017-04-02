class Weight implements Comparable<Weight>  {
    public int v;
    public int w;
    public int value;
    public boolean directed = false;

    public Weight(int v,int w,int weight,boolean directed){
	this.v = v;
	this.w = w;
	this.value = weight;
	this.directed = directed; 
	
	
    }
    public Weight(int v,int w,int weight){
	this.v = v;
	this.w = w;
	this.value = weight;

    }
      
    public int compareTo(Weight o){
	if(value > this.value){
	    return 1; 
	}
	else if(value == this.value){
	    return 0;
	}
	else {

	    return -1;
	}
	
    }


}
