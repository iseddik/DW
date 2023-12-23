import java.util.HashMap;
import java.util.List;

public interface DataExtractor {
    void extractData(HashMap<String, List<String>> columns) throws Exception;
}