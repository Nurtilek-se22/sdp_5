// Education Platform Frontend JavaScript
class EducationPlatform {
    constructor() {
        this.currentUser = null;
        this.userType = null;
        this.apiBase = 'http://localhost:8080/api';
        this.init();
    }

    init() {
        console.log('Инициализация Education Platform...');
        this.setupEventListeners();
        this.loadDemoData();
        console.log('Демо-данные загружены:', {
            students: this.demoStudents,
            teachers: this.demoTeachers,
            courses: this.demoCourses
        });
    }

    setupEventListeners() {
        // Close modal when clicking outside
        window.addEventListener('click', (event) => {
            const modal = document.getElementById('loginModal');
            if (event.target === modal) {
                this.closeLoginModal();
            }
        });

        // Smooth scrolling for navigation links
        document.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const targetId = link.getAttribute('href').substring(1);
                const targetElement = document.getElementById(targetId);
                if (targetElement) {
                    targetElement.scrollIntoView({ behavior: 'smooth' });
                }
            });
        });
    }

    loadDemoData() {
        // Demo data for testing
        this.demoStudents = [
            { id: 1, name: 'Alice Johnson', email: 'alice.johnson@email.com', studentId: '1001' },
            { id: 2, name: 'Bob Smith', email: 'bob.smith@email.com', studentId: '1002' }
        ];

        this.demoTeachers = [
            { id: 1, name: 'Dr. Sarah Wilson', email: 'sarah.wilson@university.edu', teacherId: '2001', department: 'Computer Science' }
        ];

        this.demoCourses = [
            { id: 1, name: 'Advanced Calculus', code: 'MATH301' },
            { id: 2, name: 'Java Programming', code: 'CS201' }
        ];

        this.demoGrades = [
            { studentId: 1, courseId: 1, assignment: 'Midterm Exam', score: 88.5, date: '2024-01-15' },
            { studentId: 1, courseId: 1, assignment: 'Final Exam', score: 92.0, date: '2024-02-15' },
            { studentId: 1, courseId: 2, assignment: 'Assignment 1', score: 85.0, date: '2024-01-20' },
            { studentId: 1, courseId: 2, assignment: 'Project', score: 90.0, date: '2024-02-10' },
            { studentId: 2, courseId: 1, assignment: 'Midterm Exam', score: 75.0, date: '2024-01-15' },
            { studentId: 2, courseId: 1, assignment: 'Final Exam', score: 82.0, date: '2024-02-15' }
        ];
    }

    // Modal functions
    openLoginModal() {
        document.getElementById('loginModal').style.display = 'block';
        document.body.style.overflow = 'hidden';
    }

    closeLoginModal() {
        document.getElementById('loginModal').style.display = 'none';
        document.body.style.overflow = 'auto';
    }

    switchTab(tab) {
        // Update tab buttons
        document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
        document.querySelector(`[onclick="switchTab('${tab}')"]`).classList.add('active');

        // Update forms
        document.querySelectorAll('.login-form').forEach(form => form.classList.remove('active'));
        document.getElementById(tab + 'Login').classList.add('active');
    }

    // Authentication functions
    async loginStudent(event) {
        event.preventDefault();
        const email = document.getElementById('studentEmail').value;
        const password = document.getElementById('studentPassword').value;

        console.log('Попытка входа студента:', email);
        console.log('Доступные студенты:', this.demoStudents);

        // Demo authentication - in real app, this would be an API call
        const student = this.demoStudents.find(s => s.email === email);
        
        if (student) {
            console.log('Студент найден:', student);
            this.currentUser = student;
            this.userType = 'student';
            this.showStudentDashboard();
            this.closeLoginModal();
            this.showNotification('Добро пожаловать, ' + student.name + '!', 'success');
        } else {
            console.log('Студент не найден для email:', email);
            this.showNotification('Неверные учетные данные', 'error');
        }
    }

    async loginTeacher(event) {
        event.preventDefault();
        const email = document.getElementById('teacherEmail').value;
        const password = document.getElementById('teacherPassword').value;

        console.log('Попытка входа преподавателя:', email);
        console.log('Доступные преподаватели:', this.demoTeachers);

        // Demo authentication
        const teacher = this.demoTeachers.find(t => t.email === email);
        
        if (teacher) {
            console.log('Преподаватель найден:', teacher);
            this.currentUser = teacher;
            this.userType = 'teacher';
            this.showTeacherDashboard();
            this.closeLoginModal();
            this.showNotification('Добро пожаловать, ' + teacher.name + '!', 'success');
        } else {
            console.log('Преподаватель не найден для email:', email);
            this.showNotification('Неверные учетные данные', 'error');
        }
    }

    logout() {
        this.currentUser = null;
        this.userType = null;
        this.hideAllDashboards();
        this.showNotification('Вы вышли из системы', 'info');
    }

    // Dashboard functions
    showStudentDashboard() {
        this.hideAllDashboards();
        document.getElementById('studentDashboard').classList.remove('hidden');
        this.updateStudentDashboard();
    }

    showTeacherDashboard() {
        this.hideAllDashboards();
        document.getElementById('teacherDashboard').classList.remove('hidden');
        this.updateTeacherDashboard();
    }

    hideAllDashboards() {
        document.getElementById('studentDashboard').classList.add('hidden');
        document.getElementById('teacherDashboard').classList.add('hidden');
    }

    updateStudentDashboard() {
        if (!this.currentUser) return;

        const studentGrades = this.demoGrades.filter(g => g.studentId === this.currentUser.id);
        const studentCourses = [...new Set(studentGrades.map(g => g.courseId))];
        
        // Update stats
        document.getElementById('studentCoursesCount').textContent = studentCourses.length;
        
        const averageGrade = studentGrades.length > 0 
            ? (studentGrades.reduce((sum, g) => sum + g.score, 0) / studentGrades.length).toFixed(1)
            : 0;
        document.getElementById('studentAverageGrade').textContent = averageGrade + '%';
        
        // Update courses list
        const coursesList = document.getElementById('studentCoursesList');
        coursesList.innerHTML = '';
        
        studentCourses.forEach(courseId => {
            const course = this.demoCourses.find(c => c.id === courseId);
            const courseGrades = studentGrades.filter(g => g.courseId === courseId);
            const courseAverage = (courseGrades.reduce((sum, g) => sum + g.score, 0) / courseGrades.length).toFixed(1);
            
            const courseItem = document.createElement('div');
            courseItem.className = 'course-item';
            courseItem.innerHTML = `
                <h4>${course.name}</h4>
                <p>Код: ${course.code}</p>
                <p>Средняя оценка: ${courseAverage}%</p>
                <p>Оценок: ${courseGrades.length}</p>
            `;
            coursesList.appendChild(courseItem);
        });

        // Update grades list
        const gradesList = document.getElementById('studentGradesList');
        gradesList.innerHTML = '';
        
        studentGrades.forEach(grade => {
            const course = this.demoCourses.find(c => c.id === grade.courseId);
            const letterGrade = this.getLetterGrade(grade.score);
            
            const gradeItem = document.createElement('div');
            gradeItem.className = 'grade-item';
            gradeItem.innerHTML = `
                <h4>${grade.assignment}</h4>
                <p>Курс: ${course.name}</p>
                <p>Оценка: ${grade.score}% (${letterGrade})</p>
                <p>Дата: ${grade.date}</p>
            `;
            gradesList.appendChild(gradeItem);
        });
    }

    updateTeacherDashboard() {
        if (!this.currentUser) return;

        // Update all teacher dashboard components
        this.updateTeacherStats();
        this.updateGradeFormDropdowns();
        this.updateStudentsList();
        this.updateCourseStatistics();
        this.updateStudentCourseDropdown();
    }

    updateStudentsList() {
        const studentsList = document.getElementById('studentsList');
        studentsList.innerHTML = '';
        
        this.demoStudents.forEach(student => {
            const studentGrades = this.demoGrades.filter(g => g.studentId === student.id);
            const averageGrade = studentGrades.length > 0 
                ? (studentGrades.reduce((sum, g) => sum + g.score, 0) / studentGrades.length).toFixed(1)
                : 'Нет оценок';
            
            const studentItem = document.createElement('div');
            studentItem.className = 'student-item';
            studentItem.innerHTML = `
                <div class="student-info">
                    <h4>${student.name}</h4>
                    <p>Email: ${student.email}</p>
                    <p>ID: ${student.studentId}</p>
                    <p>Средняя оценка: ${averageGrade}%</p>
                </div>
                <div class="student-actions">
                    <button class="btn btn-secondary btn-small" onclick="viewStudentGrades(${student.id})">
                        <i class="fas fa-eye"></i> Оценки
                    </button>
                    <button class="btn btn-primary btn-small" onclick="addGradeForStudent(${student.id})">
                        <i class="fas fa-plus"></i> Оценка
                    </button>
                </div>
            `;
            studentsList.appendChild(studentItem);
        });
    }

    updateTeacherStats() {
        const allStudents = this.demoStudents.length;
        const allCourses = this.demoCourses.length;
        const allGrades = this.demoGrades;
        const overallAverage = allGrades.length > 0 
            ? (allGrades.reduce((sum, g) => sum + g.score, 0) / allGrades.length).toFixed(1)
            : 0;

        document.getElementById('teacherStudentsCount').textContent = allStudents;
        document.getElementById('teacherCoursesCount').textContent = allCourses;
        document.getElementById('teacherAverageGrade').textContent = overallAverage + '%';
    }

    updateStudentCourseDropdown() {
        const courseSelect = document.getElementById('studentCourse');
        
        // Clear existing options except the first one
        courseSelect.innerHTML = '<option value="">Выберите курс (необязательно)</option>';
        
        // Add courses
        this.demoCourses.forEach(course => {
            const option = document.createElement('option');
            option.value = course.id;
            option.textContent = course.name;
            courseSelect.appendChild(option);
        });
    }

    updateGradeFormDropdowns() {
        const studentSelect = document.getElementById('gradeStudent');
        const courseSelect = document.getElementById('gradeCourse');

        // Clear existing options
        studentSelect.innerHTML = '';
        courseSelect.innerHTML = '';

        // Add students
        this.demoStudents.forEach(student => {
            const option = document.createElement('option');
            option.value = student.id;
            option.textContent = student.name;
            studentSelect.appendChild(option);
        });

        // Add courses
        this.demoCourses.forEach(course => {
            const option = document.createElement('option');
            option.value = course.id;
            option.textContent = course.name;
            courseSelect.appendChild(option);
        });
    }

    updateCourseStatistics() {
        const statisticsDiv = document.getElementById('courseStatistics');
        statisticsDiv.innerHTML = '';

        this.demoCourses.forEach(course => {
            const courseGrades = this.demoGrades.filter(g => g.courseId === course.id);
            const courseStudents = [...new Set(courseGrades.map(g => g.studentId))];
            
            if (courseGrades.length > 0) {
                const average = (courseGrades.reduce((sum, g) => sum + g.score, 0) / courseGrades.length).toFixed(1);
                
                const statItem = document.createElement('div');
                statItem.className = 'stat-item';
                statItem.innerHTML = `
                    <span class="label">${course.name}</span>
                    <span class="value">${average}% (${courseStudents.length} студентов)</span>
                `;
                statisticsDiv.appendChild(statItem);
            }
        });
    }

    async addGrade(event) {
        event.preventDefault();
        
        const studentId = parseInt(document.getElementById('gradeStudent').value);
        const courseId = parseInt(document.getElementById('gradeCourse').value);
        const assignment = document.getElementById('gradeAssignment').value;
        const score = parseFloat(document.getElementById('gradeScore').value);

        // Add new grade to demo data
        const newGrade = {
            studentId: studentId,
            courseId: courseId,
            assignment: assignment,
            score: score,
            date: new Date().toISOString().split('T')[0]
        };

        this.demoGrades.push(newGrade);

        // Update dashboard
        this.updateCourseStatistics();
        
        // Clear form
        document.getElementById('addGradeForm').reset();
        
        this.showNotification(`Оценка добавлена: ${assignment} - ${score}%`, 'success');
    }

    async addStudent(event) {
        event.preventDefault();
        
        const name = document.getElementById('studentName').value.trim();
        const email = document.getElementById('studentEmail').value.trim();
        const studentId = document.getElementById('studentId').value.trim();
        const courseId = document.getElementById('studentCourse').value;

        // Validation
        if (!name || !email || !studentId) {
            this.showNotification('Пожалуйста, заполните все обязательные поля', 'error');
            return;
        }

        // Check if email already exists
        if (this.demoStudents.find(s => s.email === email)) {
            this.showNotification('Студент с таким email уже существует', 'error');
            return;
        }

        // Check if student ID already exists
        if (this.demoStudents.find(s => s.studentId === studentId)) {
            this.showNotification('Студент с таким ID уже существует', 'error');
            return;
        }

        // Generate new student ID
        const newStudentId = Math.max(...this.demoStudents.map(s => s.id)) + 1;

        // Create new student
        const newStudent = {
            id: newStudentId,
            name: name,
            email: email,
            studentId: studentId
        };

        // Add to demo data
        this.demoStudents.push(newStudent);

        // If course is selected, add enrollment (this would be handled by backend in real app)
        if (courseId) {
            console.log(`Студент ${name} записан на курс ${courseId}`);
        }

        // Update dashboard
        this.updateGradeFormDropdowns();
        this.updateStudentsList();
        this.updateTeacherStats();
        
        // Clear form
        document.getElementById('addStudentForm').reset();
        
        this.showNotification(`Студент ${name} успешно добавлен!`, 'success');
    }

    // Utility functions
    getLetterGrade(score) {
        if (score >= 90) return 'A';
        if (score >= 80) return 'B';
        if (score >= 70) return 'C';
        if (score >= 60) return 'D';
        return 'F';
    }

    showNotification(message, type = 'info') {
        // Create notification element
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        
        // Style the notification
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 1rem 2rem;
            border-radius: 8px;
            color: white;
            font-weight: 500;
            z-index: 3000;
            animation: slideIn 0.3s ease;
            max-width: 300px;
        `;

        // Set background color based on type
        const colors = {
            success: '#28a745',
            error: '#dc3545',
            info: '#17a2b8',
            warning: '#ffc107'
        };
        notification.style.backgroundColor = colors[type] || colors.info;

        // Add to page
        document.body.appendChild(notification);

        // Remove after 3 seconds
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => {
                if (notification.parentNode) {
                    notification.parentNode.removeChild(notification);
                }
            }, 300);
        }, 3000);
    }
}

// Global functions for HTML onclick handlers
let app;

function openLoginModal() {
    app.openLoginModal();
}

function closeLoginModal() {
    app.closeLoginModal();
}

function switchTab(tab) {
    app.switchTab(tab);
}

function loginStudent(event) {
    app.loginStudent(event);
}

function loginTeacher(event) {
    app.loginTeacher(event);
}

function logout() {
    app.logout();
}

function addGrade(event) {
    app.addGrade(event);
}

function addStudent(event) {
    app.addStudent(event);
}

function viewStudentGrades(studentId) {
    const student = app.demoStudents.find(s => s.id === studentId);
    if (student) {
        app.showNotification(`Просмотр оценок для ${student.name}`, 'info');
        // В реальном приложении здесь был бы переход к детальному просмотру
    }
}

function addGradeForStudent(studentId) {
    const student = app.demoStudents.find(s => s.id === studentId);
    if (student) {
        // Автоматически выбираем студента в форме добавления оценки
        document.getElementById('gradeStudent').value = studentId;
        app.showNotification(`Добавление оценки для ${student.name}`, 'info');
    }
}

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    app = new EducationPlatform();
});

// Add CSS animations for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);
