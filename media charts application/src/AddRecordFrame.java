import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class AddRecordFrame extends JFrame implements ActionListener {

    JLabel lchooseEndPoint,laddRecord,lRecordUnit;
    JTextField taddRecord;
    JComboBox cChooseEndPoint;
    JButton bSendRecord;



    public AddRecordFrame(){
        setSize(500,500);
        setTitle("Dodawanie odczytu");
        setLayout(null);

        lchooseEndPoint = new JLabel("Wybierz punkt odbioru:");
        lchooseEndPoint.setBounds(20,20,150,20);
        lchooseEndPoint.setVisible(true);

        cChooseEndPoint= new JComboBox();
        cChooseEndPoint.setBounds(170,20,150,20);
        for(String endPointID : EndPointBase.getInstance().getEndPointMap().keySet()){
            cChooseEndPoint.addItem(endPointID);
        }
        cChooseEndPoint.setVisible(true);
        cChooseEndPoint.addActionListener(this);

        laddRecord =new JLabel("Podaj odczyt:");
        laddRecord.setBounds(20,40,150,20);
        laddRecord.setVisible(true);

        taddRecord = new JTextField();
        taddRecord.setBounds(170,40,150,20);
        taddRecord.setVisible(true);

        bSendRecord = new JButton("Dodaj");
        bSendRecord.setBounds(170,60,150,20);
        bSendRecord.setVisible(true);
        bSendRecord.addActionListener(this);

//        lRecordUnit = new JLabel();
//        lRecordUnit.setBounds(320,40,150,20);
//        lRecordUnit.setVisible(false);
//
//        add(lRecordUnit);
        add(lchooseEndPoint);
        add(cChooseEndPoint);
        add(laddRecord);
        add(taddRecord);
        add(bSendRecord);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15,"string","string");

        JFreeChart linechart = ChartFactory.createLineChart("line chart","label","Label",dataset, PlotOrientation.VERTICAL,true,true,false);

        ChartPanel chartPanel = new ChartPanel(linechart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560,376));
//        setCo

    }







    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == bSendRecord){
            String ValueRegex ="[0-9]";
            String input = taddRecord.getText();
            String deviceId = cChooseEndPoint.getSelectedItem().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateWithoutTimeString = sdf.format(new Date());

            if(Pattern.compile(ValueRegex).matcher(input).matches()){
                System.out.println(RecordBase.getInstance().addRecord(deviceId,dateWithoutTimeString,Integer.parseInt(input)));
            }
        }



    }
}
