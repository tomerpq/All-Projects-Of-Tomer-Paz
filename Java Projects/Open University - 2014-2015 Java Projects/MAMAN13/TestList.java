public class TestList{
    public static void main(String[] args){
        TextList tomer = new TextList("not so so many words");
        TextList daniel = new TextList("tomer went to to learn daniel some usefull math math math mathmathically da harasho blet");
        System.out.println(daniel.howManyWords());
        System.out.println(tomer.howManyWords());
        System.out.println(tomer.howManyDifferentWords());
        System.out.println(tomer.mostFrequentStartingLetter());
        System.out.println(daniel.mostFrequentStartingLetter());
    }
}