import auth.AuthenticationSystem;
import users.User;
import users.Teacher;
import students.Student;

/**
 * Clean Education Platform Demo
 * Demonstrates the complete functionality with clean code
 */
public class EducationPlatformDemo {
    public static void main(String[] args) {
        System.out.println("🎓 Education Platform - Clean Demo");
        System.out.println("==================================\n");
        
        // Initialize authentication system
        AuthenticationSystem auth = new AuthenticationSystem();
        
        // 1. Student Registration
        System.out.println("1. Student Registration:");
        auth.registerStudent("Alice Johnson", "alice.johnson@email.com", 1001);
        auth.registerStudent("Bob Smith", "bob.smith@email.com", 1002);
        
        // 2. Teacher Login
        System.out.println("\n2. Teacher Login:");
        User teacher = auth.loginTeacher(2001);
        
        if (teacher instanceof Teacher) {
            Teacher teacherObj = (Teacher) teacher;
            
            // 3. Students Enroll in Courses
            System.out.println("\n3. Students Enrolling:");
            Student alice = new Student("Alice Johnson", "alice.johnson@email.com", 1001);
            Student bob = new Student("Bob Smith", "bob.smith@email.com", 1002);
            
            alice.enrollInCourse("Advanced Calculus");
            alice.enrollInCourse("Java Programming");
            bob.enrollInCourse("Advanced Calculus");
            
            // 4. Teacher Adds Grades
            System.out.println("\n4. Adding Grades:");
            addGrade(alice, "Advanced Calculus", 88.5, "Midterm Exam", "2024-01-15");
            addGrade(alice, "Advanced Calculus", 92.0, "Final Exam", "2024-02-15");
            addGrade(alice, "Java Programming", 85.0, "Assignment 1", "2024-01-20");
            addGrade(alice, "Java Programming", 90.0, "Project", "2024-02-10");
            
            addGrade(bob, "Advanced Calculus", 75.0, "Midterm Exam", "2024-01-15");
            addGrade(bob, "Advanced Calculus", 82.0, "Final Exam", "2024-02-15");
            
            // 5. View Grades
            System.out.println("\n5. Viewing Grades:");
            viewGrades(alice, "Advanced Calculus");
            viewGrades(bob, "Advanced Calculus");
            
            // 6. Course Statistics
            System.out.println("\n6. Course Statistics:");
            showStatistics(alice, bob, "Advanced Calculus");
            
            // 7. Teacher Dashboard
            System.out.println("\n7. Teacher Dashboard:");
            System.out.println(teacherObj.getDashboard());
            
            // 8. Student Dashboards
            System.out.println("\n8. Student Dashboards:");
            System.out.println(alice.getDashboard());
            System.out.println(bob.getDashboard());
            
            // 9. Logout
            System.out.println("\n9. Logout:");
            teacherObj.logout();
        }
        
        System.out.println("\n✅ Demo Complete - Clean Code!");
    }
    
    private static void addGrade(Student student, String courseName, double score, 
                               String assignment, String date) {
        students.Grade grade = new students.Grade(courseName, score, assignment, date);
        student.addGrade(grade);
        System.out.println("✓ Grade added: " + student.getName() + " - " + 
                          assignment + " (" + score + "%)");
    }
    
    private static void viewGrades(Student student, String courseName) {
        System.out.println("\nGrades for " + student.getName() + " in " + courseName + ":");
        for (students.Grade grade : student.getGradesForCourse(courseName)) {
            System.out.println("  " + grade.toString());
        }
    }
    
    private static void showStatistics(Student alice, Student bob, String courseName) {
        double aliceAvg = alice.getGradesForCourse(courseName).stream()
            .mapToDouble(g -> g.getScore()).average().orElse(0.0);
        double bobAvg = bob.getGradesForCourse(courseName).stream()
            .mapToDouble(g -> g.getScore()).average().orElse(0.0);
        double overallAvg = (aliceAvg + bobAvg) / 2;
        
        System.out.println("Course: " + courseName);
        System.out.println("Alice's Average: " + String.format("%.1f", aliceAvg) + "%");
        System.out.println("Bob's Average: " + String.format("%.1f", bobAvg) + "%");
        System.out.println("Overall Average: " + String.format("%.1f", overallAvg) + "%");
    }
    
}
