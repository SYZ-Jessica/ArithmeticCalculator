
public class ArrayStack<E> {
	//holds the stack elements
	private E[] S;
	private int top=-1;
	private int increment=5;
	
	public ArrayStack() {
		this.S=(E[])new Object[5];
	}
	public int size() {
		return top+1;
	}
	public void push(E val) {
		if(this.size()==this.S.length){
			// if the stack is full, increase the size
			increament();
		}
		top=top+1;
		S[top]=val;
		
	}
	public E pop() {
		// if the stack is empty
		if(isEmpty()) {
			return null;
		}else {
			E popElement=S[top];
			S[top]=null;
			top=top-1;
			return popElement;
		}
	}
	public E top() {
		// if the stack is empty
		if(isEmpty()) {
			return null;
		}else {
			return S[top];	
		}
		
	}
	public void increament(){
		E[] newArray=(E[])new Object[S.length+increment];
		for(int a=0;a<S.length;a++) {
			//copy elements in the old array to the new one
			newArray[a]=S[a];
		}
		S=newArray;
		newArray=null;
	}
	public boolean isEmpty(){
		if(top<0)
			return true;
		else
			return false;
	}
	public void print() {
		for(E element:S) {
			System.out.print(element+" ");
		}
		System.out.println();
	}
}
