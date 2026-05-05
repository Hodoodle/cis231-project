import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Student extends User {
  private String name;
  private String email;

  public Student(String username, String password, String name, String email) {
    super(username, password, "Student");
    this.name = name;
    this.email = email;
  }

  public void viewOwnGrades(GradeBook gradeBook) {
    ArrayList<Double> grades = gradeBook.getGradesForStudent(this);
    System.out.println("Grades for " + name + ": " + grades);
    double avg = gradeBook.calculateAverage(this);
    System.out.println("Average: " + avg);
    System.out.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
  }

  public void exportOwnGradeFile(GradeBook gradeBook) {
    try {
      PrintWriter writer = new PrintWriter(username + "_grades.txt");
      writer.println("Name: " + name);
      writer.println("Email: " + email);
      ArrayList<Double> grades = gradeBook.getGradesForStudent(this);
      writer.println("Grades: " + grades);
      double avg = gradeBook.calculateAverage(this);
      writer.println("Average: " + avg);
      writer.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
      writer.close();
    } catch (FileNotFoundException e) {
    }
  }

  public String getName() {
    return name;
  }
}
