public class Group
{

 public final int MAX_STUDENTS = 400;
 private Student[] _stud;
 private int _noOfStud;
 public Group()
 {
     _stud = new Student[MAX_STUDENTS];
     _noOfStud = 0;
 }
 public void add(String n, int day, int month, int year){
     Student newStud = new Student(n,day,month,year);
     for (int i=0; i < MAX_STUDENTS; i++){
         if (_stud[i] == null){
         _stud[i] = newStud;
         _noOfStud++;
        }
        }
    }
 public int howManyMonths(){
     int[] arr = new int[12];
     int count = 0;
     for (int i=0; _stud[i] != null && i < _noOfStud; i++){
         arr[_stud[i].getBirthday().getMonth() -1] = arr[_stud[i].getBirthday().getMonth() -1] + 1;
     }
     for (int j=0; j < 12; j++){
         if (arr[j] == 0)
         count++;
     }
     return count;
 }
 public boolean biggerThan(int num){
     Student oldest = _stud[0];
     Student youngest = _stud[0];
     for (int i=0; _stud[i] != null && i < _noOfStud; i++){
         if (oldest.getBirthday().after(_stud[i].getBirthday()))
         oldest = _stud[i];
         if (youngest.getBirthday().before(_stud[i].getBirtday()))
         youngest = _stud[i];
     }
     if (oldest != null && oldest.difference(youngest) >= num)
     return true;
     return false;
 }
 public int maxDaysWithoutBirthdays(){
     StudentNode current1 = sort().getStudents();
     StudentNode current2 = sort().getStudents();
     if (current1 == null || current1.getNext() == null)
     return 365;
     return maxDaysWithoutBirthdays(current1,current1.getStudent().getBirthday().difference(getLast(current2).getStudent().getBirthday()));
 }
 private int maxDaysWithoutBirthdays(StudentNode pointer, int maxD){
     if (pointer.getNext() == null)
     return maxD;
     if (maxD < pointer.getStudent().getBirthday().difference(pointer.getNext().getStudent().getBirthday()))
     maxD = pointer.getStudent().getBirthday().difference(pointer.getNext().getStudent().getBirthday());
     pointer = pointer.getNext();
     return maxDaysWithoutBirthdays(pointer,maxD);
 }
 private StudentNode getLast(StudentNode node){
     if (node.getNext() == null)
     return node;
     node = node.getNext();
     return getLast(node);
 }
 public Student[] getStudents() {…}
 public int getNoOfStudents() {…}
 public void remove (String name, int d, int m,
 int y){…}
 public Student[] sort() {…}
 public String toString() {…}
}