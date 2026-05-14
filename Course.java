import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Course {
  private static String[] courseInfo;

  // Loads course info from a text file
  public static Boolean loadCourseInfoFromTextFile() {

    try {
        File file = new File("courseInfo.txt");

        if (!file.exists()) {
            return false;
        }

        Scanner scanner = new Scanner(file);

        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            courseInfo = line.split(",");
        }

        scanner.close();
        return true;
    }
    catch (FileNotFoundException e) {
        System.out.println("courseInfo.txt not found");
    }

    return false;
}

// Prints course info to the console
public static void printCourseInfo() {

    if (courseInfo.length >= 4) {
        System.out.println("Course Name: " + courseInfo[0]);
        System.out.println("Meeting Time: " + courseInfo[1]);
        System.out.println("Room Number: " + courseInfo[2]);
        System.out.println("Instructor: " + courseInfo[3]);
    }
    else {
        System.out.println("No course information found.");
    }
  }


  public static String getInstructorUsername() {
    return "profSmith";
  }

}
