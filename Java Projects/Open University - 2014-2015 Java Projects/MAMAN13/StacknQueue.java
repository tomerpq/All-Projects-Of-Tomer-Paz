public class StacknQueue{ 
  public static int what (TerNode t)
 {
 if (t == null)
 return 0;
 int temp = 0;
 if (t.getLeftSon() != null) temp++;
 if (t.getMiddleSon() != null) temp++;
 if (t.getRightSon() != null) temp++;
 return temp;
 } 
public static boolean something (TerNode t)
 {
 if (t == null)
 return true;
 if (what(t) == 3)
 return false;
 return something (t.getLeftSon())
 && something (t.getMiddleSon())
 && something (t.getRightSon());
 }
    public static void main(String[] args){
        TerNode t = new TerNode(1);
        t.setRightSon(new TerNode(3));
        t.getRightSon().setRightSon(new TerNode(6));
        t.getRightSon().getRightSon().setMiddleSon(new TerNode(11));
        t.getRightSon().getRightSon().setRightSon(new TerNode(12));
        t.setLeftSon(new TerNode(2));
        t.getLeftSon().setLeftSon(new TerNode(4));
        t.getLeftSon().getLeftSon().setLeftSon(new TerNode(7));
        t.getLeftSon().setRightSon(new TerNode(5));
        //t.getLeftSon().getRightSon().setMiddleSon(new TerNode(9));
        t.getLeftSon().getRightSon().setLeftSon(new TerNode(8));
        t.getLeftSon().getRightSon().getLeftSon().setMiddleSon(new TerNode(13));
        t.getLeftSon().getRightSon().setRightSon(new TerNode(10));
        t.getLeftSon().getRightSon().getRightSon().setRightSon(new TerNode(16));
        //t.getLeftSon().getRightSon().getRightSon().setMiddleSon(new TerNode(15));
        t.getLeftSon().getRightSon().getRightSon().setLeftSon(new TerNode(14));
        System.out.println(something(t));
    }
}