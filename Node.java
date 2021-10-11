/**
 * Representing a Generic Node Class for use in an Expression Tree
 * @author Jesse Clegg
 * @version 1.0
 */
public class Node<T> {
	/*
	 * the generic value to be stored in this node
	 */
	private T value;
	/*
	 * the leftChild node of this node
	 */
	public Node<T> leftChild;
	/*
	 * the rightChild node of this node
	 */
	public Node<T> rightChild;
	
	 /*
	  * Constructs the default constructor with data and both right and left child set to null 
	 */
	Node() {
		this.value = null;
		this.leftChild = null;
		this.rightChild = null;
	}
	/*
	 * Constructs a node and assigns values to all fields
	 * @param token the value to be stored in this node
	 * @param leftChild the generic node object for left subtree
	 * @param rightChild the generic node object for the right subtree
	 */
	Node(T token, Node<T> leftChild, Node<T> rightChild) {
		this.value = token;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	/*
	 * Constructs a node when only a value is passed in and assigns that value while setting children to null
	 * @param token A generic data element to be stored in this node
	 */
	Node(T token) {
		this.value = token;
		this.leftChild = null;
		this.rightChild = null;
	}
	/*
	 * Returns this nodes leftChild
	 * @return the leftChild of this node
	 */
	public Node<T> getLeftChild() {
		return this.leftChild;
	}
	/*
	 * Returns this nodes leftChild
	 * @return the rightChild of this node
	 */
	public Node<T> getRightChild() {
		return this.rightChild;
	}
	/*
	 * Returns the value of the data stored in this node
	 * @return this nodes value
	 */
	public T getValue() {
		return this.value;
	}
	/*
	 * Sets the value in this not to the generic data passed in
	 * @param setValue the data to be stored in this node
	 */
	private void setValue(T setValue) {
		this.value = setValue;
	}
	/*
	 * Sets the leftChild for this tree
	 * @param leftPointer is the node passed to be stored in leftchild
	 */
	private void setLeftChild(Node<T> leftPointer) {
		this.leftChild = leftPointer;
	}
	/*
	 * Sets the rightChild for this tree
	 * @param rightPointer is the node passed to be stored in rightchild
	 */
	private void setRighChild(Node<T> rightPointer) {
		this.rightChild = rightPointer;
	}
	/*
	 * Overrides the equals method and determines if this node is equal to another object
	 * if that object is also of type Node and both values of the two nodes are equal
	 * @param nodeToComapare is the object passed in for comparison to this node
	 * @return boolean the value true or false if the two nodes are considered equal
	 */
	@Override
	public boolean equals(Object nodeToCompare) {
		if (nodeToCompare instanceof Node) {
			Node cast = (Node) nodeToCompare;
			if (this.getValue().equals((cast).getValue())) {
				return true;
			}
		}
		return false;
	}
	/*
	 * Overrides the toString method and returns a string including the type of this node,
	 * the data stored in this node.value and the data stored in the left and right child nodes
	 * if they exist or if they have data, otherwise returns null for those fields
	 * @return the output of that information in String format
	 */
	@Override
	public String toString() {
		String typeOfNode = getClass().getSimpleName();
		T dataStored = this.getValue();
		String leftChildData;
		String rightChildData;

		Node<T> leftChild;
		if (this.leftChild != null) {
			leftChildData = this.leftChild.getValue().toString();
		} else {
			leftChildData = null;
		}
		Node<T> rightChild;
		if (this.rightChild != null) {
			rightChildData = this.rightChild.getValue().toString();
		} else {
			rightChildData = null;
		}
		String output = "nodeType: [" + typeOfNode + "]\nvalueOf: [" + dataStored.toString() + "]\nleftChildValue: ["
				+ leftChildData + "]\nrightChildValue: [" + rightChildData + "]";
		return output;

	}

}
