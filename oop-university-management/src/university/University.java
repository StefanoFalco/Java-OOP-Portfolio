package university;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	private String name;
	private String rectorFirst;
	private String rectorLast;
	private List<Student> students= new LinkedList<>();
	private int indexStudents= 10000;
	private static final int maxStudents= 100;
	private static final int maxCoures= 50;
	private List<Course> courses= new LinkedList<>();
	private int indexCourses= 10;

// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name= name;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		this.rectorFirst= first;
		this.rectorLast= last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		return rectorFirst + " " + rectorLast;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		Student s= new Student(first, last, indexStudents);
		students.add(s);
            logger.info("Enrolled student " + first + " " + last + " with ID " + indexStudents);
            return indexStudents++;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		return students.get(id-10000).toString();
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		Course c= new Course(title,teacher,indexCourses);
		courses.add(c);
            logger.info("Activated course " + title + " with code " + indexCourses);
            return indexCourses++;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		return courses.get(code-10).toString();
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		students.get(studentID-10000).getCourses().add(courses.get(courseCode-10));
		courses.get(courseCode-10).getStudents().add(students.get(studentID-10000));
            logger.info("Student " + studentID + " registered to course " + courseCode);
    }
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		String res="";
		for(Student s : courses.get(courseCode-10).getStudents()) {
			res+=s.toString() + "\n";
		}
		return res;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		String res="";
		for(Course c : students.get(studentID-10000).getCourses()) {
			res+=c.toString() + "\n";
		}
		return res;
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		students.get(studentId-10000).getGrades().add(Integer.valueOf(grade));
		courses.get(courseID-10).getGrades().add(Integer.valueOf(grade));
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		Student s= students.get(studentId-10000);
		if(s.getGrades().size() != 0) {
			double media= s.getMedia();
			return "Student " + String.valueOf(studentId) + " : " + String.valueOf(media);
		}
		else return "Student " + String.valueOf(studentId) + " hasn't taken any exams";
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		Course c= courses.get(courseId-10);
		if(c.getGrades().size() != 0) {
			double media= c.getGrades().stream().mapToInt(x-> x.intValue()).average().getAsDouble();
			return "The average for the course " + c.getTitle() + " is: " + String.valueOf(media);
		}
		else return "No student has taken the exam in " + c.getTitle();
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
        String res = "";
        List<Student> best = students.stream()
            .filter(s -> !s.getGrades().isEmpty())
            .sorted(Comparator.comparingDouble(Student::getPoints).reversed())
            .limit(3)
            .collect(java.util.stream.Collectors.toList());

        for(Student s : best) {
            res += s.getFirst() + " " + s.getLast() + " : " + s.getPoints() + "\n";
        }

        return res;
    }
// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    private final static Logger logger = Logger.getLogger("University");

}

