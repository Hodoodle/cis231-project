import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class Assignment {
  private HashMap<Integer, String> assignmentIndex;
  private HashMap<Integer, Integer> assignmentPoints;
  private int numAssignments;

  public Assignment(){
    assignmentIndex = new HashMap<>();
    assignmentPoints = new HashMap<>();
    numAssignments = 0;
  }

  public void loadAssignmentsFromTextFile() {
    try {
      File file = new File("assignments.txt");
      if (!file.exists()) return;
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        numAssignments++;
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        if (parts.length > 0) {
          String name = parts[0];
          int maxPoints = Integer.parseInt(parts[1]);
          assignmentIndex.put(numAssignments, name);
          assignmentPoints.put(numAssignments, maxPoints);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }

  public HashMap<Integer, String> getAssignmentIndex() {
    return assignmentIndex;
  }

  public int getMaxPoints(int id) {
    return assignmentPoints.get(id);
  }

  public String getAssignmentName(int id){
    return assignmentIndex.get(id);
  }

  public int getNumAssignments(){
    return numAssignments;
  }
}
