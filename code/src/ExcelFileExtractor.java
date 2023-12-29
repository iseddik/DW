import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelFileExtractor implements DataExtractor {

    private String path;
    private Map<String, List<String>> stringMap;

    public ExcelFileExtractor(String path) {
        this.path = path;
        this.stringMap = new HashMap<>();
    }

    @Override
    public void extractData(HashMap<String, List<String>> feat_map) throws Exception {
        List<String> columns = feat_map.get("XLS");
        Map<String, Integer> clmn_map = new HashMap<>();
        for(String el: columns){
            this.stringMap.put(el, new ArrayList<>());
        }
        File file = new File(path);

        try {
            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);

            String[] tmp = new String[sheet.getColumns()];

            for(int j = 0; j < sheet.getColumns(); j++){
                tmp[j] = sheet.getCell(j, 0).getContents();
            }

            for(String el : columns){
                for(int i=0; i<tmp.length; i++){
                    if(el.equals(tmp[i])){
                        clmn_map.put(el, i);
                    }
                }
            }
            if (clmn_map.isEmpty()){
                System.out.println("Parameters not exist in your xls file");
            }

            for (int i = 1; i < sheet.getRows(); i++) {
                for (String el: clmn_map.keySet()) {
                    Cell cell = sheet.getCell(clmn_map.get(el), i); 
                    this.stringMap.get(el).add(cell.getContents());  
                }
            }
            
            
            workbook.close();

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

        
        
    }

    public Map<String, List<String>> getStringMap() {
        return stringMap;
    }

    /* 
    public static void main(String[] args) {
        try {
            // Create a workbook instance and specify the path to your Excel file
            Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\etabook\\Desktop\\TP1-DW\\code\\file\\tab1.xls"));

            // Get the first sheet of the workbook
            Sheet sheet = workbook.getSheet(0);

            // Iterate through the rows and columns to read the data
            for (int i = 0; i < sheet.getRows(); i++) {
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell cell = sheet.getCell(j, i); // Get the cell
                    System.out.print(cell.getContents() + "\t"); // Display cell contents
                }
                System.out.println(); // Move to the next line for the new row
            }

            // Close the workbook to release resources
            workbook.close();

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    } */
}
