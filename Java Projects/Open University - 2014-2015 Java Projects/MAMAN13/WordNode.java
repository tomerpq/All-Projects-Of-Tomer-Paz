/**
 * this class represents a single node
 * Time complexity = O(1), Space complexity = O(1) for all methods and constructors except mergeSort method.
 * @version 18.6.2015
 * @author Tomer Paz
 */
public class WordNode{
    //instance variables:
    private String _word;//the node's word.
    private WordNode _next;//the node's tail.
    private int _num;//the number of the node's word show in the text(no double words in the list).
    //constructors:
    /**
     * this constructor builts new node with word,number of it appearing in the text,and connection to the next node.
     * @param word the word the node has
     * @param num the number of the word appearing in the text
     * @param next the next node connected to it (no connected-text = null)
     */
    public WordNode(String word, int num, WordNode next){
        _word = word;
        _next = next;
        _num = num;
    }
    //methods:
    /**
     * return the next node connected to this node
     * @return the next node
     */
    public WordNode getNext(){
        return _next;
    }
    /**
     * set the next node to be the node inserted
     * @param node the next node
     */
    public void setNext(WordNode node){
        _next = node;
    }
    /**
     * return the word that the node has
     * @return the node's word
     */
    public String getWord(){
        return _word;
    }
    /**
     * set the word in the node to be the node inserted
     * @param word the word to be set
     */
    public void setWord(String word){
        _word = word;
    }
    /**
     * gets the number of the word in the node appearing in the whole text
     * @return the number of the word in the node appears in the whole text
     */
    public int getNum(){
        return _num;
    }
    /**
     * sets the number the word shows in the whole text in the node
     * @param num the number that the word in the node appears in the whole text to be set
     */
    public void setNum(int num){
        _num = num;
    }
    //mergsort:
    /**
     * Time complexity = O(nlog(n)), Space complexity = O(log(n))
     * the method sorts the linked list's words in the TextList class
     * in lexicography order.
     * @param h the head of the list to be sorted
     * @return the head of the list(means return the list) sorted lexicography.
     */
    public WordNode mergeSort(WordNode h){
        //sort lists started by h(head) by recursivel splitting to two lists and merging the splitted lists sorted
        if ((h == null) || (h.getNext() == null))
        return h;//zero or one item
        else {//two or more items
            //split it in two parts
            ListNodePair p = h.split();
            //then sort and merge the two parts
            return mergeSort(p.x()).merge(mergeSort(p.y()));
        }
    }
    //assisting methods for mergesort:
    private ListNodePair split(){//split method
        if (_next == null)
        return new ListNodePair(this, null);
        else {
            ListNodePair p = _next.split();
            return new ListNodePair(new WordNode(_word, _num, p.y()), p.x());
        }
    }
    private WordNode merge(WordNode L){//merge ordered splitted lists method
        if (L == null)
        return this;
        if(_word.compareTo(L._word) < 0)
        if(_next == null)
        return new WordNode(_word, _num, L);
        else
        return new WordNode(_word, _num, _next.merge(L));
        else
        return new WordNode(L._word, L._num, merge(L._next));
    }
}//class