public class CharList {
 public CharNode _head;

 public CharList( ) {
 _head = null;
 }
 public static boolean isTrans(CharList list1, CharList list2){
     CharNode sPointer = list1._head;
     CharNode tPointer = list2._head;
     return isTrans(sPointer,tPointer);
 }
 private static boolean isTrans(CharNode pointer1, CharNode pointer2){
     if (pointer1 == null)
     return true;
     if (pointer2 == null)
     return false;
     if (pointer1.getValue() == pointer2.getValue())
     return isTrans(pointer1.getNext(),pointer2.getNext());
     return isTrans(pointer1,pointer2.getNext());
 }
} 