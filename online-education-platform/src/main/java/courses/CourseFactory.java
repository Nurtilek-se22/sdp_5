package courses;

public class CourseFactory {
    

    public static Course createMathCourse(String name, String instructor, int duration, String description, double price) {
        return new Course(name, instructor, duration, description, price, "Mathematics");
    }
    

    public static Course createProgrammingCourse(String name, String instructor, int duration, String description, double price) {
        return new Course(name, instructor, duration, description, price, "Programming");
    }
    

    public static Course createScienceCourse(String name, String instructor, int duration, String description, double price) {
        return new Course(name, instructor, duration, description, price, "Science");
    }
}
