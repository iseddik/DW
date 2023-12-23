import java.sql.*;
import java.util.List;

public class DataWarehouse {
    private String dbURL = "jdbc:sqlserver://localhost\\SQLSERVER19:1433;databaseName=Hello;encrypt=false;trustServerCertificate=false";
    private String user = "sa";
    private String pass = "123";
    private Connection conn;
    public void loadPersonData(ResultSet personData, List<String> columns, String tableName){

        String cls="";
        for (int i=0; i<columns.size(); i++){
            if (i==columns.size()-1){
                cls += columns.get(i);
            }else{
                cls += columns.get(i)+",";
            }
        }


        try {
            this.conn = DriverManager.getConnection(dbURL, user, pass);

            while (personData.next()) {
                String Query="INSERT INTO "+tableName+" ("+cls+") values (";
                String values = "";
                for (int i=0; i<columns.size(); i++){
                    if (i==columns.size()-1){
                        Query += "?)";
                    }else{
                        Query += "?, ";
                    }
                }
                PreparedStatement st = conn.prepareStatement(Query);

                for (int i=0; i<columns.size(); i++){
                    st.setString(i+1, personData.getString(columns.get(i)));
                }

                //System.out.println(Query);
                int rowsInserted = st.executeUpdate();
                if (rowsInserted > 0){
                    System.out.print("user succefuly added");
                }

//                System.out.println(personData.getString("LastName"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
