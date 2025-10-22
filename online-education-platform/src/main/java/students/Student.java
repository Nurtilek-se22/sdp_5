package students;

import users.User;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Grade> grades;
    private List<String> enrolledCourses;
    
    public Student(String name, String email, int studentId) {
        super(name, email, studentId, "Student");
        this.grades = new ArrayList<>();
        this.enrolledCourses = new ArrayList<>();
    }
    
    @Override
    public void login() {
        System.out.println("=== Student Login ===");
        System.out.println("Welcome back, " + name + "!");
        System.out.println("Student ID: " + id);
        System.out.println("Enrolled Courses: " + enrolledCourses.size());
        System.out.println("Average Grade: " + String.format("%.2f", getAverageGrade()) + "%");
        System.out.println("====================\n");
    }
    
    @Override
    public void logout() {
        System.out.println("Student " + name + " has logged out.");
    }
    
    @Override
    public String getDashboard() {
        StringBuilder dashboard = new StringBuilder();
        dashboard.append("=== Student Dashboard ===\n");
        dashboard.append("Name: ").append(name).append("\n");
        dashboard.append("Email: ").append(email).append("\n");
        dashboard.append("Student ID: ").append(id).append("\n");
        dashboard.append("Enrolled Courses: ").append(enrolledCourses.size()).append("\n");
        dashboard.append("Average Grade: ").append(String.format("%.2f", getAverageGrade())).append("%\n");
        
        if (!enrolledCourses.isEmpty()) {
            dashboard.append("\nEnrolled Courses:\n");
            for (String course : enrolledCourses) {
                dashboard.append("- ").append(course).append("\n");
            }
        }
        
        return dashboard.toString();
    }
    
    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }
    
    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }
    
    public List<Grade> getGradesForCourse(String courseName) {
        List<Grade> courseGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if (grade.getCourseName().equals(courseName)) {
                courseGrades.add(grade);
            }
        }
        return courseGrades;
    }
    
    public void enrollInCourse(String courseName) {
        if (!enrolledCourses.contains(courseName)) {
            this.enrolledCourses.add(courseName);
        }
    }
    
    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (Grade grade : grades) {
            sum += grade.getScore();
        }
        return sum / grades.size();
    }
    
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
}
