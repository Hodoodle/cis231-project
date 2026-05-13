public class Instructor extends User {
  private String name;
  private String email;

  public Instructor(String username) {
    String[] data = User.returnData(username);
    super(data[0], data[1], data[2]);
    this.name = data[3];
    this.email = data[4];
  }

  // Interfaces with the Gradebook class to add a student into the gradebook
  public void addStudent(Student s, GradeBook gradeBook) {
    gradeBook.addStudent(s);
  }

  // Interfaces with the Assignemnt class to add assignments
  public void addAssignment(Assignment a, String aName, int aPoints) {
    a.addAssignment(aName, aPoints);
  }

  // Interfaces with the Gradebook class to add assignment grades
  public void recordGrade(Student s, double g, GradeBook gradeBook, Assignment a, int id) {
    gradeBook.addGrade(s, a, id, g);
  }

  // Interfaces with the gradebok class to display all student grades
  public void viewAllGrades(GradeBook gradeBook, Assignment assignment) {
    gradeBook.printAllGrades(assignment);
  }
  
}
