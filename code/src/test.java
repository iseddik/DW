import java.sql.*;

public class test {
 
    public static void main(String[] args) {
 
        
        String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=Hello;encrypt=false;trustServerCertificate=false";
        String user = "sa";
        String pass = "123";
        Connection conn;
        try {
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                DataExtractorAbstract ex = new DataExtractorAbstract("Person", conn);
                ex.extractData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        
        
    }
}