/**
 * this class represents a pointer that will point on 2 linked lists (splitted from the original list)
 * Time complexity = O(1), Space complexity = O(1).(for all methods and constructors in the class).
 * @version 18.6.2015
 * @author Tomer Paz
 */
public class ListNodePair{
    //instance variables:
    private WordNode _a, _b;//for splitting the list two 2 lists.
    //constructors:
    /**
     * constructs an object that will point on 2 (splitted) linked lists.
     * @param a the list I head
     * @param b the list II head
     */
    public ListNodePair(WordNode a, WordNode b){
        this._a = a;
        this._b = b;
    }
    //methods:
    /**
     * returns the pointer to the splitted list I
     * @return the pointer to the splitted list I
     */
    public WordNode x(){
        return _a;
    }
    /**
     * returns the pointer to the splitted list II
     * @return the pointer to the splitted list II
     */
    public WordNode y(){
        return _b;
    }
}//class