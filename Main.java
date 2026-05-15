import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    // Create handler classes
    GradeBook gradeBook = new GradeBook();
    Assignment assignment = new Assignment();

    // Error handling
    if (!gradeBook.loadGradesFromTextFile()){ 
      System.out.println("Something went wrong when loading in grades, check to see if the file has been deleted or corrupted.");
      scanner.close();
      return;
    }

    if (!assignment.loadAssignmentsFromTextFile()) {
      System.out.println("Something went wrong with loading in assignments, check to see if the file has been deleted or corrupted.");
      scanner.close();
      return;
    }

    if (!Login.loadLoginsFromTextFile()) {
      System.out.println("Something went wrong with loading in logins, check to see if the file has been deleted or corrupted.");
      scanner.close();
      return;
    }

    if (!Course.loadCourseInfoFromTextFile()){
      System.out.println("Something went wrong with loading in course info, the program will still function but some information may not be displayed as intended.");
    }

    // Login
    System.out.print("Enter Username: ");
    String username = scanner.nextLine();
    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    if (Login.login(username, password)) {
      String role = User.getRole(username);
      Course.printCourseInfo();

      if (role.equals("Instructor")) {
        Instructor instructor = new Instructor(username);
        boolean running = true;
        while (running) {
          System.out.println(
                    "1. Add Student\n"
                  + "2. Add Assignment\n"
                  + "3. Record Grade\n"
                  + "4. View All Students\n"
                  + "5. View All Grades\n"
                  + "6. Export Student Grade File\n"
                  + "7. Exit");
          String choice = scanner.nextLine();

          if (choice.equals("1")) { // Add Student
            System.out.print("Student Username: ");
            String sUser = scanner.nextLine();
            if(Login.checkStudentExistence(sUser)){
              System.out.println("A student with that username already exists.");
              continue;
            } else if(sUser.equals(Course.getInstructorUsername())){
              System.out.println("Student can not have the same username as instructor");
              continue;
            }
            System.out.print("Student Password: ");
            String sPass = scanner.nextLine();
            System.out.print("Student Name: ");
            String sName = scanner.nextLine();
            System.out.print("Student Email: ");
            String sEmail = scanner.nextLine();
            Login.addLogin(sUser, sPass, sName, sEmail);
            Student s = new Student(sUser);
            instructor.addStudent(s, gradeBook);

          } else if (choice.equals("2")) { // Add assignment
            System.out.print("Assignment name: ");
            String aName = scanner.nextLine();
            if(assignment.getMaxPoints(aName) == -1){
              System.out.println("An assignment with that name already exists");
              continue;
            }

            System.out.print("Max Points: ");
            int aPoints = 0;
            try{
              aPoints = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e) {
              System.out.println("Enter a valid number.");
              continue;
            }

            instructor.addAssignment(assignment, aName, aPoints);

          } else if (choice.equals("3")) { // Grade Assignment
            System.out.print("Student Username: ");
            String sUser = scanner.nextLine();
            if(!Login.checkStudentExistence(sUser)){
              System.out.println("Please enter a valid student username.");
              continue;
            }
            assignment.displayAssignmentIndex();
            System.out.print("Assignment Id: ");
            int aNum = 0;
            try {
              aNum = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e){
              System.out.println("Please enter a valid number.");
              continue;
            }
            
            if(!assignment.assignmentExists(aNum)){
              System.out.println("Enter a valid Assignment ID");
              continue;
            }
            System.out.print("Grade: ");
            double g = 0;
            try {
              g = Double.parseDouble(scanner.nextLine());
            } catch(NumberFormatException e){
              System.out.println("Please enter a valid number");
              continue;
            }
            Student s = new Student(sUser);
            instructor.recordGrade(s, g, gradeBook, assignment, aNum);

          } else if (choice.equals("4")) { // View All Students
            Login.viewAllStudents();

          } else if (choice.equals("5")) { // View All Grades
            instructor.viewAllGrades(gradeBook, assignment);

          } else if (choice.equals("6")) { // Export Student Grade
            System.out.print("Student Username: ");
            String sUser = scanner.nextLine();
            if(!Login.checkStudentExistence(sUser)){
              System.out.println("Please enter a valid student username.");
              continue;
            }
            Student s = new Student(sUser);
            instructor.exportGradeFile(s, gradeBook, assignment);

          } else if (choice.equals("7")) { // Exit
            running = false;
          }
        }
      } else if (role.equals("Student")) { // Student UI
        Student student = new Student(username);
        boolean running = true;
        while (running) {
          System.out.println(
                          "1. View Grades\n" 
                        + "2. Export Grades\n"
                        + "3. Exit");
          String choice = scanner.nextLine();

          if (choice.equals("1")) { // View own grades
            student.viewOwnGrades(gradeBook, assignment);

          } else if (choice.equals("2")) { // Export Grades
            student.exportGradeFile(gradeBook, assignment);

          } else if (choice.equals("3")) { // Exit
            running = false;
          }
        }
      } else {
        System.out.println("Something went wrong with the login file, try again later.");
      }
    } else {
      System.out.println("Login Failed, reboot and try again.");
    }
    gradeBook.saveGradesToTextFile();
    scanner.close();
  }
}
