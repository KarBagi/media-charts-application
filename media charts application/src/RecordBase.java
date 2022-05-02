import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecordBase {
    private static RecordBase instance;
    private Map<String,Map<String,Record>> recordMap;

    private RecordBase(){
        this.recordMap = new HashMap<>();
//        readFromFile();
    }

    public static RecordBase getInstance(){
        if(instance == null){
            instance = new RecordBase();
        }
        return instance;
    }

    public String addRecord(String deviceId,String date,int value){
        if(recordMap.get(deviceId)==null){
            recordMap.put(deviceId,new HashMap<>());
        }
        if (recordMap.get(deviceId).get(date) != null) {
            return "Dla tego dnia podano już pomiar";
        } else {
            recordMap.get(deviceId).put(date, new Record(value));
            return "Pomyślnie dodano pomiar";
        }
    }

    public void randomizeData(String deviceId){
        Date actualDate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar actualDateWithoutTime = Calendar.getInstance();


        try {
            actualDateWithoutTime.setTime(sdf.parse(sdf.format(actualDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int random =0;
        for(int i=0; i<28; i++){
            random= (int)(Math.random() * 40 + 10);
            System.out.println(sdf.format(actualDateWithoutTime.getTime()));
            addRecord(deviceId,sdf.format(actualDateWithoutTime.getTime()),random);
            actualDateWithoutTime.add(Calendar.HOUR,-24);

        }
    }
    public Map<String,Map<String,Record>> getRecordMap(){
        return recordMap;
    }

    public String[][] getTableData(String deviceId){
        String[][] data = new String[recordMap.get(deviceId).keySet().size()][3];
        int i=0;
        for(Map.Entry<String,Record> entry: recordMap.get(deviceId).entrySet()){
            data[i][0]=deviceId;
            data[i][1]=entry.getKey();
            data[i][2]=Integer.toString(entry.getValue().getValue());
            i++;
        }
        return data;
    }


}
