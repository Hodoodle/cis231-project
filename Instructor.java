import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Instructor extends User {
  private String name;

  public Instructor(String username, String password, String name) {
    super(username, password, "Instructor");
    this.name = name;
  }

  public void addStudent(Student s, GradeBook gradeBook) {
    gradeBook.addStudent(s);
  }

  public void addAssignment(Assignment a) {}

  public void recordGrade(Student s, double g, GradeBook gradeBook) {
    gradeBook.addGrade(s, g);
  }

  public void viewAllGrades(GradeBook gradeBook) {
    for (Map.Entry<String, ArrayList<Double>> entry : gradeBook.getAllGrades().entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  public void exportStudentGradeFile(Student s, GradeBook gradeBook) {
    try {
      PrintWriter writer = new PrintWriter(s.getUsername() + "_export.txt");
      writer.println("Student: " + s.getName());
      ArrayList<Double> grades = gradeBook.getGradesForStudent(s);
      writer.println("Grades: " + grades);
      double avg = gradeBook.calculateAverage(s);
      writer.println("Average: " + avg);
      writer.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
      writer.close();
    } catch (FileNotFoundException e) {
    }
  }
}
