import java.sql.*;

public class DataExtractorAbstract implements DataExtractor {
    private String tableName;
    private Connection jdbcConnection;

    public DataExtractorAbstract(String tableName, Connection jdbcConnection) {
        this.tableName = tableName;
        this.jdbcConnection = jdbcConnection;
    }

    @Override
    public void extractData() {
        try {
            Statement statement = this.jdbcConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            System.out.println("here!");
            System.out.println(resultSet.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
