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
    for (String username : printGrades.keySet()) {
      System.out.print(username);
      printGrades.get(username).forEach((k,v) -> {
          System.out.print(", " + k + ":" + v);
        });
      System.out.println();
    }
  }

  public void exportStudentGradeFile(Student s, GradeBook gradeBook, Assignment a) {
    try {
      PrintWriter writer = new PrintWriter(s.getUsername() + "_grades.txt");
      writer.println("Name: " + s.getName());
      writer.println("Username: " + s.getUsername());
      writer.println("Email: " + s.getEmail());
      HashMap<String, Double> grades = gradeBook.getGradesForStudent(s);
      grades.forEach((k,v) -> {
          writer.println(k + ":  " + v);
        });
      double avg = gradeBook.calculateAverage(s, a);
      writer.println("Average: " + avg);
      writer.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
      writer.close();
    } catch (FileNotFoundException e) {
    }
  }
}
