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

        columns.put("XLS", new ArrayList<>());
        columns.get("XLS").add("first_name");
        columns.get("XLS").add("last_name");

        columns.put("TAR", new ArrayList<>());
        columns.get("TAR").add("FirstName");
        columns.get("TAR").add("LastName");

        String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=AdventureWorksDW2016;encrypt=false;trustServerCertificate=false";
        String user = "sa";
        String pass = "123";
        Connection conn;
        DataWarehouse dw = new DataWarehouse();
        SQLServerExtractor sqlExtractor;

        // Extract data from different sources :
        try {
            // From SQLSERVER
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                sqlExtractor = new SQLServerExtractor("DimEmployee", conn);
                sqlExtractor.extractData(columns);
                dw.loadPersonData(sqlExtractor.getStringMap(), columns, "SQL", "Person");
            }

            // From CSV FILE
            CSVFileExtractor csvExtractor = new CSVFileExtractor(
                    "C:\\Users\\etabook\\Desktop\\TP1-DW\\code\\file\\tab1.csv");
            csvExtractor.extractData(columns);

            // From XLS FILE
            ExcelFileExtractor xlsExtractor = new ExcelFileExtractor(
                    "C:\\Users\\etabook\\Desktop\\TP1-DW\\code\\file\\tab1.xls");
            xlsExtractor.extractData(columns);

            dw.loadPersonData(csvExtractor.getStringMap(), columns, "CSV", "Person");
            dw.loadPersonData(xlsExtractor.getStringMap(), columns, "XLS", "Person");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}