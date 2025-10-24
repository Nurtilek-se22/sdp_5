import courses.Course;
import facade.EducationPlatformFacade;
import students.Student;
import users.Teacher;

public class EducationPlatformDemo {
    public static void main(String[] args) {
        System.out.println("Education Platform - Teacher and Student Demo");
        System.out.println("=============================================\n");

        EducationPlatformFacade facade = new EducationPlatformFacade();

        // Register students
        Student alice = facade.registerStudent("Alice Johnson", "alice.johnson@email.com", 1001);
        Student bob = facade.registerStudent("Bob Smith", "bob.smith@email.com", 1002);

        // Register and login teacher
        Teacher teacher = facade.registerTeacher("Prof. John Doe", "john.doe@university.edu", 2004, "Physics");

        // Teacher adds a new student
        teacher.addStudent(1003, "Charlie Brown", "charlie.brown@email.com");

        // Student login
        facade.loginStudent(1001);

        // Student enrolls in course
        Course mathCourse = facade.createMathCourse("Advanced Calculus", "Dr. Sarah Wilson", 6, "Calculus for advanced students", 100.0);
        alice.enrollInCourse(mathCourse.getCourseName());

        // Add grade for student
        alice.addGrade(new students.Grade("Advanced Calculus", 85.5, "Midterm Exam", "2024-01-15"));

        // Logout users
        facade.logout(alice.getId());
        facade.logout(teacher.getId());

        System.out.println("\nDemo Complete!");
    }
}