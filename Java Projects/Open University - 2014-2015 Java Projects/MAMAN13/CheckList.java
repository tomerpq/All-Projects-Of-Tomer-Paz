public class CheckList{
    public static void main(String[] args){
        CharList s = new CharList();
        CharList t = new CharList();
        CharList k = new CharList();
        s._head = new CharNode('a',new CharNode(('b'),new CharNode(('b'),new CharNode(('c'),new CharNode(('d'),null)))));
        k._head = new CharNode('a',new CharNode(('a'),new CharNode(('c'),new CharNode(('c'),new CharNode(('b'),
        new CharNode(('b'),new CharNode(('d'),new CharNode(('d'),null))))))));
        t._head = new CharNode('a',new CharNode(('b'),new CharNode(('b'),new CharNode(('b'),new CharNode(('c'),
        new CharNode(('c'),new CharNode(('d'),null)))))));
        System.out.println(CharList.isTrans(s,t));
    }
}