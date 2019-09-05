public class Question4
{
 public static String f1(String s)
 {
 return s.substring(0, s.length()/2);
 }

 public static String f2(String s)
 {
 return s.substring(s.length()/2);
 }

 public static String what (String s1, String s2)
 {
 if (s1.length() == 0)
 return s2;
 if (s2.length() == 0)
 return s1;
 if (s1.charAt(0) < s2.charAt(0))
 return s1.charAt(0) + what (s1.substring(1), s2);
 return s2.charAt(0) + what (s1, s2.substring(1));
 }
 public static String something(String s)
 {
 if (s.length() > 1)
 {
 String s1 = f1(s);
 String s2 = f2(s);
 s1 = something(s1);
 s2 = something(s2);
 return what(s1, s2);
 }
 else
 return s;
 }
} 