import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Student extends User {
  private String name;
  private String email;

  public Student(String username) {
    String[] data = User.returnData(username);
    super(data[0], data[1], data[2]);
    this.name = data[3];
    this.email = data[4];
  }

  //Outputs the student's own grades
  public void viewOwnGrades(GradeBook gradeBook, Assignment a) {
    System.out.println("Grades for " + name + ":");
    HashMap<String, Double> grades = gradeBook.getGradesForStudent(this);
      grades.forEach((k,v) -> {
          System.out.println(k + ": " + v + "/" + a.getMaxPoints(k));
        });
    double avg = gradeBook.calculateAverage(this, a);
    System.out.printf("Average: " + "%.2f\n", avg);
    System.out.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
  }

  //Overloaded from User class form of the same method
  public void exportGradeFile(GradeBook gradeBook, Assignment a) {
    try {
      PrintWriter writer = new PrintWriter(username + "_grades.txt");
      writer.println("Name: " + name);
      writer.println("Username: " + username);
      writer.println("Email: " + email);
      HashMap<String, Double> grades = gradeBook.getGradesForStudent(this);
      grades.forEach((k,v) -> {
          writer.println(k + ": " + v + "/" + a.getMaxPoints(k));

        });
      double avg = gradeBook.calculateAverage(this, a);
      writer.printf("Average: %.2f\n", avg);
      writer.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
      writer.close();
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername(){
    return username;
  }
}
