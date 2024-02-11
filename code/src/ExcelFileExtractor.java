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


public class ExcelFileExtractor implements DataExtractor, Runnable {

    private String path;
    private Map<String, List<String>> stringMap;
    private HashMap<String, List<String>> feat_map;
    private Map<String, String> maching;

    public ExcelFileExtractor(String path, HashMap<String, List<String>> feat_map, Map<String, String> maching) {
        this.path = path;
        this.stringMap = new HashMap<>();
        this.feat_map = feat_map;
        this.maching = maching;
    }

    @Override
    public void extractData() throws Exception {
        List<String> columns = feat_map.get("XLS");
        Map<String, Integer> clmn_map = new HashMap<>();
        for(String el: columns){
            this.stringMap.put(maching.get(el), new ArrayList<>());
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
                    this.stringMap.get(maching.get(el)).add(cell.getContents());
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

    @Override
    public void run() {
        try {
            this.extractData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
