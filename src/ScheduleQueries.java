
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author evanlandis
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
//        System.out.println("addSchedule invoked");
//        
//        connection = DBConnection.getConnection();
//        boolean exist = false;
//        try
//        {
//            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentID = ? and coursecode = ?");
//            getScheduleByStudent.setString(1, entry.getSemester());
//            getScheduleByStudent.setString(2, entry.getStudentID());
//            getScheduleByStudent.setString(3, entry.getCourseCode());
//            resultSet = getScheduleByStudent.executeQuery();
//
//            
//            while(resultSet.next())
//            {
//                exist = true;
//                break;
//            }
//        }
//        catch(SQLException sqlException)
//        {
//            sqlException.printStackTrace();
//        }
//        
//        if(exist)
//            return;
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester,coursecode,studentid,status,timestamp) values (?,?,?,?,?)");
            System.out.println(entry.getSemester()+entry.getStudentID()+entry.getCourseCode()+entry.getStatus());
            addScheduleEntry.setString(1, entry.getSemester());//not sure what format to use
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        System.out.println("added entry");
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester,String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentID = ? ");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();

            
            while(resultSet.next())
            {
                ScheduleEntry studentSchedule = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                schedule.add(studentSchedule);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getScheduleByStudent = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduleByStudent.setString(1, currentSemester);
            getScheduleByStudent.setString(2, courseCode);
            resultSet = getScheduleByStudent.executeQuery();

            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
   
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester = ? and courseCode = ? and status='s' ");
            getScheduledStudentsByCourse.setString(1, semester);
            getScheduledStudentsByCourse.setString(2, courseCode);
            resultSet = getScheduledStudentsByCourse.executeQuery();

            while(resultSet.next())
            {
                ScheduleEntry student = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                students.add(student);                    


            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester = ? and courseCode = ? and status='w' ");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, courseCode);
            resultSet = getWaitlistedStudentsByCourse.executeQuery();

            while(resultSet.next())
            {
                
                String status = resultSet.getString(4);
                ScheduleEntry student = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                System.out.println("getwaitlistedstudent:" + student.getStudentID() + ":" + student.getCourseCode() + ", " + student.getStatus());
                students.add(student);                    


            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    

    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and courseCode = ? ");
            dropStudentScheduleByCourse.setString(1,semester);
            dropStudentScheduleByCourse.setString(2,studentID);
            dropStudentScheduleByCourse.setString(3,courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
        
    public static void dropScheduleByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and courseCode = ? ");
            dropScheduleByCourse.setString(1,semester);
            dropScheduleByCourse.setString(2,courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry)
    {
        dropStudentScheduleByCourse(semester, entry.getStudentID(),entry.getCourseCode());
        entry.setStatus("s");
        addScheduleEntry(entry);
//        connection = DBConnection.getConnection();
//        try
//        {//should be similar to add scheule entry
//            
//            updateScheduleEntry = connection.prepareStatement("update app.schedule set studentid = ? , status = ? , timestamp = ?  where semester = ? and coursecode = ?");
//            updateScheduleEntry.setString(1, entry.getStudentID());
//            updateScheduleEntry.setString(2, "s");
//            updateScheduleEntry.setTimestamp(3, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
//            updateScheduleEntry.setString(4, semester);
//            updateScheduleEntry.setString(5, entry.getCourseCode());
//            
////            addScheduleEntry.setTimestamp(1, entry.getTimestamp);
//            updateScheduleEntry.executeUpdate();
//        }
//        catch(SQLException sqlException)
//        {
//            sqlException.printStackTrace();
//        }
        
        
    }
    


}