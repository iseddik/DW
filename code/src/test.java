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
                SQLServerExtractor sqlExtractor = new SQLServerExtractor("DimEmployee", conn);
                //System.err.println(ex.tableName);
                List<String> myList = new ArrayList<>();
                myList.add("FirstName");
                myList.add("LastName");
                sqlExtractor.extractData(myList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        
    }
}