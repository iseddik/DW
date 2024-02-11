import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpperCaseTransformer implements Runnable{
    private Map<String, List<String>> data;
    private List<String> columns;
    private String source;
    public UpperCaseTransformer(Map<String, List<String>> data, String source, List<String> columns) {
        this.data = data;
        this.columns = columns;
        this.source = source;
    }

    private void transformData() {
        ArrayList<String> tmp = new ArrayList<>();
        int i = 0;
            for (String cl:columns) {
                for (String el : data.get(cl)) {
                    tmp.add(el.toUpperCase());
                }
                data.get(cl).clear();
                data.get(cl).addAll(tmp);
            }

    }

    public Map<String, List<String>> getData(){
        return data;
    }

    public String getSource() {
        return source;
    }
        @Override
    public void run() {
        this.transformData();
    }
}
