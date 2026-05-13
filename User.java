import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.HashMap;

public abstract class User {
  protected String username;
  protected String password;
  protected String role;
  private static HashMap<String, String> login;

  public User(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  //Load the login info into a hashmap
  public static boolean loadLoginsFromTextFile(){
    login = new HashMap<>();
    try {
      File file = new File("users.txt");
      if (!file.exists()) return false;

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] credentials = line.split(",");
        if (credentials.length >= 2) {
          login.put(credentials[0], credentials[1]);
        }
      }

      scanner.close();
      return true;
    } catch (FileNotFoundException e) {
    }
    return false;
  }

  //Checks the login info against the hashmap to see if the login exists
  public static boolean login(String inputUser, String inputPass) {
    if(login.get(inputUser).equals(inputPass)){
      return true;
    } else {
    return false;
    }
  }

  //Both the Instructor and Student class have a method that does this but they do it slightly differently.
  public void exportGradeFile(Student s, GradeBook gradeBook, Assignment a) {
    try {
      PrintWriter writer = new PrintWriter(username + "_grades.txt");
      String [] info = User.returnData(username);
      writer.println("Name: " + info[3]);
      writer.println("Username: " + username);
      writer.println("Email: " + info[4]);
      HashMap<String, Double> grades = gradeBook.getGradesForStudent(s);
      grades.forEach((k,v) -> {
          writer.println(k + ": " + v + "/" + a.getMaxPoints(k));

        });
      double avg = gradeBook.calculateAverage(s, a);
      writer.printf("Average: %.2f\n", avg);
      writer.println("Letter Grade: " + gradeBook.determineLetterGrade(avg));
      writer.close();
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }


  //Add a user to the users.txt file
  public static void addLogin(String inputUsername, String inputPassword, String inputName, String inputEmail){
    try{
      FileWriter fw = new FileWriter("users.txt", true);
      PrintWriter writer = new PrintWriter(fw);
      writer.println(inputUsername + "," + inputPassword + ",Student," + inputName + "," + inputEmail);
      writer.close();
      fw.close();
      login.put(inputUsername, inputPassword);
    } catch(IOException e){
      System.err.println(e.getMessage());
    }
  }

  //Get all data in an array of Strings
  public static String[] returnData(String username){
    String[] info = {};
    try{
    File file = new File("users.txt");
      if (!file.exists()) return info;

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        info = line.split(",");
        if (info.length >= 5) {
          if (info[0].equals(username)) {
            scanner.close();
            return info;
          }
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
    }
    return info;
  }

  //Check if a student exists, used in data validation
  public static boolean checkStudentExistence(String username){
    if (login.containsKey(username)){
      return true;
    } else {
      return false;
    }
  }

  //Simplified form of returnData
  public static String getRole(String username) {
    return User.returnData(username)[2];
  }

}
