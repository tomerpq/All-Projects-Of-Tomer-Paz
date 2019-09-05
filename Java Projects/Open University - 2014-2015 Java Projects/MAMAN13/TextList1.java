/*#shadi:
 * grade 
 */
/**
* Represents a list of words from a text
*
*/
public class TextList {
    
    private WordNode _head;
    public final char SPACE = ' ';
	   
    /**
     *   Constructs an empty list.
     *  
     */
    public TextList() 
    {
        _head = null;    
    }
 
    /**
     * Constructs list from the specified string.  
     *  
     *  
     * @param text the string to tokenize and build the list from.
     */
    public TextList(String text) 
    {
        // first add all words with O(n)
        //String[] words = text.split(" ");
        if (text.equals(""))
            _head = null;  
           
        else{
           
        String[] words = splitToWords(text);
        
        WordNode current = null;
        for (String word : words)
        {
            WordNode next = new WordNode(word, null);
            
            next.setNext(current);
            current = next;
            /*
            if (current == null) 
            {
                current = next;
                _head = current;
            }
            else
            {
                current.setNext(next);
                current = next;
            } 
            */ 
        }
        _head = current;
        
        // now sort with merge sort O(nlogn)
       _head = mergeSort(_head);   
       
       // now unify duplicate occurences O(n)
      current = _head;
      while (current != null && current.getNext() != null) 
      {
		if (current.getWord().equals(current.getNext().getWord()))
		{
		    current.setCount(current.getCount()+1);
		    current.setNext(current.getNext().getNext());
		}
		else
		    current = current.getNext();
      }
    }
 }
    
    /**
     * Adds the specified word to the list.
         * @param word the word  to add to the list.
     */
    public void addToData(String word) 
   
    {
        if(word.equals(""))
           return;
        WordNode current = _head;
        if (_head == null || current.getWord().compareTo(word)>0 ) 
        {
            _head = new WordNode(word, _head);
            return;
        }
        while (current != null) 
        {
            if (current.getWord().equals(word))
            {
                current.setCount(current.getCount()+1);
                return;
            }
            if (current.getNext()==null || current.getNext().getWord().compareTo(word)>0) 
            {
                current.setNext(new WordNode(word,current.getNext()));
                return;
            }
            current = current.getNext();
        }
    }


    /**
     * Returns the number of words in the text represented by the list.
         *
     * @return the number of words in the text represented by the list.
     */
    public int howManyWords() 
    {
        int result = 0;
        WordNode current = _head;
        while (current != null) 
        {
            result += current.getCount();
            current = current.getNext();
        }
        return result;        
    }


    /**
     * Returns the number of unique words in the text represented by the list.
         * @return the number of unique words in the text represented by the list.
     */
    public int howManyDifferentWords() 
    {
        int result = 0;
        WordNode current = _head;
        while (current != null) 
        {
            result++;
            current = current.getNext();
        }
        return result;        
    }



    /**
     * Returns the most frequent word in the text represented by the list.
        * @return the most frequent word in the text represented by the list.
     */
    public String mostFrequentWord() 
    {
        String result = "";
        int max = 0;
        WordNode current = _head;
        while (current != null) 
        {
            if (current.getCount() > max)
            {
                max = current.getCount();
                result = current.getWord();
            }
            current = current.getNext();
        }
        return result;        
    }



    /**
     * Returns the letter that is the most frequent as the first letter in a word.
         *
     * @return  the letter that is the most frequent as the first letter in a word.
     */
    public char mostFrequentStartingLetter() 
    {
        return mostFrequentStartingLetter(0, ' ', _head); 
    }


    private char mostFrequentStartingLetter(int maxOcc, char maxChar, WordNode current) 
    {
        // base
        if (current == null)
            return maxChar;
            
         // check for new char
        char tempChar = current.getWord().charAt(0);
        int tempOcc = current.getCount();
        current = current.getNext();
        while (current!=null && current.getWord().charAt(0)==tempChar)
        {
                    tempOcc += current.getCount();
                    current = current.getNext();
        }
        
        // check new max
        if (tempOcc > maxOcc)
            return mostFrequentStartingLetter(tempOcc, tempChar, current);
        else
            return mostFrequentStartingLetter(maxOcc, maxChar, current);
    }
    


    /**
     * Returns the total number of words starting with the specified letter.
     *@param letter to check which  words start with 
     * @return the total number of words starting with the specified letter.
     */
    public int howManyStarting(char letter) 
    {
        int result = 0;
        WordNode current = _head;
        // advance to starting word
        while (current != null && letter > current.getWord().charAt(0))  
            current = current.getNext();

        // count
        while (current != null && letter == current.getWord().charAt(0))  
        {
            result += current.getCount();;
            current = current.getNext();
        }
        return result;        
    }

    
    /**
     * Returns the string representation of the list. <br /Each word is followed
     * by its count (separated by tab) and each word-count pair appears in
     * a different line . 
     * The words are ordered in  lexicographic order. 
          * @return the string representation of the list object.
     */
    public String toString() 
    {
        String result = "";
        WordNode current = _head;
        while (current != null) 
        {
            result += current + "\n";
            current = current.getNext();
        }
        return result;
    }
    
    /**
     * Mergesort algorithm.
     * 
     */
    private  WordNode mergeSort(WordNode node) 
    {
		if (node == null || node.getNext() == null)
            return node; // checks for empty or single list
		WordNode list2 = split (node);
	
		node = mergeSort (node);
		list2 = mergeSort (list2);
		
		return merge (node, list2);
    }     // end merge_sort
    
   /**
    * Splits the specified list to 2 lists of equal (/+1) length  
    */ 
    private WordNode split(WordNode node) 
    {
		if (node == null || node.getNext() == null) 
			return null;
		WordNode list2 = node.getNext();
		node.setNext(list2.getNext());
		list2.setNext(split(list2.getNext()));
		return list2;
   }  // end split method
   
   /**
    * Merges the two specified lists, assuming each specified list is already 
    * sorted.
    */
   private WordNode merge(WordNode node, WordNode list2) 
   {
	   if (node == null) 
		   return list2;
	   if (list2 == null) 	
		   return node;
//	if (node.getWord().charAt(0) < list2.getWord().charAt(0)) {
	
	   if (node.getWord().compareTo(list2.getWord()) < 0) {
		   node.setNext(merge (node.getNext(), list2));
		   return node;
	   } // end if
	   else {
		   list2.setNext(merge(node, list2.getNext()));
		   return list2;
      }// end else
   } // end merge method
   
   /**
    * Split a given String into words.
    * The results is a String array that is identical to the result of - text.split(" ")
    */
   private String[] splitToWords(String text)
   {
	   String[] words = new String[text.length()];
	   int fromIndex = 0; // index to start the search for next space
	   int inx = 0; // index of the found space (the next space)
	   int counter = 0; // word counter
	   
	   while (inx > -1 && text.length()>0 && fromIndex<text.length() )
	   {
		   inx = text.indexOf(SPACE, fromIndex);
		 
		   if (inx > -1)
		   {
			   words[counter] = text.substring(fromIndex, inx);
			   fromIndex = inx+1;
		   }
		   else
		   {
			   words[counter] = text.substring(fromIndex);
		   }
		   
		   counter++;		   		   
	   
	   }
	   
	   String[] outWords = new String[counter];
	   for (int i=0; i < outWords.length; i++)
		   outWords[i] = words[i];
	   
	   return outWords;
   }
    
}
