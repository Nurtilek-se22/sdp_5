package auth;

import users.User;
import users.Teacher;
import students.Student;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationSystem {
    private Map<Integer, User> loggedInUsers;
    private Map<Integer, Teacher> teachers;
    
    public AuthenticationSystem() {
        this.loggedInUsers = new HashMap<>();
        this.teachers = new HashMap<>();
        
        // Initialize some default teachers
        initializeDefaultTeachers();
    }

    private void initializeDefaultTeachers() {
        Teacher teacher1 = new Teacher("Dr. Sarah Wilson", "sarah.wilson@university.edu", 2001, "Computer Science");
        Teacher teacher2 = new Teacher("Prof. Michael Brown", "michael.brown@university.edu", 2002, "Mathematics");
        Teacher teacher3 = new Teacher("Dr. Emily Davis", "emily.davis@university.edu", 2003, "Programming");
        
        teachers.put(2001, teacher1);
        teachers.put(2002, teacher2);
        teachers.put(2003, teacher3);
    }
    

    public User loginTeacher(int teacherId) {
        Teacher teacher = teachers.get(teacherId);
        if (teacher != null) {
            loggedInUsers.put(teacherId, teacher);
            teacher.login();
            return teacher;
        } else {
            System.out.println("Teacher with ID " + teacherId + " not found.");
            return null;
        }
    }

    public User registerStudent(String name, String email, int studentId) {
        try {
            Student student = new Student(name, email, studentId);
            System.out.println("Student registered successfully: " + name);
            return student;
        } catch (Exception e) {
            System.out.println("Error registering student: " + e.getMessage());
            return null;
        }
    }

    public User registerTeacher(String name, String email, int teacherId, String department) {
        Teacher teacher = new Teacher(name, email, teacherId, department);
        teachers.put(teacherId, teacher);
        System.out.println("Teacher registered successfully: " + name);
        return teacher;
    }

    public void logout(int userId) {
        User user = loggedInUsers.get(userId);
        if (user != null) {
            user.logout();
            loggedInUsers.remove(userId);
        } else {
            System.out.println("No user with ID " + userId + " is currently logged in.");
        }
    }

    public User getCurrentUser(int userId) {
        return loggedInUsers.get(userId);
    }

    public boolean isLoggedIn(int userId) {
        return loggedInUsers.containsKey(userId);
    }

    public Teacher getTeacher(int teacherId) {
        return teachers.get(teacherId);
    }

    public Map<Integer, Teacher> getAllTeachers() {
        return new HashMap<>(teachers);
    }
}
