import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
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
=======
public class UpperCaseTransformer {

    
    public static void transformData(HashMap<String, ArrayList<Map<String, List<String>>>> data, String column, String source) {
        if ( data.get(source).get(0).get(column) != null ) {
            for (int i = 0; i < data.get(source).size(); i++){
                for (String ele : data.get(source).get(0).get(column)) {
                    System.out.println(ele.toUpperCase());
                }
            }
        } else {
            System.out.println("The given column doesn't exist!");
        }
>>>>>>> f5895d122454364434322f9c2d67f439c863899f
    }
}
