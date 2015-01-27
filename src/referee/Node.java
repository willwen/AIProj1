package referee;

public class Node {
	Point nodePoint;
	int value;
	Node[] children;
	    
    Node (Point nodePoint, int value, Node[] children){
		this.nodePoint = nodePoint;
		this.value = value;
		this.children = children;
	}
}
