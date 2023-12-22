import java.sql.*;

public class SqlConnection {
    private String database;
    private String url;
    private String username;
    private String password;
    private Connection Connector=null;

    public SqlConnection(String url, String username, String password, String database) {
        this.database = database;
        this.url = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName="+this.database+";encrypt=false;trustServerCertificate=false";
        this.username = username;
        this.password = password;
    } 
    public Connection connect(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connector = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.println("success access!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Connector;
    }


    public void close(){
        try {
            Connector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
