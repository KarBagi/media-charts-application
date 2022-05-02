import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EndPointBase {
    private static EndPointBase instance;

    private Map<String,EndPoint> endPointMap;

    private EndPointBase(){
         this.endPointMap = new HashMap<>();
         readFromFile();
    }

    public static EndPointBase getInstance(){
        if(instance == null){
            instance = new EndPointBase();
        }
        return instance;
    }
    public Map<String,EndPoint> getEndPointMap(){
        return endPointMap;
    }

    public void addEndPoint(String endDeviceId,String postCode, String city, String street, String buildingNumber,String flatNumber,MediaType mediaType){
        if(endPointMap.get(endDeviceId)==null) {
            endPointMap.put(endDeviceId, new EndPoint(postCode, city, street, buildingNumber, flatNumber, mediaType));
        }else{
            throw new Error("urządzenie o tym id zostało już zarejestrowane");
        }
    }





    private void readFromFile(){
        endPointMap= new HashMap<>();

        File file = new File("articleBase.txt");

        Scanner scanner = null;

        try{
            scanner = new Scanner(file);
            while(scanner.hasNext()){
                String[] temp= scanner.nextLine().split(";");
                MediaType mediaType=MediaType.GAS;
                switch (temp[6]){
                    case "GAS"->mediaType = MediaType.GAS;
                    case "ENERGY"->mediaType =  MediaType.ENERGY;
                    case "WATER"->mediaType =  MediaType.WATER;
                }
                endPointMap.put(temp[0],new EndPoint(temp[1],temp[2],temp[3],temp[4],temp[5],mediaType));
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }

    }



    public void saveToFile()  {
        PrintWriter save;
        try{
            save = new PrintWriter("articleBase.txt");
            for(Map.Entry<String,EndPoint> entry:endPointMap.entrySet()){
                save.println(entry.getKey()+";"+entry.getValue().getPostCode()+";"+entry.getValue().getCity()+";"+entry.getValue().getStreet()+";"+entry.getValue().getBuildingNumber()+";"+entry.getValue().getFlatNumber()+";"+entry.getValue().getMediaType());

            }
            save.close();
        }
        catch (FileNotFoundException e ){
            System.out.println(e);
        }


    }

}
