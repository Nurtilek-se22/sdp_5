package users;

import auth.AuthenticationSystem;
import students.Student;
import courses.Course;
import java.util.List;
import java.util.ArrayList;


public class Teacher extends User {
    private final String department;
    private final List<Course> assignedCourses;
    private AuthenticationSystem authSystem;

    public Teacher(String name, String email, int id, String department) {
        super(name, email, id, "Teacher");
        this.department = department;
        this.assignedCourses = new ArrayList<>();
    }

    public void setAuthSystem(AuthenticationSystem authSystem) {
        this.authSystem = authSystem;
    }

    @Override
    public void login() {
        System.out.println("=== Teacher Login ===");
        System.out.println("Welcome, Professor " + name + "!");
        System.out.println("Department: " + department);
        System.out.println("Teacher ID: " + id);
        System.out.println("Assigned Courses: " + assignedCourses.size());
        System.out.println("========================\n");
    }

    @Override
    public void logout() {
        System.out.println("Professor " + name + " has logged out.");
    }

    @Override
    public String getDashboard() {
        StringBuilder dashboard = new StringBuilder();
        dashboard.append("=== Teacher Dashboard ===\n");
        dashboard.append("Name: ").append(name).append("\n");
        dashboard.append("Email: ").append(email).append("\n");
        dashboard.append("Department: ").append(department).append("\n");
        dashboard.append("Teacher ID: ").append(id).append("\n");
        dashboard.append("Assigned Courses: ").append(assignedCourses.size()).append("\n");

        if (!assignedCourses.isEmpty()) {
            dashboard.append("\nAssigned Courses:\n");
            for (int i = 0; i < assignedCourses.size(); i++) {
                Course course = assignedCourses.get(i);
                dashboard.append((i + 1)).append(". ").append(course.getCourseName())
                        .append(" (").append(course.getCourseType()).append(")\n");
            }
        }

        return dashboard.toString();
    }

    public void addStudent(int studentId, String name, String email) {
        if (authSystem == null) {
            System.out.println("Error: Authentication system not initialized. Cannot add student.");
            return;
        }
        authSystem.registerStudent(name, email, studentId);
        System.out.println("Student " + name + " added by teacher " + this.getName());
    }

    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
            System.out.println("Course '" + course.getCourseName() + "' assigned to Professor " + name);
        }
    }

    public String getDepartment() { 
        return department; 
    }
    
    public List<Course> getAssignedCourses() { 
        return new ArrayList<>(assignedCourses); 
    }
}