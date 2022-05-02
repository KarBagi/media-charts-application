import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ForecastBase {
    private static ForecastBase instance;
    private Map<String, Map<String,Record>> forecastMap;

    private ForecastBase(){
        this.forecastMap = new HashMap<>();
        readFromFile();
    }

    public static ForecastBase getInstance(){
        if(instance == null){
            instance = new ForecastBase();
        }
        return instance;
    }

    public String addRecord(String deviceId,String date,int value){
        if(forecastMap.get(deviceId)==null){
            forecastMap.put(deviceId,new HashMap<>());
        }
        if (forecastMap.get(deviceId).get(date) != null) {
            return "Dla tego dnia podano już pomiar";
        } else {
            forecastMap.get(deviceId).put(date, new Record(value));
            return "Pomyślnie dodano pomiar";
        }
    }

    public void generateForecast(String deviceId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<Integer,Integer> eachDaySum = new HashMap<>();
        Integer recordSum=0;
        Map<Integer,Integer> eachDayAvg = new HashMap<>();
        Integer recordAvg=0;
        Calendar dateWithoutTime=Calendar.getInstance();


        try {
            dateWithoutTime.setTime(sdf.parse(sdf.format(new Date())));
            for(Map.Entry<String,Record> entry:RecordBase.getInstance().getRecordMap().get(deviceId).entrySet()){
                Date date = sdf.parse(entry.getKey());

                Calendar newDate = Calendar.getInstance();
                newDate.add(Calendar.HOUR,-24*28);

                if(date.getTime()>newDate.getTimeInMillis()){
                    if(eachDaySum.get(date.getDay())!=null) {
                        eachDaySum.put(date.getDay(), eachDaySum.get(date.getDay()) + entry.getValue().getValue());
                    }else{
                        eachDaySum.put(date.getDay(),entry.getValue().getValue());
                    }
                    recordSum+=entry.getValue().getValue();


                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(Map.Entry<Integer,Integer> entry:eachDaySum.entrySet()){
            eachDayAvg.put(entry.getKey(),entry.getValue()/3);
        }
        recordAvg = recordSum/28;
//        Date tempDate;
        for(int i=0; i<7;i++){

//            tempDate = new Date(dateWithoutTime.add(Calendar.HOUR,24));
            addRecord(deviceId,sdf.format(dateWithoutTime.getTime()),eachDayAvg.get(dateWithoutTime.getTime().getDay()));
            dateWithoutTime.add(Calendar.HOUR,24);
        }


    }


    public Map<String,Map<String,Record>> getForecastMap(){
        return forecastMap;
    }

    public void readFromFile(){

    }

    public void saveToFile(){


    }

}
