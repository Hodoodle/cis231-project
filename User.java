import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

  public String getRole() {
    return role;
  }

  public String getUsername() {
    return username;
  }
}
