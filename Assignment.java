public class Assignment {
  private String assignmentName;
  private double maxPossiblePoints;

  public Assignment(String assignmentName, double maxPossiblePoints) {
    this.assignmentName = assignmentName;
    this.maxPossiblePoints = maxPossiblePoints;
  }

  public String getAssignmentDetails() {
    return assignmentName + " (Max Points: " + maxPossiblePoints + ")";
  }
}
