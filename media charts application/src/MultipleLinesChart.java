import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MultipleLinesChart extends JFrame {
    public MultipleLinesChart(String deviceId,Boolean records,Boolean forecasts) {   // the constructor will contain the panel of a certain size and the close operations
        super("Zużycie "+deviceId); // calls the super class constructor

        JPanel chartPanel = createChartPanel(deviceId,records,forecasts);
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(String deviceId,Boolean records,Boolean forecasts) { // this method will create the chart panel containin the graph

        String chartTitle = EndPointBase.getInstance().getEndPointMap().get(deviceId).getMediaType().name();
        String xAxisLabel = "Czas";
        String yAxisLabel ="Y";
        switch(chartTitle){
            case "ENERGY"-> yAxisLabel ="kWh";
            case "WATER", "GAS" -> yAxisLabel="cm^3";
        }


        XYDataset dataset = createDataset(deviceId,records,forecasts);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);

        customizeChart(chart);

        // saves the chart as an image files

        return new ChartPanel(chart);
    }

    private XYDataset createDataset(String deviceID,Boolean records,Boolean forecasts) {    // this method creates the data as time seris
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Zużycie");
        XYSeries series2 = new XYSeries("Prognoza");


        ;
        int i = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if(records) {
                            for (Map.Entry<String, Record> entry : RecordBase.getInstance().getRecordMap().get(deviceID).entrySet()) {

                                try {
                                    series1.add(sdf.parse(entry.getKey()).getTime(), entry.getValue().getValue());
                                    System.out.println(sdf.parse(entry.getKey()) + " ; " + sdf.parse(entry.getKey()).getDay());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

        if(forecasts){
            for (Map.Entry<String, Record> entry : ForecastBase.getInstance().getForecastMap().get(deviceID).entrySet()) {
                try {
                    series2.add(sdf.parse(entry.getKey()).getTime(), entry.getValue().getValue());
                    System.out.println(sdf.parse(entry.getKey()) + " ; " + sdf.parse(entry.getKey()).getDay());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.DARK_GRAY);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

    }

}
