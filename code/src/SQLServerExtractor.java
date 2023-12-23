import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLServerExtractor implements DataExtractor {

    private Connection jdbcConnection;
    private String tableName;
    private Map<String, List<String>> stringMap;

    public SQLServerExtractor(String tableName, Connection jdbcConnection) {
        this.tableName = tableName;
        this.jdbcConnection = jdbcConnection;
        this.stringMap = new HashMap<>();
    }
    

    @Override
    public void extractData(HashMap<String, List<String>> feat_map) {
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
            ResultSet resultSet = statement.executeQuery("SELECT "+cls+" FROM " + this.tableName);

            for (int i=0; i<columns.size(); i++){
                this.stringMap.put(columns.get(i), new ArrayList<>());
            }

            while (resultSet.next()) {
                for (int i=0; i<columns.size(); i++){
                    this.stringMap.get(columns.get(i)).add(resultSet.getString(columns.get(i)).toString());
                }
            }

             
//            for(int i=0; i<stringMap.get(columns.get(0)).size(); i++){
//                for (String el: columns){
//                    System.out.print(stringMap.get(el).get(i) + "\t");
//                }
//                System.out.println();
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getStringMap() {
        return stringMap;
    }
}
