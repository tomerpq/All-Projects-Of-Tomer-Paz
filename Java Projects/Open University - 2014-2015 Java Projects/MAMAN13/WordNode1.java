/**
 * Represents a word node - a word, the number of times the word appears and a pointer to the next word node
 *
 */
public class WordNode
{
    private final int DEFAULT_COUNT = 1;

    private String _word;
    private int _count;
    private WordNode _next;
    /**
     * Constructs a word node. 
     *  
     *  
     * @param word  a new word 
     * @param next  the next word in the list
     */ 
    public WordNode(String word, WordNode next){
        setWord(word);
        setCount(DEFAULT_COUNT);
        setNext(next);
    }

    /**
     * Returns the word represented by  this node
     *
     * @return the word represented by this node
     */
    public String getWord(){
        return _word;
    }

    /**
     * Sets the word represented by this node
     *
     * @param  the word represented by this node
     */
    public void setWord(String word){
        this._word = word;
    }

    /**
     * Returns the number of times this word appears
     *
     * @return the number of times this word appears
     */
    public int getCount(){
        return _count;
    }

    /**
     * Sets the number of times this word appears
     *
     * @param the number of times this word appears
     */
    public void setCount(int count){
        this._count = count;
    }

    /**
     * Returns the next word node
     *
     * @return the next word node
     */
    public WordNode getNext(){
        return _next;
    }

    /**
     *Sets the next word node
     *
     * @param the next word node
     */
    public void setNext(WordNode next){
        this._next = next;
    }  

    /**
     * Returns a string representation of this word tab between the word and the word count
     *
     * @return a string representation of this word
     */
    public String toString(){
        return this.getWord() + "\t" + this.getCount();
    }

}
