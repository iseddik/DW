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
        ArrayList<Thread> threads = new ArrayList<>();
        int sizeSqlServer;

        
        // Extract data from different sources :
        try {
            // From SQLSERVER
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                sqlExtractor = new SQLServerExtractor("DimEmployee", conn, columns, 1, 2);
                sizeSqlServer = sqlExtractor.serverSize();
                
                for(int i=0; i<sizeSqlServer; i=i+69){
                    sqlExtractor = new SQLServerExtractor("DimEmployee", conn, columns, (i == 0)?i: i+1, i+69);
                    Thread th = new Thread(sqlExtractor);
                    //th.start();
                    threads.add(th);
                }
                
                CSVFileExtractor csvExtractor = new CSVFileExtractor(
                    "C:\\Users\\etabook\\Desktop\\TP1-DW\\code\\file\\tab1.csv", columns);
                threads.add(new Thread(csvExtractor));

                ExcelFileExtractor xlsExtractor = new ExcelFileExtractor(
                    "C:\\Users\\etabook\\Desktop\\TP1-DW\\code\\file\\tab1.xls", columns);
                threads.add(new Thread(xlsExtractor));


                long startTime = System.currentTimeMillis();

                for(Thread th: threads){
                    th.start();
                }
                
                for(Thread th: threads){
                    th.join();
                }

                threads.clear();

                long endTime = System.currentTimeMillis(); // Capture end time
                long elapsedTime = endTime - startTime;

                System.out.println("Elapsed time: " + elapsedTime + " milliseconds");

                for(String clmns: columns.keySet()){

                }

                dw.loadPersonData(sqlExtractor.getStringMap(), columns, "SQL", "Person");
                dw.loadPersonData(csvExtractor.getStringMap(), columns, "CSV", "Person");
                dw.loadPersonData(xlsExtractor.getStringMap(), columns, "XLS", "Person");
            }

            // From CSV FILE
            

            // From XLS FILE
            

            

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        
    }
}