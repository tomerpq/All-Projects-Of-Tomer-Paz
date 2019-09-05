public class Compare{
    public Compare(){}
    public static int myCompare(String s1, String s2){
        if (s2.length() == 0 && s1.length() > 0)
        return 1;
        if (s1.length() == 0 && s2.length() > 0)
        return -1;
        if (s1.charAt(0) > s2.charAt(0))
        return 1;
        if (s2.charAt(0) > s1.charAt(0))
        return -1;
        if (s1.substring(0) == s2.substring(0))
        return 0;
        return myCompare(s1.substring(1,s1.length()), s2.substring(1,s2.length()));
    }
}