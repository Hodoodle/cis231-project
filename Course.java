public class Course {
  private String courseName;
  private String meetingTime;
  private String roomNumber;
  private Instructor instructor;

  public Course(String courseName, String meetingTime, String roomNumber, Instructor instructor) {
    this.courseName = courseName;
    this.meetingTime = meetingTime;
    this.roomNumber = roomNumber;
    this.instructor = instructor;
  }

  public String getCourseDetails() {
    return courseName + " meets at " + meetingTime + " in " + roomNumber;
  }

  public static String getInstructorUsername(){
    return "profSmith";
  }
}
