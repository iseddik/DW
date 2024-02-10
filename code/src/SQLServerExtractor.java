import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLServerExtractor implements DataExtractor, Runnable{

    private Connection jdbcConnection;
    private String tableName;
    private Map<String, List<String>> stringMap;
    private HashMap<String, List<String>> feat_map;
    private int start;
    private int end;

    public SQLServerExtractor(String tableName, Connection jdbcConnection, HashMap<String, List<String>> feat_map, int start, int end) {
        this.tableName = tableName;
        this.jdbcConnection = jdbcConnection;
        this.stringMap = new HashMap<>();
        this.feat_map = feat_map;
        this.start = start;
        this.end = end;
    }
    

    public int serverSize(){
        Statement statement;
        try {
            statement = this.jdbcConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(*) from " + this.tableName);
            resultSet.next();
            int count = resultSet.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
        
    }

    @Override
    public void extractData() {
        List<String> columns = feat_map.get("SQL");
        String cls = "";

        for (int i=0; i<columns.size(); i++){
            if (i==columns.size()-1){
                cls += columns.get(i);
            }else{
                cls += columns.get(i)+",";
            }
        }

        try {
            Statement statement = this.jdbcConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT "+cls+" FROM " + this.tableName + " Order by CustomerKey " + "OFFSET " + this.start + " ROWS FETCH NEXT " + this.end + " ROWS ONLY");

            for (int i=0; i<columns.size(); i++){
                this.stringMap.put(columns.get(i), new ArrayList<>());
            }

            while (resultSet.next()) {
                for (int i=0; i<columns.size(); i++){
                    this.stringMap.get(columns.get(i)).add(resultSet.getString(columns.get(i)).toString());
                }
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getStringMap() {
        return stringMap;
    }


    @Override
    public void run() {
        this.extractData();
    }
}
