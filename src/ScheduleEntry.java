
import java.sql.Timestamp;
import java.util.Calendar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author evanlandis
 */
public class ScheduleEntry {
    private String Semester;
    private String CourseCode;
    private String StudentID;
    private String Status;
    private Timestamp Timestamp;
    // declare a timestamp field.
    
    
    
    //for timestamp from modules
    
    

    
    //constructor
    public ScheduleEntry(String Semester, String CourseCode, String StudentID, String Status, Timestamp Timestamp) {
        this.Semester = Semester;
        this.CourseCode = CourseCode;
        this.StudentID = StudentID;
        this.Status = Status;
        this.Timestamp = Timestamp;
    }

    //getters
    public String getSemester() {
        return Semester;
    }
    public String getCourseCode() {
        return CourseCode;
    }
    public String getStudentID() {
        return StudentID;
    }
    public String getStatus() {
        return Status;
    }
    
    public Timestamp getTimestamp() {
        return Timestamp;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
}
