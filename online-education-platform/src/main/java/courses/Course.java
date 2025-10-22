package courses;

public class Course {
    private String courseName;
    private String instructor;
    private int duration;
    private String description;
    private double price;
    private String courseType;
    
    public Course(String courseName, String instructor, int duration, String description, double price, String courseType) {
        this.courseName = courseName;
        this.instructor = instructor;
        this.duration = duration;
        this.description = description;
        this.price = price;
        this.courseType = courseType;
    }

    public String getCourseName() { return courseName; }
    public String getInstructor() { return instructor; }
    public int getDuration() { return duration; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCourseType() { return courseType; }
    
    @Override
    public String toString() {
        return courseName + " (" + courseType + ") - $" + price;
    }
}
