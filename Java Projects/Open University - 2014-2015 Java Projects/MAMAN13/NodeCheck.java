public class NodeCheck{
    public static int min (int a, int b){ 
        if (a < b)
        return a;
        else 
        return b;
    }
public static boolean isLeaf (Node t){
    if (t != null && t.getLeftSon() == null && t.getRightSon() == null)
    return true;
    return false;
} 
public static int f (Node t)
{
 if (t == null)
 return 0;
 return 1 + min (f (t.getLeftSon()),f (t.getRightSon()));
}
public static Node what(Node t)
{
 return what (t, f(t));
}
private static Node what (Node t, int num)
{
 if ((t == null) || ((num == 1) && (isLeaf(t))))
 return t;
 Node temp = what(t.getLeftSon(), num-1);
 if (temp != null)
 return temp;
 return what(t.getRightSon(), num-1);
} 
    public static void main(String[] args){
        Node t = new Node(40,new Node(20,new Node(30,new Node(45,null,null),null),
        new Node(5,new Node(50,null,null),new Node(25,null,null)))
        ,new Node(10,new Node(77,null,null),new Node(89,null,null)));
        System.out.println(f(t));
        System.out.println(what(t).getNumber());
    }
}