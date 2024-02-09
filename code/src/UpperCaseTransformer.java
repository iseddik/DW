import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpperCaseTransformer {

    
    public static void transformData(HashMap<String, ArrayList<Map<String, List<String>>>> data, String column, String source) {
        if ( data.get(source).get(0).get(column) != null ) {
            for (int i = 0; i < data.get(source).size(); i++){
                for (String ele : data.get(source).get(0).get(column)) {
                    // Do any transform that you want on this element from the given column !
                    System.out.println(ele);
                }
            }
        } else {
            System.out.println("The given column doesn't exist!");
        }
    }
    
}
