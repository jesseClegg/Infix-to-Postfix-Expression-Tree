/**
 * Tests functionalities of classes by reading from testPool.txt
 * @author Jesse Clegg
 * @version 1.0
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Driver {
	/*
	 * Runs the testing protolol on startup
	 */
	public static void main(String[] args) throws IOException {
		printTestingPool();
	}
	/*
	 * Executes testing from testingPool.txt alongside original data for accurate verification
	 */
	public static void printTestingPool() throws IOException {
		List<String> lines = Files.readAllLines(Paths
				.get("/Users/jc/eclipse-workspace/projectOne311/afterOfficeHoursTry/TuesdaysShot/bin/testingPool.txt"));
		ArrayList<Node> traversalList = new ArrayList<Node>();
		int lineNumber = 1;
		for (int i = 0; i < lines.size(); i++) {
			ExpressionTree tree = new ExpressionTree(lines.get(i));
			System.out.println();
			System.out.println("Original infixExpression: "+lines.get(i));
			System.out.print(tree);
			ExpressionTree tree2 = new ExpressionTree(lines.get(i));
			System.out.println();
			System.out.println(tree.getRoot().toString());
			System.out.println("testing are trees = " + tree.equals(tree2));		
			System.out.println("testing are roots = "+tree.getRoot().equals(tree2.getRoot()));
		}
	}
}
