import java.util.HashMap;
import java.util.Arrays;

public class MinPQ<Key, Value extends Comparable<? super Value> >  {

    //keeps track of size
    private int size;

    //last position in the array
    private int index;
    private HashMap<Key,Integer> pos;
    private HashMap<Key,Value> keyVal;
    private Key[] keys;
    private Value[] values;

    //private Comparator<Key> comparator; 
    
    public MinPQ(){
	this(11);
    }
    
    //intialise min PQ
    public MinPQ(int capacity){
	size = capacity;
	index = 0;
	keys = (Key []) new Object[capacity + 1];
	values =(Value []) new Comparable[capacity + 1];
	pos = new HashMap<Key, Integer>();
	keyVal = new HashMap<Key,Value>();
    }
    
    
    private void swim(int pos){
       
	while(pos > 1 && values[pos].compareTo(values[pos/2]) < 0  ){
	    swap(pos,pos/2);
	    pos /= 2;
	}
	
	
	
    }
    private void sink(int k){
	while(2 * k <= this.index){
	    int j = 2 * k;
	    //if j is larger only compare to j + 1
	    if( j < index && larger(j,j+1) ) j++;
	    if(!larger(k,j) ) break;
	    swap(k,j);
	    k = j;
	    
	}

    }
    //swaps two values and notes their positions
    private void swap(int i, int j){
	this.pos.put(keys[i],j);
	this.pos.put(keys[j],i);
	Key tempKey = keys[i];
	Value tempVal = values[i];
	keys[i] = keys[j];
	values[i] = values[j];
	keys[j] = tempKey;
	values[j] = tempVal;

    }

    //check if i is larger than j
    private boolean larger(int i, int j){
	
	return values[i].compareTo(values[j]) >= 0 ;
	
    }
    
    //inserts into array at the end and then swims up
    public void insert(Key newKey, Value newVal){
	if(index == values.length - 1) resize(2 * values.length );
	++index;
	keys[index] = newKey;
	values[index] = newVal;
	//System.out.println("Key is " + newKey.toString());
	pos.put(keys[index],index);
	keyVal.put(newKey,newVal);
	swim(index);
	
    }
    
    
    //resizes the array
    private void resize(int newSize){
	
	this.size =newSize;
	Key [] temp = (Key[]) new Object[newSize];
	Value [] tempVal = (Value[]) new Comparable[newSize];
	for(int i = 0 ; i <= index ; i++){
	    temp[i] = keys[i];
	    tempVal[i] = values[i];
	}
	keys = temp;
	values = tempVal;
	

    }
    
    //decreases value with associated key
    public void decreaseKey(Key key, Value newVal){
	int posit = pos.get(key);
	//System.out.println("Key pos " + posit);
	if(newVal.compareTo(values[posit]) > 0) return;
	keyVal.put(key,newVal);
	values[posit] = newVal;
	swim(posit);
	/*
	for (Key name: pos.keySet()){

            String theKey =name.toString();
            String value = pos.get(name).toString();  
            System.out.println(theKey + " " + value);  
	    }
	*/

    }
    
    public Value[] printOut(){

	return values;
    }

    public Key[] getKeys(){
	return keys;
    }
    public class Token{
	public Key key;
	public Value value;
	public Token(Key key, Value value){
	    this.key = key;
	    this.value = value;
	}
    }

    
        
    public Token delMin(){
	swap(1,index);
	Key minKey = keys[index];
	Value minVal = values[index--];
	
	keys[index+1] = null;
	values[index + 1] = null;
	sink(1);
	if((index > 0) && (index == ( (size-1) / 4 ))) resize(size/2);
	return new Token(minKey,minVal);
    }

    public boolean isEmpty(){
	return index == 0;
    }
    
    public static void main(String[] args){
	MinPQ<Integer,Integer> test = new MinPQ<Integer,Integer>();
	test.insert(11,10);
	test.insert(12,5);
	test.insert(13,7);
	test.insert(14,3);
	test.insert(15,2);
	test.insert(16,8);
	test.insert(17,11);
	test.insert(18,6);
	test.insert(19,1);
	test.insert(20,9);
	test.insert(21,4);
	test.insert(22,12);
	test.insert(23,13);
	test.decreaseKey(22,1);
	while(!test.isEmpty()){
	System.out.println( Arrays.toString(test.printOut()) );
	test.delMin();
	}
    }
}

