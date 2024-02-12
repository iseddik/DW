import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) {
        HashMap<String, List<String>> columns = new HashMap<>();

        columns.put("SQL", new ArrayList<>());
        columns.get("SQL").add("FirstName");
        columns.get("SQL").add("LastName");
        columns.get("SQL").add("CustomerKey");

        columns.put("CSV", new ArrayList<>());
        columns.get("CSV").add("id");
        columns.get("CSV").add("first_name");
        columns.get("CSV").add("last_name");
        
        
        columns.put("XLS", new ArrayList<>());
        columns.get("XLS").add("first_name");
        columns.get("XLS").add("last_name");
        columns.get("XLS").add("id");

        columns.put("TAR", new ArrayList<>());
        columns.get("TAR").add("FirstName");
        columns.get("TAR").add("LastName");
        columns.get("TAR").add("ID");

//        columns.put("SQL", new ArrayList<>());
//        columns.get("SQL").add("OrderDate");
//        columns.get("SQL").add("OrderDateKey");
//        columns.get("SQL").add("CustomerKey");
//        columns.get("SQL").add("SalesAmount");


        //name mapping between data resources and target
        HashMap<String, Map<String, String>> maching = new HashMap<>();
        maching.put("XLS", new HashMap<>());
        maching.get("XLS").put("first_name", "FirstName");
        maching.get("XLS").put("last_name", "LastName");
        maching.get("XLS").put("id", "id");

        maching.put("CSV", new HashMap<>());
        maching.get("CSV").put("first_name", "FirstName");
        maching.get("CSV").put("last_name", "LastName");
        maching.get("CSV").put("id", "id");

        maching.put("SQL", new HashMap<>());
        maching.get("SQL").put("FirstName", "FirstName");
        maching.get("SQL").put("LastName", "LastName");
        maching.get("SQL").put("CustomerKey", "id");

        String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=AdventureWorksDW2016;encrypt=false;trustServerCertificate=false";
        String user = "sa";
        String pass = "JBoussouf";
        Connection conn;

        SQLServerExtractor sqlExtractor;
        ArrayList<Thread> threads = new ArrayList<>();
        int sizeSqlServer;
        ArrayList<SQLServerExtractor> sql = new ArrayList<>();
        HashMap<String, ArrayList<Map<String, List<String>>>> data = new HashMap<>();

        
        
        try {
            // Data Extraction
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                sqlExtractor = new SQLServerExtractor("DimCustomer", conn, columns, 1, 2, maching.get("SQL"), "CustomerKey");

                sizeSqlServer = sqlExtractor.serverSize();
                System.out.println(sizeSqlServer);

                for (int i = 0; i < sizeSqlServer; i = i + 1000) {
                    sqlExtractor = new SQLServerExtractor("DimCustomer", conn, columns, i, 1000, maching.get("SQL"), "CustomerKey");

                    Thread th = new Thread(sqlExtractor);
                    sql.add(sqlExtractor);
                    threads.add(th);
                }

                CSVFileExtractor csvExtractor = new CSVFileExtractor(
                        "C:\\Users\\Administrateur\\Desktop\\tab1.csv", columns, maching.get("CSV"));
                threads.add(new Thread(csvExtractor));

                ExcelFileExtractor xlsExtractor = new ExcelFileExtractor(
                        "C:\\Users\\Administrateur\\Desktop\\tab1.xls", columns, maching.get("XLS"));

                threads.add(new Thread(xlsExtractor));


                for (Thread th : threads) {
                    th.start();
                }
                for (Thread th : threads) {
                    th.join();
                }

                //data chunks

                data.put("CSV", new ArrayList<>());
                data.get("CSV").add(csvExtractor.getStringMap());

                data.put("XLS", new ArrayList<>());
                data.get("XLS").add(xlsExtractor.getStringMap());

                data.put("SQL", new ArrayList<>());
                for (SQLServerExtractor s : sql) {
                    data.get("SQL").add(s.getStringMap());
                }


                // Data Transformation
                //uppercase transformation
                threads.clear();
                List<UpperCaseTransformer> upperCaseTransformers = new ArrayList<>();
                List<String> cln_trans = new ArrayList<>();
                cln_trans.add("FirstName");
                for (String s : data.keySet()) {
                    for (Map<String, List<String>> el : data.get(s)) {
                        UpperCaseTransformer upperCaseTransformer = new UpperCaseTransformer(el, s, cln_trans);
                        threads.add(new Thread(upperCaseTransformer));
                        upperCaseTransformers.add(upperCaseTransformer);

                    }
                }

                for (Thread th : threads) {
                    th.start();
                }
                for (Thread th : threads) {
                    th.join();
                }
                for (UpperCaseTransformer u:upperCaseTransformers){
                    System.out.println(u.getSource()+" ----> "+u.getData());
                }
                /* 
                // Data Loading
                for (String key: data.keySet()){
                    for(Map<String, List<String>> el: data.get(key)){
                        DataWarehouse dw = new DataWarehouse(el, columns, key, "Person");
                        threads.add(new Thread(dw));
                    }
                }
                for (Thread th: threads){
                    th.start();
                }
                for (Thread th: threads){
                    th.join();
                }
                for (UpperCaseTransformer u:upperCaseTransformers){
                    System.out.println(u.getSource()+" ----> "+u.getData());
                }


                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;

                System.out.println("Elapsed time: " + elapsedTime + " milliseconds");*/


            }
        }catch (SQLException sqlException){
            System.out.println(sqlException);} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}