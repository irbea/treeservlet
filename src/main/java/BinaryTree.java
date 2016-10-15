import java.io.PrintWriter;

public class BinaryTree {

	private Node root;
	private Node currentNode;
	private int maxDepth;
	private int currentDepth;
	private int sumDepth;
	private int numLeaves;
	private double averageDepth;
	private double sumDeviation;
	private boolean inComment = false;

	public BinaryTree() {
		root = new Node('/');
		currentNode = root;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int getMaxDepth() {
		maxDepth = 0;
		currentDepth = 0;
		currentNode = root;

		calculateMaxDepth(currentNode);
		return maxDepth;
	}

	private void calculateMaxDepth(Node current) {
		if (current != null) {
			++currentDepth;
			if (currentDepth > maxDepth) {
				maxDepth = currentDepth;
			}
			calculateMaxDepth(current.getRightNode());
			calculateMaxDepth(current.getLeftNode());
			--currentDepth;
		}

	}

	public double getAverageDepth() {
		currentDepth = sumDepth = numLeaves = 0;
		currentNode = root;
		calculateAverageDepth(currentNode);
		averageDepth = ((double) sumDepth) / numLeaves;
		return averageDepth;
	}

	private void calculateAverageDepth(Node current) {
		if (current != null) {
			++currentDepth;
			calculateAverageDepth(current.getLeftNode());
			calculateAverageDepth(current.getRightNode());
			--currentDepth;

			if (current.getLeftNode() == null && current.getRightNode() == null) {
				++numLeaves;
				sumDepth += currentDepth;
			}
		}

	}

	public double getDeviation() {
		averageDepth = getAverageDepth();
		sumDeviation = 0.0;
		currentDepth = numLeaves = 0;

		calculateDeviation(root);

		double deviation = 0.0;

		if ((numLeaves - 1) > 0) {
			deviation = Math.sqrt(sumDeviation / (numLeaves - 1));
		} else {

			deviation = Math.sqrt(sumDeviation);
		}

		return deviation;
	}

	private void calculateDeviation(Node current) {

		if (current != null) {
			++currentDepth;
			calculateDeviation(current.getLeftNode());
			calculateDeviation(current.getRightNode());
			--currentDepth;
			if (current.getLeftNode() == null && current.getRightNode() == null) {
				++numLeaves;
				sumDeviation += ((currentDepth - averageDepth) * (currentDepth - averageDepth));
			}
		}

	}

	public void bitRead(char data) {
		if (data == '0') {
			if (currentNode.getLeftNode() == null) {
				Node node = new Node('0');
				currentNode.setLeftNode(node);
				currentNode = root;
			} else {
				currentNode = currentNode.getLeftNode();
			}
		} else {
			if (currentNode.getRightNode() == null) {
				Node node = new Node('1');
				currentNode.setRightNode(node);
				currentNode = root;
			} else {
				currentNode = currentNode.getRightNode();
			}
		}
	}

	public void printTree(PrintWriter out) {

		Integer currentDepth = 0;
		printNode(root, currentDepth, out);
	}

	private void printNode(Node node, Integer currentDepth, PrintWriter out) {

		if (node != null) {
			++currentDepth;

			printNode(node.getRightNode(), currentDepth, out);

			for (int i = 0; i < currentDepth; ++i) {
				out.print("---");
			}

			out.print(node.getCharacter());
			out.print("(");
			out.print(currentDepth - 1);
			out.print(")\n");

			printNode(node.getLeftNode(), currentDepth, out);
			--currentDepth;
		}
	}

	public void parseCharacter(char character) {
		
		if (character == '>') {
			inComment = true;
			return;
		}

		if (character == '\n') {
			inComment = false;
			return;
		}

		if (inComment) {
			return;
		}

		if (character == 'N')
		{
			return;
		}

		for (int i = 0; i < 8; ++i) {
			if ((character & 0x0080) != 0)
			{
				this.bitRead('1');
			} else
			{
				this.bitRead('0');
			}
			character <<= 1;
		}
	}

}
