import java.sql.*;

public class SqlConnection {
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=";
    private String username = "your_username";
    private String password = "your_password";
    private Connection Connector=null;


    public SqlConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    } 


    public Connection connect(String database){
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connector = DriverManager.getConnection(this.url+database, this.username, this.password);
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
