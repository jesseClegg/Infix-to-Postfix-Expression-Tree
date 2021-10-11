/*
 * Representing a binary expression tree of generic Nodes
 * @author Jesse Clegg
 * @version 1.0
 */
import java.util.*;

public class ExpressionTree {
	/*
	 * Stores a reference to the root of this expression tree 
	 */
	private Node<String> root;
	/*
	 * Stores the string value of the infixExpression 
	 */
	private String infixExpression;
	/*
	 * Constructs ExpressionTree and assigns value of null to root and infixExpression
	 */
	ExpressionTree() {
		this.root = null;
		this.infixExpression = null;
	}
	/*
	 * Constructs an ExpressionTree from an infix expression
	 * @param infix is the infix expression to be stored in infixExpresion field and converted into an expression tree
	 */
	ExpressionTree(String infix) {
		this.infixExpression = infix;
		this.root = generateTree(convertToPostFix(sanitizeInput(this.infixExpression)));
	}
	/*
	 * Sanitizes the string expression passed in and converts the elements into an arrayList of Strings
	 * necesaary to account for multidigit numbers, decimals and to strip spaces
	 *@param infix The expression passed in to be cleaned and sorted by this expression 
	 *@return an arrayList of strings suitable to be evaluated by the convertToPostFix() method
	 */
	public ArrayList<String> sanitizeInput(String infix) {
		ArrayList<String> cleanInput = new ArrayList<String>();
		char token = ' ';
		for (int i = 0; i < infix.length(); i++) {
			token = infix.charAt(i);
			if (Character.isDigit(token) || token == '.') {
				StringBuilder multiDigitNumber = new StringBuilder();
				multiDigitNumber.append(String.valueOf(token));
				int index = i + 1;
				while (index != infix.length()
						&& (Character.isDigit(infix.charAt(index)) || infix.charAt(index) == '.')) {
					multiDigitNumber.append(infix.charAt(index));
					index++;
					i++;
				}
				cleanInput.add(String.valueOf(multiDigitNumber));
			}
			switch (token) {
			case '(':
			case ')':
			case '+':
			case '-':
			case '*':
			case '/':
				cleanInput.add(String.valueOf(token));
				break;
			default:
				break;
			}
		}
		return cleanInput;
	}
	/*
	 * Converts an arrayList infix expression into a postfix expression arrayList 
	 * Allows for faster and more accurate assembly of the tree when in postfix form
	 * @param sanitizedExpression an arraylist of strings, each string containing an item in the infix expression to be sorted, no spaces
	 * @return an arraylist of strings from sanitized expression that are now sorted into the order of a postfix expression
	 */
	public ArrayList<String> convertToPostFix(ArrayList<String> sanitizedExpression) {
		Stack<String> postfixStack = new Stack<String>();
		ArrayList<String> postfixExpression = new ArrayList<String>();
		String token = " ";
		for (int i = 0; i < sanitizedExpression.size(); i++) {
			token = String.valueOf(sanitizedExpression.get(i));
			if (isNumber(token)) {
				postfixExpression.add(token);
			} else if (isOpenParenthesis(token)) {
				postfixStack.push(token);
			} else if (isClosedParenthesis(token)) {
				while (isOpenParenthesis(postfixStack.peek()) == false) {
					postfixExpression.add(postfixStack.pop());
				}
			} else if (isOperator(token)) {
				while (postfixStack.isEmpty() == false
						&& checkPrecedence(postfixStack.peek()) >= checkPrecedence(token)) {
					if (isOpenParenthesis(postfixStack.peek()) == false) {
						postfixExpression.add(postfixStack.pop());
					} else {
						postfixStack.pop();
					}

				}
				//must be added outside of the loop to preserve associativity
				postfixStack.add(token);
			}
		}
		//when done walking the arrayList, append the contents left in the stack-excluding "("-to the postfix
		while (postfixStack.isEmpty() == false) {
			if (isOpenParenthesis(postfixStack.peek()) == false) {
				postfixExpression.add(postfixStack.pop());
			} else {
				postfixStack.pop();
			}
		}
		return postfixExpression;
	}

	
	/*
	 * Assembles an ExpressionTree of generic Node subtrees in an efficient manner acheived by use of a postfix formatting and a stack 
	 * @param postfixExpression the original infix converted to postfix for represented as an arraylist of Strings
	 * @return the root of this ExpressionTree by way of a reference to root
	 */
	
