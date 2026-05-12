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

  public HashMap<Integer, String> getAssignmentIndex() {
    return assignmentIndex;
  }

  public void displayAssignmentIndex() {
    assignmentIndex.forEach((k, v) -> {
      System.out.println("Id " + k + ": " + v);
    });
  }

  public int getMaxPoints(int id) {
    return assignmentPoints.get(id);
  }

  public String getName(int id){
    return assignmentIndex.get(id);
  }

  public boolean assignmentExists(int id){
    if(assignmentIndex.containsKey(id)){
      return true;
    } else {
      return false;
    }
  }

  public int getTotalPoints() {
    int sum = 0;
    for(int p : assignmentPoints.values()){
      sum += p;
    }
    return sum;
  }

  public int getNumAssignments(){
    return numAssignments;
  }
}
