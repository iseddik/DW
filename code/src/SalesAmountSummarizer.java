import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesAmountSummarizer {
    private Map<String, List<String>> sourceData;
    private HashMap<String, ArrayList<Map<String, List<String>>>> targetData;
    private String feacher_source;
    private String feacher_target;

    public SalesAmountSummarizer(Map<String, List<String>> sourceData, HashMap<String, ArrayList<Map<String, List<String>>>> targetData, String feacher_source, String feacher_target) {
        this.sourceData = sourceData;
        this.targetData = targetData;
        this.feacher_source = feacher_source;
        this.feacher_target = feacher_target;

    }

    public void amountForEachUser(){
        System.out.println(feacher_target);
    }
}
