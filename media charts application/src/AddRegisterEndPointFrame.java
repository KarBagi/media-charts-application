

import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRegisterEndPointFrame extends JFrame implements ActionListener {

    JTextField tPostCode,tCity,tStreet,tBuildingNumber,tFlatNumber,tSerialNumber;
    JLabel lPostCode,lCity,lStreet,lBuildingNumber,lFlatNumber,lSerialNumber,lType,lPostCodeError,lBuildingNumberError,lFlatNumberError,lSerialNumberError,lCityError,lStreetError;
    JComboBox cChoseType;
    JButton bRegister;


    public AddRegisterEndPointFrame(){
        setSize(500,500);
        setTitle("Rejestrowanie nowego punktu odbioru");
        setLayout(null);

        tPostCode = new JTextField();
        tPostCode.setBounds(170,20,150,20);
        tPostCode.setVisible(true);

        tCity = new JTextField();
        tCity.setBounds(170,40,150,20);
        tCity.setVisible(true);

        tStreet = new JTextField();
        tStreet.setBounds(170,60,150,20);
        tStreet.setVisible(true);

        tBuildingNumber = new JTextField();
        tBuildingNumber.setBounds(170,80,150,20);
        tBuildingNumber.setVisible(true);

        tFlatNumber = new JTextField();
        tFlatNumber.setBounds(170,100,150,20);
        tFlatNumber.setVisible(true);

        tSerialNumber = new JTextField();
        tSerialNumber.setBounds(170,120,150,20);
        tSerialNumber.setVisible(true);

        cChoseType = new JComboBox();
        cChoseType.setBounds(170,140,150,20);
        for(MediaType mediaType: MediaType.values()){
            cChoseType.addItem(mediaType.name());
        }
        cChoseType.setVisible(true);


        lPostCode = new JLabel("Kod pocztowy");
        lPostCode.setBounds(20,20,150,20);
        lPostCode.setVisible(true);

        lCity = new JLabel("Miasto");
        lCity.setBounds(20,40,150,20);
        lCity.setVisible(true);

        lStreet = new JLabel("Ulica");
        lStreet.setBounds(20,60,150,20);
        lStreet.setVisible(true);

        lBuildingNumber = new JLabel("Nr budynku");
        lBuildingNumber.setBounds(20,80,150,20);
        lBuildingNumber.setVisible(true);

        lFlatNumber = new JLabel("Nr Mieszkania");
        lFlatNumber.setBounds(20,100,150,20);
        lFlatNumber.setVisible(true);

        lPostCodeError = new JLabel("niepoprawny ciąg");
        lPostCodeError.setBounds(320,20,150,20);
        lPostCodeError.setVisible(false);

        lCityError = new JLabel("niepoprawny ciąg");
        lCityError.setBounds(320,40,150,20);
        lCityError.setVisible(false);

        lStreetError = new JLabel("niepoprawny ciąg");
        lStreetError.setBounds(320,60,150,20);
        lStreetError.setVisible(false);

        lBuildingNumberError= new JLabel("niepoprawny ciąg");
        lBuildingNumberError.setBounds(320,80,150,20);
        lBuildingNumberError.setVisible(false);

        lFlatNumberError = new JLabel("niepoprawny ciąg");
        lFlatNumberError.setBounds(320,100,150,20);
        lFlatNumberError.setVisible(false);

        lSerialNumberError = new JLabel("niepoprawny ciąg");
        lSerialNumberError.setBounds(320,120,150,20);
        lSerialNumberError.setVisible(false);

        lSerialNumber = new JLabel("Seria urządzenia");
        lSerialNumber.setBounds(20,120,150,20);
        lSerialNumber.setVisible(true);

        lType = new JLabel("Typ medium");
        lType.setBounds(20,140,150,20);
        lType.setVisible(true);

        bRegister = new JButton("Zarejestruj");
        bRegister.setBounds(20,160,300,20);
        bRegister.addActionListener(this);
        bRegister.setVisible(true);





        add(tPostCode);
        add(tCity);
        add(tStreet);
        add(tBuildingNumber);
        add(tFlatNumber);
        add(tSerialNumber);

        add(lPostCode);
        add(lCity);
        add(lStreet);
        add(lBuildingNumber);
        add(lFlatNumber);
        add(lSerialNumber);
        add(lType);

        add(bRegister);

        add(lPostCodeError);
        add(lCityError);
        add(lStreetError);
        add(lSerialNumberError);
        add(lBuildingNumberError);
        add(lFlatNumberError);
        add(cChoseType);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String PostCodeRegex = "[0-9]{2}-[0-9]{3}";
        String SerialNumberRegex = "[A-Z]{2}[0-9]{5}[A-Z]{3}";
        String NumberRegex ="[A-Z]{0,1}[0-9]{1,4}[A-Z]{0,2}";

        if(source == bRegister){
            Boolean validationPassed= true;

            String serialNumber=tSerialNumber.getText()
                    ,postCode=tPostCode.getText()
                    ,city=tCity.getText()
                    ,street=tStreet.getText()
                    ,flatNumber=tFlatNumber.getText()
                    ,buildingNumber=tBuildingNumber.getText();


            MediaType madiaType =MediaType.GAS;
            switch(cChoseType.getSelectedItem().toString()){
                case "WATER"-> madiaType = MediaType.WATER;
                case "GAS"-> madiaType = MediaType.GAS;
                case "ENERGY"-> madiaType = MediaType.ENERGY;

            }

            if(!Pattern.compile(PostCodeRegex).matcher(postCode).matches()){
                lPostCodeError.setVisible(true);
                validationPassed=false;
            }else{
                lPostCodeError.setVisible(false);
            }

            if(!Pattern.compile(SerialNumberRegex).matcher(serialNumber).matches()){
                lSerialNumberError.setVisible(true);
                validationPassed=false;
            }else{
                lSerialNumberError.setVisible(false);
            }

            if(!Pattern.compile(NumberRegex).matcher(flatNumber).matches()){
                lFlatNumberError.setVisible(true);
                validationPassed=false;
            }else{
                lFlatNumberError.setVisible(false);
            }

            if(!Pattern.compile(NumberRegex).matcher(buildingNumber).matches()){
                lBuildingNumberError.setVisible(true);
                validationPassed=false;
            }else{
                lBuildingNumberError.setVisible(false);
            }

            if(city.length()<3){
                lCityError.setVisible(true);
                validationPassed=false;
            }else{
                lCityError.setVisible(false);
            }

            if(street.length()<3){
                lStreetError.setVisible(true);
                validationPassed=false;
            }else{
                lStreetError.setVisible(false);
            }

            if(validationPassed){
                EndPointBase.getInstance().addEndPoint(serialNumber,postCode,city,street,buildingNumber,flatNumber,madiaType);
                EndPointBase.getInstance().saveToFile();
                dispose();
            }

        }

    }
}
