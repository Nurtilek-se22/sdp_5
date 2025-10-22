package users;

import students.Student;
import courses.Course;
import java.util.List;
import java.util.ArrayList;

/**
 * Teacher class representing a teacher in the education platform
 */
public class Teacher extends User {
    private String department;
    private List<Course> assignedCourses;
    
    public Teacher(String name, String email, int id, String department) {
        super(name, email, id, "Teacher");
        this.department = department;
        this.assignedCourses = new ArrayList<>();
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
    
    /**
     * Assign a course to this teacher
     */
    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
            System.out.println("Course '" + course.getCourseName() + "' assigned to Professor " + name);
        }
    }
    
    // Getters
    public String getDepartment() { return department; }
    public List<Course> getAssignedCourses() { return assignedCourses; }
}
