import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class GradeBook {
  private HashMap<String, HashMap<String,Double>> student;
  private HashMap<String,Double> grades;

  public GradeBook() {
    student = new HashMap<>();
    grades = new HashMap<>();
  }

  public void addStudent(Student s) {
    if (!student.containsKey(s.getUsername())) {
      student.put(s.getUsername(), grades);
    }
  }

  public void addGrade(Student s, Assignment assignment, int a, double grade) {
    if (!student.containsKey(s.getUsername())) {
      student.put(s.getUsername(), grades);
    }
    student.get(s.getUsername()).put(assignment.getName(a), grade);
  }

  public HashMap<String, Double> getGradesForStudent(Student s) {
    return student.getOrDefault(s.getUsername(), grades);
  }

  public double calculateAverage(Student s, Assignment a) {

    HashMap<String, Double> studentGrades = student.get(s.getUsername());
    
    if (studentGrades.isEmpty()) return 0.0;
    double sum = 0;
    double possible = 0;
    for (String k : studentGrades.keySet()){
      sum += studentGrades.get(k);
      possible += a.getMaxPoints(k);
    }

    double avg = sum/possible;
    return avg;
  }

  public String determineLetterGrade(double avg) {
    double average = avg * 100;
    if (average >= 90) return "A";
    if (average >= 80) return "B";
    if (average >= 70) return "C";
    if (average >= 60) return "D";
    return "F";
  }

  public void saveGradesToTextFile() {
    try {
      PrintWriter writer = new PrintWriter("grades.txt");
      for (String username : student.keySet()) {
        writer.print(username);
        student.get(username).forEach((k,v) -> {
          writer.print(";" + k + "," + v);
        });
        writer.println();
      }
      writer.close();
    } catch (FileNotFoundException e) {
    }
  }

  public boolean loadGradesFromTextFile() {
    try {
      File file = new File("grades.txt");
      if (!file.exists()) return false;
      Scanner scanner = new Scanner(file);
      String assignmentName;
      double assignmentPoints;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(";");
        if (parts.length > 0) {
          String username = parts[0];
          HashMap<String, Double> studentGrades = new HashMap<>();
          for (int i = 1; i < parts.length; i++) {
            String[] innerParts = parts[i].split(",");
            assignmentName = innerParts[0];
            assignmentPoints = Double.parseDouble(innerParts[1]);
            studentGrades.put(assignmentName, assignmentPoints);
          }
          student.put(username, studentGrades);
        }
      }
      scanner.close();
      return true;
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public HashMap<String, HashMap<String, Double>> getAllGrades() {
    return student;
  }
}