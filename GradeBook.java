import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GradeBook {
  private HashMap<String, ArrayList<Double>> grades;

  public GradeBook() {
    grades = new HashMap<>();
  }

  public void addStudent(Student s) {
    if (!grades.containsKey(s.getUsername())) {
      grades.put(s.getUsername(), new ArrayList<>());
    }
  }

  public void addGrade(Student s, double grade) {
    if (!grades.containsKey(s.getUsername())) {
      grades.put(s.getUsername(), new ArrayList<>());
    }
    grades.get(s.getUsername()).add(grade);
  }

  public ArrayList<Double> getGradesForStudent(Student s) {
    return grades.getOrDefault(s.getUsername(), new ArrayList<>());
  }

  public double calculateAverage(Student s) {
    ArrayList<Double> studentGrades = getGradesForStudent(s);
    if (studentGrades.isEmpty()) return 0.0;
    double sum = 0;
    for (double g : studentGrades) {
      sum += g;
    }
    return sum / studentGrades.size();
  }

  public String determineLetterGrade(double average) {
    if (average >= 90) return "A";
    if (average >= 80) return "B";
    if (average >= 70) return "C";
    if (average >= 60) return "D";
    return "F";
  }

  public void saveGradesToTextFile() {
    try {
      PrintWriter writer = new PrintWriter("grades.txt");
      for (String username : grades.keySet()) {
        writer.print(username);
        for (double g : grades.get(username)) {
          writer.print("," + g);
        }
        writer.println();
      }
      writer.close();
    } catch (FileNotFoundException e) {
    }
  }

  public void loadGradesFromTextFile() {
    try {
      File file = new File("grades.txt");
      if (!file.exists()) return;
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        if (parts.length > 0) {
          String username = parts[0];
          ArrayList<Double> studentGrades = new ArrayList<>();
          for (int i = 1; i < parts.length; i++) {
            studentGrades.add(Double.parseDouble(parts[i]));
          }
          grades.put(username, studentGrades);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
    }
  }

  public HashMap<String, ArrayList<Double>> getAllGrades() {
    return grades;
  }
}
