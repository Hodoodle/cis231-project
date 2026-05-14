public class Instructor extends User {

  public Instructor(String username) {
    String[] data = User.returnData(username);
    super(data[0], data[1], data[2]);
  }

  // Interfaces with the Gradebook class to add a student into the gradebook
  public void addStudent(Student s, GradeBook g) {
    g.addStudent(s);
  }

  // Interfaces with the Assignemnt class to add assignments
  public void addAssignment(Assignment a, String aName, int aPoints) {
    a.addAssignment(aName, aPoints);
  }

  // Interfaces with the Gradebook class to add assignment grades
  public void recordGrade(Student s, double grade, GradeBook g, Assignment a, int id) {
    g.addGrade(s, a, id, grade);
  }

  // Interfaces with the gradebok class to display all student grades
  public void viewAllGrades(GradeBook g, Assignment a) {
    g.printAllGrades(a);
  }
  
}
