import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.HashMap;

public class Login {
  private static HashMap<String, String> login;

  // Load the login info into a hashmap
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

  // Check credentials against login hashmap
  public static boolean login(String inputUser, String inputPass) {
    if(login.get(inputUser).equals(inputPass)){
      return true;
    } else {
    return false;
    }
  }
  
  // Add login info to hashmap and users.txt
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

  // Check if a student exists
  public static boolean checkStudentExistence(String username){
    if (username.equals(Course.getInstructorUsername())){
      return false;
    } else if (login.containsKey(username)) {
      return true;
    } else {
      return false;
    }
  }

  // Print a list of all students
  public static void viewAllStudents(){
    login.forEach((k,v) -> {
      if(!k.equals(Course.getInstructorUsername())) {
        System.out.printf("%-1s \t%15s\n", "Name: " + User.returnData(k)[3], "Username: " + k);
      }
    });
  }
}
