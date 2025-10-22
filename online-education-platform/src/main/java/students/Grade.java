package students;

public class Grade {
    private String courseName;
    private double score;
    private String assignment;
    private String date;
    private String letterGrade;
    
    public Grade(String courseName, double score, String assignment, String date) {
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
