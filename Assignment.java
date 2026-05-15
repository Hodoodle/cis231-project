import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

  // Loads assignments from assignment.txt into hashmaps
  public boolean loadAssignmentsFromTextFile() {
    try {
      File file = new File("assignments.txt");
      if (!file.exists()) return false;
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
      return true;
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  // Adds assignment to assignments.txt and assignment hashmaps
  public void addAssignment(String name, int points){
    int assignmentId = numAssignments + 1;
    assignmentIndex.put(assignmentId, name);
    assignmentPoints.put(assignmentId, points);
    try{
      FileWriter fw = new FileWriter("assignments.txt", true);
      PrintWriter writer = new PrintWriter(fw);
      writer.println(name + "," + points);
      writer.close();
      fw.close();
    } catch(IOException e){
      System.err.println(e.getMessage());
    }

  }

  // Display all assignments
  public void displayAssignmentIndex() {
    assignmentIndex.forEach((k, v) -> {
      System.out.println("Id " + k + ": " + v + ", " + getMaxPoints(k) + "points.");
    });
  }

  // Get max points from ID
  public int getMaxPoints(int id) {
    return assignmentPoints.get(id);
  }

  // Overload to get max points with name
  public int getMaxPoints(String name) {
    for (int i : assignmentIndex.keySet()){
      if (assignmentIndex.get(i).equals(name)){
        return assignmentPoints.get(i);
      } else {
        return -1;
      }
    }
    return 0;
  }

  // Get name from assignment ID
  public String getName(int id){
    return assignmentIndex.get(id);
  }

  // Check if assignment exists
  public boolean assignmentExists(int id){
    if(assignmentIndex.containsKey(id)){
      return true;
    } else {
      return false;
    }
  }
}
