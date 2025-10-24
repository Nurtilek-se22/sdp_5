package courses;

public class Course {
    private final String courseName;
    private final String instructor;
    private final int duration;
    private final String description;
    private final double price;
    private final String courseType;
    

    public Course(String courseName, String instructor, int duration, String description, double price, String courseType) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (instructor == null || instructor.trim().isEmpty()) {
            throw new IllegalArgumentException("Instructor name cannot be null or empty");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (courseType == null || courseType.trim().isEmpty()) {
            throw new IllegalArgumentException("Course type cannot be null or empty");
        }
        
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
