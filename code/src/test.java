import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test {
 
    public static void main(String[] args) {
        HashMap<String, List<String>> columns = new HashMap<>();
        columns.put("SQL", new ArrayList<>());
        columns.get("SQL").add("FirstName");
        columns.get("SQL").add("LastName");

        columns.put("CSV", new ArrayList<>());
        columns.get("CSV").add("first_name");
        columns.get("CSV").add("last_name");

        columns.put("TAR", new ArrayList<>());
        columns.get("TAR").add("FirstName");
        columns.get("TAR").add("LastName");


        String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=AdventureWorksDW2016;encrypt=false;trustServerCertificate=false";
        String user = "sa";
        String pass = "123";
        Connection conn;
        DataWarehouse dw = new DataWarehouse();
        try {
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                SQLServerExtractor sqlExtractor = new SQLServerExtractor("DimEmployee", conn);
                //System.err.println(ex.tableName);
                sqlExtractor.extractData(columns);

                dw.loadPersonData(sqlExtractor.getStringMap(), columns, "SQL","Person");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        List<String> myList = new ArrayList<>();
//        myList.add("first_name");
//        myList.add("last_name");


        CSVFileExtractor csvExtractor = new CSVFileExtractor("C:\\Users\\etabook\\Desktop\\TP1-DW\\code\\file\\tab1.csv");
        try {
            csvExtractor.extractData(columns);
            dw.loadPersonData(csvExtractor.getStringMap(), columns, "CSV","Person");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // load data


    }
}