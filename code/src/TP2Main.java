import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TP2Main {

    public static void main(String[] args) {

        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<String> columns1 = new ArrayList<>();
        ArrayList<SQLExtractorByTableName> sqlExtractorByTableName = new ArrayList<>();

        columns1.add("FirstName");
        columns1.add("LastName");
        columns1.add("CustomerKey");


        //name mapping between data resources and target
        HashMap<String, Map<String, String>> maching1 = new HashMap<>();

        maching1.put("SQL", new HashMap<>());
        maching1.get("SQL").put("FirstName", "FirstName");
        maching1.get("SQL").put("LastName", "LastName");
        maching1.get("SQL").put("CustomerKey", "id");

        //Extract data from the first table
        SQLExtractorByTableName dimCustomer = new SQLExtractorByTableName("DimCustomer",columns1, "CustomerKey", maching1);
        threads.add(new Thread(dimCustomer));
        sqlExtractorByTableName.add(dimCustomer);

        ArrayList<String> columns2 = new ArrayList<>();
        columns2.add("ProductKey");
        columns2.add("OrderDateKey");
        columns2.add("CustomerKey");
        columns2.add("SalesAmount");

        //name mapping between data resources and target
        HashMap<String, Map<String, String>> maching2 = new HashMap<>();
        maching2.put("SQL", new HashMap<>());
        maching2.get("SQL").put("ProductKey", "ProductKey");
        maching2.get("SQL").put("OrderDateKey", "OrderDateKey");
        maching2.get("SQL").put("CustomerKey", "CustomerKey");
        maching2.get("SQL").put("SalesAmount", "SalesAmount");
        //Extract data from the second table
        SQLExtractorByTableName factInternetSales = new SQLExtractorByTableName("FactInternetSales",columns2, "ProductKey", maching2);
        threads.add(new Thread(factInternetSales));
        sqlExtractorByTableName.add(factInternetSales);

        for (Thread th: threads){
            th.start();
        }
        for (Thread th: threads){
            try {
                th.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
        for (SQLExtractorByTableName table:sqlExtractorByTableName){
            System.out.println(table.getSource() + " ----> "+table.getData());
        }






    }
}
