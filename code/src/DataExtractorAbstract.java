import java.sql.*;
import java.util.List;

public class DataExtractorAbstract implements DataExtractor {
    private String tableName;
    private Connection jdbcConnection;

    public DataExtractorAbstract(String tableName, Connection jdbcConnection) {
        this.tableName = tableName;
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
            ResultSet resultSet = statement.executeQuery("SELECT "+cls+" FROM " + tableName);
            DataWarehouse dw = new DataWarehouse();
            dw.loadPersonData(resultSet, columns, "Person");

//            while (resultSet.next()) {
//                System.out.print(resultSet.getString("FirstName")+"\t");
//                System.out.println(resultSet.getString("LastName"));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
