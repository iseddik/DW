import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLServerExtractor extends DataExtractorAbstract {

    private Connection jdbcConnection;

    public SQLServerExtractor(String tableName, Connection jdbcConnection) {
        super(tableName);
        this.jdbcConnection = jdbcConnection;
    }
    

    @Override
    public void extractData(List<String> columns) {
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

            /* 
            for(int i=0; i<stringMap.get(columns.get(0)).size(); i++){
                for (String el: columns){
                    System.out.print(stringMap.get(el).get(i) + "\t");
                }
                System.out.println();
            }*/
            //DataWarehouse dw = new DataWarehouse();
            //dw.loadPersonData(resultSet, columns, "Person");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
}
