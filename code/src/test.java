import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class test {
 
    public static void main(String[] args) {
 
        
        String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=AdventureWorksDW2016;encrypt=false;trustServerCertificate=false";
        String user = "sa";
        String pass = "123";
        Connection conn;
        try {
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                DataExtractorAbstract ex = new DataExtractorAbstract("DimEmployee", conn);
                List<String> myList = new ArrayList<>();
                myList.add("FirstName");
                myList.add("LastName");
                ex.extractData(myList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        
    }
}