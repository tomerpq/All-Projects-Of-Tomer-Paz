public class TerNode
{
 private int _number;
 private TerNode _leftSon, _middleSon, _rightSon;

 public TerNode(int number) {
 _number = number;
 _leftSon = null;
 _middleSon = null;
 _rightSon = null;
 }

 public int getNumber() {return _number;}
 public TerNode getLeftSon() {return _leftSon;}
 public TerNode getMiddleSon() {return _middleSon;}
 public TerNode getRightSon() {return _rightSon;}

 public void setNumber(int number) {_number = number;}
 public void setLeftSon(TerNode node) {_leftSon = node;}
 public void setMiddleSon(TerNode node){_middleSon = node;}
 public void setRightSon(TerNode node) {_rightSon = node;}
}