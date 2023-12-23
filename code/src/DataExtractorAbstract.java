import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataExtractorAbstract implements DataExtractor {
    private String tableName;
    private Map<String, List<String>> stringMap;

    public DataExtractorAbstract(String tableName) {
        this.tableName = tableName;
        this.stringMap = new HashMap<>();
    }

    
}
