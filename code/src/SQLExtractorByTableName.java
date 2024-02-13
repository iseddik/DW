import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLExtractorByTableName implements Runnable{
    private String tableName;
    private ArrayList<String> cln;
    private String key;
    private HashMap<String, Map<String, String>> maching;
    private HashMap<String, ArrayList<Map<String, List<String>>>> data;
    private String source;

    public SQLExtractorByTableName(String tableName, ArrayList<String> cln, String key, HashMap<String, Map<String, String>> maching) {
        this.tableName = tableName;
        this.cln = cln;
        this.key = key;
        this.maching = maching;
        this.data = new HashMap<>();
        this.source = tableName;
    }

    private void extract_sql(){
        HashMap<String, List<String>> columns = new HashMap<>();

        columns.put("SQL", new ArrayList<>());
        for (String s: cln){
            columns.get("SQL").add(s);
        }


        String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=AdventureWorksDW2016;encrypt=false;trustServerCertificate=false";
        String user = "sa";
        String pass = "JBoussouf";
        Connection conn;

        SQLServerExtractor sqlExtractor;
        ArrayList<Thread> threads = new ArrayList<>();
        int sizeSqlServer;
        ArrayList<SQLServerExtractor> sql = new ArrayList<>();


        try {
            // Data Extraction
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected!");
                sqlExtractor = new SQLServerExtractor(tableName, conn, columns, 1, 2, maching.get("SQL"), key);

                sizeSqlServer = sqlExtractor.serverSize();
                System.out.println(sizeSqlServer);

                for (int i = 0; i < sizeSqlServer; i = i + 1000) {
                    sqlExtractor = new SQLServerExtractor(tableName, conn, columns, i, 1000, maching.get("SQL"), key);

                    Thread th = new Thread(sqlExtractor);
                    sql.add(sqlExtractor);
                    threads.add(th);
                }

//                CSVFileExtractor csvExtractor = new CSVFileExtractor(
//                        "C:\\Users\\Administrateur\\Desktop\\tab1.csv", columns, maching.get("CSV"));
//                threads.add(new Thread(csvExtractor));
//
//                ExcelFileExtractor xlsExtractor = new ExcelFileExtractor(
//                        "C:\\Users\\Administrateur\\Desktop\\tab1.xls", columns, maching.get("XLS"));
//
//                threads.add(new Thread(xlsExtractor));


                for (Thread th : threads) {
                    th.start();
                }
                for (Thread th : threads) {
                    th.join();
                }

                //data chunks

//                data.put("CSV", new ArrayList<>());
//                data.get("CSV").add(csvExtractor.getStringMap());
//
//                data.put("XLS", new ArrayList<>());
//                data.get("XLS").add(xlsExtractor.getStringMap());

                this.data.put("SQL", new ArrayList<>());
                for (SQLServerExtractor s : sql) {
                    this.data.get("SQL").add(s.getStringMap());
                }


                // Data Transformation
                //uppercase transformation
//                threads.clear();
//                List<UpperCaseTransformer> upperCaseTransformers = new ArrayList<>();
//                List<String> cln_trans = new ArrayList<>();
//                cln_trans.add("FirstName");
//                for (String s : data.keySet()) {
//                    for (Map<String, List<String>> el : data.get(s)) {
//                        UpperCaseTransformer upperCaseTransformer = new UpperCaseTransformer(el, s, cln_trans);
//                        threads.add(new Thread(upperCaseTransformer));
//                        upperCaseTransformers.add(upperCaseTransformer);
//
//                    }
//                }
//
//                for (Thread th : threads) {
//                    th.start();
//                }
//                for (Thread th : threads) {
//                    th.join();
//                }
//                for (UpperCaseTransformer u:upperCaseTransformers){
//                    System.out.println(u.getSource()+" ----> "+u.getData());
//                }
//
           }
        }catch (SQLException sqlException){
            System.out.println(sqlException);} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, ArrayList<Map<String, List<String>>>> getData() {
        return data;
    }

    public String getSource() {
        return source;
    }

    @Override
    public void run() {
        extract_sql();
    }
}
