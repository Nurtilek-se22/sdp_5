package students;

public class Grade {
    private final String courseName;
    private final double score;
    private final String assignment;
    private final String date;
    private final String letterGrade;

    public Grade(String courseName, double score, String assignment, String date) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        if (assignment == null || assignment.trim().isEmpty()) {
            throw new IllegalArgumentException("Assignment name cannot be null or empty");
        }
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        
        this.courseName = courseName;
        this.score = score;
        this.assignment = assignment;
        this.date = date;
        this.letterGrade = calculateLetterGrade(score);
    }

    private String calculateLetterGrade(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    public String getCourseName() { return courseName; }
    public double getScore() { return score; }
    public String getAssignment() { return assignment; }
    public String getDate() { return date; }
    public String getLetterGrade() { return letterGrade; }
    
    @Override
    public String toString() {
        return String.format("Grade{courseName='%s', score=%.1f, assignment='%s', date='%s', letterGrade='%s'}", 
                            courseName, score, assignment, date, letterGrade);
    }
}
