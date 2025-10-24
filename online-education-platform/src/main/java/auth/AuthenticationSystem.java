package auth;

import users.User;
import users.Teacher;
import students.Student;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationSystem {
    private final Map<Integer, User> loggedInUsers;
    private final Map<Integer, Teacher> teachers;
    private final Map<Integer, Student> students;

    public AuthenticationSystem() {
        this.loggedInUsers = new HashMap<>();
        this.teachers = new HashMap<>();
        this.students = new HashMap<>();
        
        initializeDefaultTeachers();
    }

    private void initializeDefaultTeachers() {
        Teacher teacher1 = new Teacher("Dr. Sarah Wilson", "sarah.wilson@university.edu", 2001, "Computer Science");
        Teacher teacher2 = new Teacher("Prof. Michael Brown", "michael.brown@university.edu", 2002, "Mathematics");
        Teacher teacher3 = new Teacher("Dr. Emily Davis", "emily.davis@university.edu", 2003, "Programming");

        teacher1.setAuthSystem(this);
        teacher2.setAuthSystem(this);
        teacher3.setAuthSystem(this);

        teachers.put(2001, teacher1);
        teachers.put(2002, teacher2);
        teachers.put(2003, teacher3);
    }


    public User registerStudent(String name, String email, int studentId) {
        validateUserInput(name, email, studentId);
        
        if (students.containsKey(studentId)) {
            System.out.println("Error: Student with ID " + studentId + " already exists.");
            return students.get(studentId);
        }
        Student student = new Student(name, email, studentId);
        students.put(studentId, student);
        System.out.println("Student registered successfully: " + name);
        return student;
    }

    public Teacher registerTeacher(String name, String email, int teacherId, String department) {
        validateUserInput(name, email, teacherId);
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        
        Teacher teacher = new Teacher(name, email, teacherId, department);
        teacher.setAuthSystem(this);
        teachers.put(teacherId, teacher);
        System.out.println("Teacher registered successfully: " + name);
        return teacher;
    }


    public User loginStudent(int studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            loggedInUsers.put(studentId, student);
            student.login();
            return student;
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
            return null;
        }
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

    public Student getStudent(int studentId) {
        return students.get(studentId);
    }

    public Map<Integer, Student> getAllStudents() {
        return new HashMap<>(students);
    }

    private void validateUserInput(String name, String email, int userId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email must be valid");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }
}