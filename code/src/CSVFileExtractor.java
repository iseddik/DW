
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// import java.util.concurrent.RunnableFuture;
import java.util.HashMap;

public class CSVFileExtractor implements DataExtractor, Runnable{
    private String path;
    private Map<String, List<String>> stringMap;
    private HashMap<String, List<String>> feat_map;

    public CSVFileExtractor(String path, HashMap<String, List<String>> feat_map) {
        this.path = path;
        this.stringMap = new HashMap<>();
        this.feat_map = feat_map;
    }


    @Override
    public void extractData() throws Exception {
        List<String> columns = feat_map.get("CSV");
        Map<String, Integer> clmn_map = new HashMap<>();
        for(String el: columns){
            this.stringMap.put(el, new ArrayList<>());
        }
        File file = new File(path);

        String [] record;
        CSVReader csvReader = null;
        

        try {
            csvReader = new CSVReaderBuilder(new FileReader(file))
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(',')
                            .build())
                    .build();

            
            String[] tmp = csvReader.readNext();
            // !!!
            for(String el : columns){
                for(int i=0; i<tmp.length; i++){
                    if(el.equals(tmp[i])){
                        clmn_map.put(el, i);
                    }
                } 
            }

            System.out.println(clmn_map);
            if (clmn_map.isEmpty()){
                System.out.println("parameters not exist in your csv file");
            }

           
            
            while ((record = csvReader.readNext()) != null) {
                for (String el: clmn_map.keySet()){
                    this.stringMap.get(el).add(record[clmn_map.get(el)]);
                }
            }

        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    
}
