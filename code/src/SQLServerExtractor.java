import java.sql.Connection;

public class SQLServerExtractor extends DataExtractorAbstract implements Runnable {

    public SQLServerExtractor(String tableName, Connection jdbcConnection) {
        super(tableName, jdbcConnection);
    }


    @Override
    public void extractData() {
        super.extractData();

    }
    @Override
    public void run() {
    }
    
}
