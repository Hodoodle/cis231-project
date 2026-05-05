import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    GradeBook gradeBook = new GradeBook();

    gradeBook.loadGradesFromTextFile();

    System.out.print("Enter Username: ");
    String username = scanner.nextLine();
    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    if (User.login(username, password)) {
      String role = "";
      try {
        Scanner fileScanner = new Scanner(new File("users.txt"));
        while (fileScanner.hasNextLine()) {
          String[] parts = fileScanner.nextLine().split(",");
          if (parts[0].equals(username)) {
            role = parts[2];
            break;
          }
        }
        fileScanner.close();
      } catch (FileNotFoundException e) {
      }

      if (role.equals("Instructor")) {
        Instructor instructor = new Instructor(username, password, username);
        boolean running = true;
        while (running) {
          System.out.println(
              "1. Add Student\n"
                  + "2. Record Grade\n"
                  + "3. View All Grades\n"
                  + "4. Export Student Grade File\n"
                  + "5. Exit");
          String choice = scanner.nextLine();
          if (choice.equals("1")) {
            System.out.print("Student Username: ");
            String sUser = scanner.nextLine();
            System.out.print("Student Password: ");
            String sPass = scanner.nextLine();
            System.out.print("Student Name: ");
            String sName = scanner.nextLine();
            System.out.print("Student Email: ");
            String sEmail = scanner.nextLine();
            Student s = new Student(sUser, sPass, sName, sEmail);
            User.addLogin(sUser, sPass);
            instructor.addStudent(s, gradeBook);
          } else if (choice.equals("2")) {
            System.out.print("Student Username: ");
            String sUser = scanner.nextLine();
            System.out.print("Grade: ");
            double g = Double.parseDouble(scanner.nextLine());
            Student s = new Student(sUser, "pass", sUser, "email");
            instructor.recordGrade(s, g, gradeBook);
          } else if (choice.equals("3")) {
            instructor.viewAllGrades(gradeBook);
          } else if (choice.equals("4")) {
            System.out.print("Student Username: ");
            String sUser = scanner.nextLine();
            Student s = new Student(sUser, "pass", sUser, "email");
            instructor.exportStudentGradeFile(s, gradeBook);
          } else if (choice.equals("5")) {
            running = false;
          }
        }
      } else if (role.equals("Student")) {
        Student student = new Student(username, password, username, "email");
        boolean running = true;
        while (running) {
          System.out.println("1. View Grades\n2. Export Grades\n3. Exit");
          String choice = scanner.nextLine();
          if (choice.equals("1")) {
            student.viewOwnGrades(gradeBook);
          } else if (choice.equals("2")) {
            student.exportOwnGradeFile(gradeBook);
          } else if (choice.equals("3")) {
            running = false;
          }
        }
      }
    } else {
      System.out.println("Login Failed, reboot and try again.");
    }
    gradeBook.saveGradesToTextFile();
    scanner.close();
  }
}
