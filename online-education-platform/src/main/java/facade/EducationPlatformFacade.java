package facade;

import auth.AuthenticationSystem;
import students.Student;
import users.Teacher;
import courses.Course;
import courses.CourseFactory;
import java.util.Map;


public class EducationPlatformFacade {

    private final AuthenticationSystem authSystem;

    public EducationPlatformFacade() {
        authSystem = new AuthenticationSystem();
    }

    public Student registerStudent(String name, String email, int studentId) {
        return (Student) authSystem.registerStudent(name, email, studentId);
    }

    public Teacher registerTeacher(String name, String email, int teacherId, String department) {
        return authSystem.registerTeacher(name, email, teacherId, department);
    }


    public Teacher loginTeacher(int teacherId) {
        return (Teacher) authSystem.loginTeacher(teacherId);
    }


    public Student loginStudent(int studentId) {
        return (Student) authSystem.loginStudent(studentId);
    }


    public Map<Integer, Teacher> getAllTeachers() {
        return authSystem.getAllTeachers();
    }


    public Map<Integer, Student> getAllStudents() {
        return authSystem.getAllStudents();
    }


    public Student getStudent(int studentId) {
        return authSystem.getStudent(studentId);
    }


    public Teacher getTeacher(int teacherId) {
        return authSystem.getTeacher(teacherId);
    }

    public Course createMathCourse(String name, String instructor, int duration, String description, double price) {
        return CourseFactory.createMathCourse(name, instructor, duration, description, price);
    }

    public Course createProgrammingCourse(String name, String instructor, int duration, String description, double price) {
        return CourseFactory.createProgrammingCourse(name, instructor, duration, description, price);
    }

    public void logout(int userId) {
        authSystem.logout(userId);
    }
}