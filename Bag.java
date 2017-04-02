import java.util.Iterator;
import java.util.NoSuchElementException;
 

public class Bag<Value> implements Iterable<Value> {
    private Node<Value> top;
    private int size;

    private static class Node<Value>{
	private Value val;
	private Node<Value> next;
    }
    
    public Bag(){
	this.size = 0;
	this.top = null;
	
    }
    public boolean isEmpty(){

	return size == 0;
    }
    public int getSize(){
	
	return this.size;
    }

    public void add(Value value){
	Node<Value> prev = this.top;
	top = new Node<Value>();
	top.val = value;
	top.next = prev;
    }
   
     
    public Iterator<Value> iterator()  {
	return new ListIterator<Value>(this.top);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Value> implements Iterator<Value> {
	private Node<Value> current;

	public ListIterator(Node<Value> first) {
	    current = first;
	}

	public boolean hasNext()  {
	    return current != null;
	}
	public void remove(){
	    throw new UnsupportedOperationException();
	}

	public  Value next() {
	    if (!hasNext()) throw new NoSuchElementException();
	    Value val = current.val;
	    current = current.next;
	    return val;
	}
	    
      
    }
}
