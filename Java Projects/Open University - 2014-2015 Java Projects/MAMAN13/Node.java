public class Node
{
 private int _number;
 private Node _leftSon, _rightSon;
 public Node (int number) {
 _number = number;
 _leftSon = null;
 _rightSon = null;
 }
 public Node(int number, Node left, Node right){
     _number = number;
     _leftSon = left;
     _rightSon = right;
 }
 public int getNumber() {return _number; }
 public Node getLeftSon() {return _leftSon; }
 public Node getRightSon() {return _rightSon; }
 public void setRightSon(Node node){ _rightSon = node;}
 public void setLeftSon(Node node){ _leftSon = node;}
} 
