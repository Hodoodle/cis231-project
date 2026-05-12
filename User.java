import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileWriter;

public abstract class User {
  protected String username;
  protected String password;
  protected String role;

  public User(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public static boolean login(String inputUser, String inputPass) {
    try {
      File file = new File("users.txt");
      if (!file.exists()) return false;

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] credentials = line.split(",");
        if (credentials.length >= 2) {
          if (credentials[0].equals(inputUser) && credentials[1].equals(inputPass)) {
            scanner.close();
            return true;
          }
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
    }
    return false;
  }

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
  

  public static void addLogin(String inputUsername, String inputPassword, String inputName, String inputEmail){
    try{
      FileWriter fw = new FileWriter("users.txt", true);
      PrintWriter writer = new PrintWriter(fw);
      writer.println(inputUsername + "," + inputPassword + ",Student," + inputName + "," + inputEmail);
      writer.close();
      fw.close();
    } catch(IOException e){
      System.err.println(e.getMessage());
    }
  }

  public String getRole() {
    return role;
  }

  public String getUsername() {
    return username;
  }
}
