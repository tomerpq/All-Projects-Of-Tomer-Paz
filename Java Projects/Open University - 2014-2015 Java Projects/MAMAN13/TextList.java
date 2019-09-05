/**
 * this class represents a linked list build of some text made of words with a single
 * space between them that have only small english letters (a-z).
 * the list is sorted lexicography.
 * each cell(object) in the list contains-the word from the text, the number the word appears.
 * @version 18.6.2015
 * @author Tomer Paz
 */
public class TextList{
    //instance variables:
    private WordNode _head;//the head of the list
    //constructors:
    /**
     * Time complexity = O(1), Space complexity = O(1)
     * empty constructor that makes empty list
     */
    public TextList(){
        _head = null;
    }
    /**
     * Time complexity = O(nlog(n)), Space complexity = O(n)-(of the heap) n-is the num of words.
     * 1.this contructor builds a list from all the words in the text
     * 2.sorts the words in the list to get lexicography sorted list
     * 3.remove words that appears twice and adds to it the number it shows.(the dafult is 1-appear 1 time in the text)
     * if text is empty string the linked list will be empty list
     * @param text the text inserted made of small english letters and spaces between words.
     */
    public TextList(String text){
        if (text.equals("") || text.equals(" "))//empty text
        _head = null;
        else{//building the list
            if (text.charAt(0) == ' ')
            text = text.substring(1);//removing spaces from the text's start
            if (text.charAt(text.length() -1) != ' ')
            text = text.concat(" ");//making sure the text has space at his end
            //building the unsorted list
            _head = new WordNode(text.substring(0,text.indexOf(" ")),1,null);
            WordNode current = _head;//pointing current to head.
            text = text.substring(text.indexOf(" "));
            if (text.length() > 1)
            text = text.substring(1);
            while (text.length() > 1){
                current.setNext(new WordNode(text.substring(0,text.indexOf(" ")),1,null));
                current = current.getNext();
                text = text.substring(text.indexOf(" "));
                if (text.length() > 1)
                text = text.substring(1);
            }
            //sorting the list with mergeSort from WordNode class:
            _head = _head.mergeSort(_head);
            //now deleting double words in the list-
            //every word in the list will have the number it shows in the text.
            current = _head;//repointing current to head.
            while (current != null && current.getNext() != null){
                if (current.getWord().equals(current.getNext().getWord())){//same words
                    current.setNum(current.getNum() +1);
                    current.setNext(current.getNext().getNext());//removing unnecesarry word
                } 
                if (current.getNext() == null)
                break;//bound
                if (!current.getWord().equals(current.getNext().getWord()))
                current = current.getNext();//to continue loop
            }
        }//else
    }//2nd constructor
    //methods:
    /**
     * Time complexity = O(n), Space complexity = O(1) n-words
     * this method add a word to the list(the list with the new word keeps
     * the lexi order,and no double words(if same word inserted that word will
     * have bigger num))
     * if the word is empty string nothing will happen
     * @param word the word inserted (same terms as in the constructor for the text string)
     */
    public void addToData(String word){
        if (!word.equals("")){//dont do anything if word is empty string.
        WordNode current = _head;
        WordNode befcurrent = _head;//two pointers that points to head at the start, after the second pointer will be before the firstone.
        if (current == null){//empty list case.
        _head = new WordNode(word,1,null);
        return;
        }
        while (current != null){//the list contains the head atleast.
            if (word.compareTo(current.getWord()) == 0){
            current.setNum(current.getNum() +1);//same words case
            return;
        }
            else if (word.compareTo(current.getWord()) > 0){//word is bigger lexi
                if (current.getNext() != null){
                if (current == _head)    
                current = current.getNext();
                else{
                    current = current.getNext();
                    befcurrent = befcurrent.getNext();
                }
            }
                else{
                current.setNext(new WordNode(word,1,null));
                return;
            }
            }
            else{//word is smaller lexi
                if (current == _head){
                WordNode temp = new WordNode(word,1,current);
                _head = temp;
                return;
            }
            else {
                WordNode temp = new WordNode(word,1,current);
                befcurrent.setNext(temp);
                return;
            }
            }
        }//while
    }//if for the whole method
    }//addToData
    /**
     * Time complexity = O(n), Space complexity = O(1) n-words
     * the method return the number of the words in the list
     * @return number of all the words in the list
     */
    public int howManyWords(){
        WordNode current = _head;//pointer
        int numW = 0;//number of words to be returned
        while (current != null){
            numW += current.getNum();
            current = current.getNext();
        }
        return numW;
    }
    /**
     * Time complexity = O(n), Space complexity = O(1) n-words
     * the method returns the number of different words in the list
     * @return the number of different words in the list
     */
    public int howManyDifferentWords(){
        WordNode current = _head;//pointer
        int numDW = 0;//number of different words to be returned
        while (current != null){
            numDW++;
            current = current.getNext(); 
        }
        return numDW;
    }
    /**
     * Time complexity = O(n), Space complexity = O(1) n-words
     * the methods returns the most frequent word.
     * if theres two it returns the smaller lexi word.
     * if the list is empty empty string will be returned
     * @return the most frequent word.
     */
    public String mostFrequentWord(){
        WordNode current = _head;//pointer
        if (current == null)
        return "";
        int firstMCW = 0;//bound for first (lexi) most common word.
        String fMCW = "";//most first(lexi) commong word.
        while (current != null){
            if (current.getNum() > firstMCW){
                firstMCW = current.getNum();
                fMCW = current.getWord();
            }
            current = current.getNext();
        }
        return fMCW;
    }
    /**
     * Time complexity = O(n), Space complexity = O(1) n-words
     * the method gets letter from (a-z) and returns the num of words starting with this letter
     * @param letter the letter inserted to check how many words starts with it
     * @return the num words starts with 'letter'
     */
    public int howManyStarting(char letter){
        WordNode current = _head;//pointer
        int numL = 0;//counts the number of times the words starts with "letter".
        while (current != null){
            if (numL > 0 && current.getWord().charAt(0) != letter)
            break;//bound for minimzing checkings(we passed the "letter..." words).
            if (current.getWord().charAt(0) == letter)
            numL += current.getNum();
            current = current.getNext();
        }
        return numL;
    }
    /**
     * recursive method that returns the letter that is the head of most of the words
     * in the list.
     * if theres two letters like that-the smaller lexi letter will be returned.
     * if the list is empty space char will be returned
     * @return the most frequent starting letter of the words
     */
    public char mostFrequentStartingLetter(){
        return mostFrequentStartingLetter(buildIntArray(),0,0,'a',' ');
    }
    //overloading mostFrequentStartingLetter:
    //this method searches the countchars array from its start and finds the most 
    //frequent starting letter, and if theres 2+ of it(same number of appearnce)-
    //the smaller char will be returned.
    private char mostFrequentStartingLetter(int[] arrN, int arrIndex, int count, char check, char letter){
        if (check > 'z')
        return letter;
        if (arrN[arrIndex] > count){
            count = arrN[arrIndex];//frequency bound
            letter = check;//first most common word(lexi) bound
        }
        check++;
        return mostFrequentStartingLetter(arrN,arrIndex +1,count,check,letter);
    }
    //helping (rec) method that fills the 26 places 1d int array with the number of each letter(a-z) shows in the start of the words of the list.
    //each cell corresponds to the letter number shows alphabetically. for example int[3] represents number times d shows in the start of the list's words. 
    private int[] buildIntArray(){
        int[] countCharArr = new int[26];//26 places int array filled with zeros to fill the count of each letter.
        WordNode current = _head;//pointer to head.
        String word = "abcdefghijklmnopqrstuvwxyz";//this word will be helpful for pointing which array index to change.
        return buildIntArray(current,word,countCharArr);
    }
    //overloading buildIntArray:
    private int[] buildIntArray(WordNode pointer, String w, int[] arrN){
        if (pointer == null)
        return arrN;
        arrN[w.indexOf(pointer.getWord().charAt(0))] = arrN[w.indexOf(pointer.getWord().charAt(0))] + pointer.getNum();//num of words the letter heads-get in the corresponding place in the array
        return buildIntArray(pointer.getNext(),w,arrN);
    }
    /**
     * Time complexity = O(n), Space complexity = O(1) n-words
     * returns a string representing the words in the linked list-
     * the word in lexi order + tab + the number it shows in the text then goes to next line.
     * if the list is empty empty string will be returned
     * @return the words in the list ordered lexi and the number they show.
     */
    public String toString(){
        WordNode current = _head;//pointer
        String s = "";//the output that represents the words and their num appearnce in the list.
        while (current != null){
            s += current.getWord() + "\t" + current.getNum();
            if (current.getNext() != null)
            s += "\n";
            current = current.getNext();
        }
        return s;
    }
}//class