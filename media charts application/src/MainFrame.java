import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class MainFrame extends JFrame implements ActionListener {


    JLabel lChooseEndPoint,lAddRecord,lShowChart;
    JComboBox cChooseEndPoint;
    JButton bAddRecord,bShowChart,bAddEndPoint,bSimulate,bRefreshAvailableEndPoints;
    JTextField tAddRecord;
    JCheckBox cbRecords,cbForecasts;
    JScrollPane scrollPane;
    JTable dataTable;




    public MainFrame(){
        setSize(500,300);
        setMinimumSize(new Dimension(500,300));
        setTitle("Program");
        setLayout(null);

        lChooseEndPoint = new JLabel("Wybierz punkt odbioru:");
        lChooseEndPoint.setBounds(20,20,150,20);
        lChooseEndPoint.setVisible(true);

        cChooseEndPoint= new JComboBox();
        cChooseEndPoint.setBounds(170,20,150,20);
        for(String endPointID : EndPointBase.getInstance().getEndPointMap().keySet()){
            cChooseEndPoint.addItem(endPointID);
        }
        cChooseEndPoint.addActionListener(this);

        bRefreshAvailableEndPoints = new JButton("Odśwież");
        bRefreshAvailableEndPoints.setBounds(320,20,150,20);
        bRefreshAvailableEndPoints.setVisible(true);
        bRefreshAvailableEndPoints.addActionListener(this);

        lAddRecord =new JLabel("Podaj odczyt:");
        lAddRecord.setBounds(20,40,150,20);
        lAddRecord.setVisible(true);

        tAddRecord = new JTextField();
        tAddRecord.setBounds(170,40,150,20);
        tAddRecord.setVisible(true);

        bAddRecord = new JButton("Dodaj");
        bAddRecord.setBounds(320,40,150,20);
        bAddRecord.setVisible(true);
        bAddRecord.addActionListener(this);

        lShowChart = new JLabel("Pokaż wykres:");
        lShowChart.setBounds(20,160,150,20);
        lShowChart.setVisible(true);

        cbRecords = new JCheckBox("Odczyty");
        cbRecords.setBounds(20,180,150,20);
        cbRecords.setVisible(true);

        cbForecasts = new JCheckBox("Prognozy");
        cbForecasts.setBounds(20,200,150,20);
        cbForecasts.setVisible(true);

        bShowChart = new JButton("Rysuj wykres");
        bShowChart.setBounds(170,220,150,20);
        bShowChart.setVisible(true);
        bShowChart.addActionListener(this);

        bAddEndPoint = new JButton("Dodaj Licznik");
        bAddEndPoint.setBounds(170,180,150,20);
        bAddEndPoint.setVisible(true);
        bAddEndPoint.addActionListener(this);

        bSimulate = new JButton("Symuluj wyniki");
        bSimulate.setBounds(170,200,150,20);
        bSimulate.setVisible(true);
        bSimulate.addActionListener(this);

        scrollPane = new JScrollPane();
        add(scrollPane);
        add(lChooseEndPoint);
        add(cChooseEndPoint);
        add(lAddRecord);
        add(tAddRecord);
        add(bAddRecord);
        add(lShowChart);
        add(cbRecords);
        add(cbForecasts);
        add(bShowChart);
        add(bAddEndPoint);
        add(bSimulate);
        add(bRefreshAvailableEndPoints);
    }
    public void refreshAvailableEndPoints(){
        remove(cChooseEndPoint);
        cChooseEndPoint= new JComboBox();
        cChooseEndPoint.setBounds(170,20,150,20);
        for(String endPointID : EndPointBase.getInstance().getEndPointMap().keySet()){
            cChooseEndPoint.addItem(endPointID);
        }
        cChooseEndPoint.addActionListener(this);
        add(cChooseEndPoint);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == bRefreshAvailableEndPoints){
            refreshAvailableEndPoints();
        }
        if(source == cChooseEndPoint){
            remove(scrollPane);

            String columnNames[]={"ID","Data","Zużycie"};
            dataTable = new JTable(RecordBase.getInstance().getTableData(cChooseEndPoint.getSelectedItem().toString()), columnNames);
            dataTable.setPreferredScrollableViewportSize(new Dimension());
            dataTable.setFillsViewportHeight(true);
            scrollPane = new JScrollPane(dataTable);
            scrollPane.setBounds(20,60,450,100);
            add(scrollPane);

            repaint();

        }
        if(source == bSimulate){
            EndPointBase.getInstance().getEndPointMap().keySet().forEach(item ->{
                RecordBase.getInstance().randomizeData(item);
                ForecastBase.getInstance().generateForecast(item);

            });

        }
        if(source == bShowChart){
            MultipleLinesChart frame = new MultipleLinesChart(cChooseEndPoint.getSelectedItem().toString(),cbRecords.isSelected(),cbForecasts.isSelected());
            frame.setVisible(true);
        }
        if(source == bAddEndPoint){
            AddRegisterEndPointFrame frame = new AddRegisterEndPointFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }

        if(source == bAddRecord){
            String ValueRegex ="[0-9]{1,4}";
            String input = tAddRecord.getText();
            String deviceId = cChooseEndPoint.getSelectedItem().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateWithoutTimeString = sdf.format(new Date());

            if(Pattern.compile(ValueRegex).matcher(input).matches()){
                System.out.println(RecordBase.getInstance().addRecord(deviceId,dateWithoutTimeString,Integer.parseInt(input)));
            }
        }



    }
}
