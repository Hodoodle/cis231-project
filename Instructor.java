import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Instructor extends User {
  private String name;
  private String email;

  public Instructor(String username, String password, String name) {
    String[] data = User.returnData(username);
    super(data[0], data[1], data[2]);
    this.name = data[3];
    this.email = data[4];
  }

  public void addStudent(Student s, GradeBook gradeBook) {
    gradeBook.addStudent(s);
  }

  public void addAssignment(Assignment a) {}

  public void recordGrade(Student s, double g, GradeBook gradeBook, Assignment a, int id) {
    gradeBook.addGrade(s, a, id, g);
  }

  public void viewAllGrades(GradeBook gradeBook, Assignment assignment) {
    HashMap<String, HashMap<String, Double>> printGrades = gradeBook.getAllGrades(); 
    double avg = 0;
    String lGrade;
    for (String username : printGrades.keySet()) {
      System.out.print(username + "\t");
      Student s = new Student(username);
      printGrades.get(username).forEach((k,v) -> {
        System.out.print(k + ": " + v + "/" + assignment.getMaxPoints(k) + "\t");
      });
      avg = gradeBook.calculateAverage(s, assignment);
      lGrade = gradeBook.determineLetterGrade(avg);
      System.out.printf("Average: %.2f, %s\n", avg, lGrade);
    }
  }
  
}