	public Node<String> generateTree(ArrayList<String> postfixExpression) {
		Stack<Node<String>> nodeStack = new Stack<Node<String>>();
		String token = " ";
		for (int i = 0; i < postfixExpression.size(); i++) {
			token = postfixExpression.get(i);
			if (isNumber(token)) {
				Node<String> leafNode = new Node<String>(token);
				nodeStack.push(leafNode);
			} else if (isOperator(token)) {
				Node<String> tempRight = nodeStack.pop();
				Node<String> tempLeft = nodeStack.pop();
				Node<String> OperatorNode = new Node<String>(token, tempLeft, tempRight);
				nodeStack.push(OperatorNode);
			}
		}
		this.root = nodeStack.pop();
		return this.root;
	}
	/*
	 * Assigns a precedence to strings passed in, all non operator or parenthesis return 0
	 * @param token the current string to be evaluated and assigned a precedence value to 
	 * @return returns a numerical value to establish an order of operations when compared to another string passed in to this function
	 * 
	 */
	public int checkPrecedence(String token) {
		int precedence = 0;
		if (token.equals("*") || token.equals("/")) {
			precedence = 2;
			return precedence;
		}
		if (token.equals("+") || token.equals("-")) {
			precedence = 1;
			return precedence;
		} else {
			precedence = 0;
			return precedence;
		}

	}
	/*
	 * Compares a string to "(" 
	 * Enhances code readability
	 * @param token is current string to be compared
	 * @return Returns boolean value if token is "("
	 */
	public boolean isOpenParenthesis(String token) {
		if (token.equals("(")) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * Compares a string to ")" 
	 * Enhances code readability
	 * @param token is current string to be compared
	 * @return Returns boolean value if token is ")"
	 */
	public boolean isClosedParenthesis(String token) {
		if (token.equals(")")) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * Compares a string to "+","-","/","*" 
	 * Enhances code readability
	 * @param token is current string to be compared
	 * @return Returns boolean value if token is one of the 4 operators
	 */
	public boolean isOperator(String token) {
		switch (token) {
		case "+":
			return true;
		case "-":
			return true;
		case "/":
			return true;
		case "*":
			return true;
		default:
			return false;
		}
	}
	/*
	 * Checks if a string represents a number, including decimals 
	 * Enhances code readability
	 * @param token is current string to be compared
	 * @return Returns boolean value if token is a number
	 */
	public boolean isNumber(String token) {
		if (Character.isDigit(token.charAt(0)) == true || token.charAt(0) == '.') {
			return true;
		} else {
			return false;
		}
	}
	/*
	 *Converts the reference to a list of nodes in postOrder into a string
	 *allows for string of traversal to be printed, and called with passing a node 
	 *@return string of this trees PostOrderTraversal
	 */
	public String getPostOrderTraversalString() {
		String postOrderTraversal = " ";
		ArrayList<Node> postOrderTraversalList = new ArrayList<Node>();
		traversePostOrder(this.root, postOrderTraversalList);
		StringBuilder postOrderString = new StringBuilder();
		for (int i = 0; i < postOrderTraversalList.size(); i++) {
			postOrderString.append(postOrderTraversalList.get(i).getValue());
		}
		postOrderTraversal = postOrderString.toString();
		return postOrderTraversal;
	}
	/*
	 *Traverses the expressionTree in postOrder and returns the nodes in that order
	 *@param node the root node of the tree to be traversed
	 * @param traversalList arrayList to store the traversal order, if declared inside of the recursion it would not store all the values
	 * @return arrayList of nodes in this tree in postOrder
	 */
	public ArrayList<Node> traversePostOrder(Node<String> node, ArrayList<Node> traversalList) {
		if (node == null) {
			return traversalList;
		}

		traversePostOrder(node.leftChild, traversalList);

		traversePostOrder(node.rightChild, traversalList);

		traversalList.add(node);
		return traversalList;
	}
	/*
	 *Converts the reference to a list of nodes in preOrder into a string
	 *allows for string of traversal to be printed, and called with passing a node 
	 *@return string of this trees PreOrderTraversal
	 */
	public String getPreOrderTraversalString() {
		String preOrderTraversal = " ";
		ArrayList<Node> preOrderTraversalList = new ArrayList<Node>();
		traversePreOrder(this.root, preOrderTraversalList);
		StringBuilder preOrderString = new StringBuilder();
		for (int i = 0; i < preOrderTraversalList.size(); i++) {
			preOrderString.append(preOrderTraversalList.get(i).getValue());
		}
		preOrderTraversal = preOrderString.toString();
		return preOrderTraversal;
	}

	/*
	 *Traverses the expressionTree in preOrder and returns the nodes in that order
	 *@param node the root node of the tree to be traversed
	 * @param traversalList arrayList to store the traversal order, if declared inside of the recursion it would not store all the values
	 * @return arrayList of nodes in this tree in preOrder
	 */
	public ArrayList<Node> traversePreOrder(Node<String> node, ArrayList<Node> traversalList) {
		if (node == null) {
			return traversalList;
		}

		traversalList.add(node);
		traversePreOrder(node.leftChild, traversalList);

		traversePreOrder(node.rightChild, traversalList);
		return traversalList;
	}
	/*
	 *Converts the reference to a list of nodes in inOrder into a string
	 *allows for string of traversal to be printed, and called with passing a node 
	 *@return string of this trees inOrderTraversal
	 */
	public String getInOrderTraversalString() {
		String inOrderTraversal = " ";
		ArrayList<Node> inOrderTraversalList = new ArrayList<Node>();
		traverseInOrder(this.root, inOrderTraversalList);
		StringBuilder inOrderString = new StringBuilder();
		for (int i = 0; i < inOrderTraversalList.size(); i++) {
			inOrderString.append(inOrderTraversalList.get(i).getValue());
		}
		inOrderTraversal = inOrderString.toString();
		return inOrderTraversal;
	}
	/*
	 *Traverses the expressionTree in InOrder and returns the nodes in that order
	 *@param node the root node of the tree to be traversed
	 * @param traversalList arrayList to store the traversal order, if declared inside of the recursion it would not store all the values
	 * @return arrayList of nodes in this tree in InOrder
	 */
	public ArrayList<Node> traverseInOrder(Node<String> node, ArrayList<Node> traversalList) {
		if (node == null) {
			return traversalList;
		}

		traverseInOrder(node.leftChild, traversalList);
		traversalList.add(node);

		traverseInOrder(node.rightChild, traversalList);
		return traversalList;
	}
	/*
	 * Returns a reference to root
	 * @return root is the root node of this ExpressionTree
	 */
	public Node<String> getRoot() {
		return this.root;
	}
	/*
	 * Returns the original infix expression this tree was constructed with
	 * @return the infix of this tree
	 */
	public String getInfix() {
		return this.infixExpression;
	}
	/*
	 * Compares an object to this tree and returns equal if all three traversals are the same for both trees
	 * @param treeToCompare the object to compare to this tree
	 * @return boolean can only be true if the trees have identical subtrees
	 */
	@Override
	public boolean equals(Object treeToCompare) {
		if (treeToCompare instanceof ExpressionTree) {

			ExpressionTree cast = (ExpressionTree) treeToCompare;

			if (this.getInOrderTraversalString().equals(((ExpressionTree) treeToCompare).getInOrderTraversalString())
					&& this.getPreOrderTraversalString().equals(((ExpressionTree) treeToCompare).getPreOrderTraversalString())
					&& this.getPostOrderTraversalString().equals(((ExpressionTree) treeToCompare).getPostOrderTraversalString())) {
				return true;
			}

		}

		return false;
	}
	/*
	 * Appends key fields of data into String 
	 * @return the string output containing type of tree, infixExpressoin, preOrder traversal and postorder traversal
	 */
	@Override
	public String toString() {
		String preOrderString = getPreOrderTraversalString();
		String postOrderString = getPostOrderTraversalString();
		String typeOfTree = getClass().getSimpleName();
		String infixExpression = this.getInfix();
		String output = ("typeOfTree: [" + typeOfTree + "]\n" + "infixExpression: [" + infixExpression
				+ "]\npreOrderString: [" + preOrderString + "]\npostOrderString: [" + postOrderString + "]");
		return output;
	}
}
