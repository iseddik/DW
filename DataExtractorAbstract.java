import java.sql.*;

public abstract class DataExtractorAbstract implements DataExtractor {
    private String tableName;
    private Connection jdbcConnection;

    public DataExtractorAbstract(String tableName, Connection jdbcConnection) {
        this.tableName = tableName;
        this.jdbcConnection = jdbcConnection;
    }

    @Override
    public void extractData() {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.jdbcConnection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + this.tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
