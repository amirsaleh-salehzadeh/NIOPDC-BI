package rex.graphics.charts;

import javax.swing.JPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import java.awt.BorderLayout;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.category.BarRenderer;
import java.awt.GradientPaint;
import rex.metadata.ExecuteResult;
import rex.metadata.CubeSlicer;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.ui.RectangleAnchor;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.axis.CategoryAxis;
import javax.swing.ImageIcon;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.util.Rotation;
import rex.utils.S;
import javax.swing.BoxLayout;
import javax.swing.Box;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendGraphic;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.resources.*;
import java.awt.Font;
import rex.utils.AppColors;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class Chart extends JPanel{
   private CubeSlicer cubeSlicer;
   private JFreeChart chart;
   private int chartType;
   private ExecuteResultToCategoryDatasetBridge bridge;
   public static final int
        BAR_VERTICAL = 0
      , BAR_HORIZONTAL = 1
      , BAR_3D_VERTICAL = 2
      , BAR_3D_HORIZONTAL = 3
      , PIE_3D = 4
      , COMBINED = 5;

   private final static ImageIcon[] icons = new ImageIcon[]{
        S.getAppIcon("chart_bar_vertical.gif")
      , S.getAppIcon("chart_bar_horizontal.gif")
      , S.getAppIcon("chart_bar_3d_vertical.gif")
      , S.getAppIcon("chart_bar_3d_horizontal.gif")
      , S.getAppIcon("chart_pie_3d.gif")
      , S.getAppIcon("chart_combined.gif")
   };


   public Chart(   ExecuteResult _executeResult
                 , CubeSlicer    _cubeSlicer
                 , int           _chartType) {

      chartType = _chartType;
      cubeSlicer = _cubeSlicer;
      this.setLayout(new BorderLayout());
      this.add(createPanel(_executeResult, _cubeSlicer, _chartType), BorderLayout.CENTER);
   }

   public ImageIcon getIcon(){
      return icons[chartType];
   }
   public static ImageIcon getIconForType(int chartType){
      return icons[chartType];
   }
   public int getChartType(){
      return  chartType;
   }

   public void setNewChartData(ExecuteResult _executeResult, CubeSlicer _cubeSlicer){
      this.removeAll();
      this.add(createPanel(_executeResult, _cubeSlicer, chartType), BorderLayout.CENTER);
   }

   /**
    * Creates a sample chart.
    *
    * @param dataset  the dataset.
    *
    * @return The chart.
    *
    *
    */
   private JPanel createPanel(  ExecuteResult _executeResult
                              , CubeSlicer _cubeSlicer
                              , int _chartType) {
      ChartPanel chartPanel;
      JPanel rowPanel;
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//      panel.add(Box.createVerticalGlue());

      switch(chartType){
         case BAR_VERTICAL:
         case BAR_HORIZONTAL:
         case BAR_3D_VERTICAL:
         case BAR_3D_HORIZONTAL:
            bridge = new ExecuteResultToCategoryDatasetBridge(_executeResult, _cubeSlicer);
            chart = createCategoryChart(bridge, chartType);
            chartPanel = new ChartPanel(chart);
            chartPanel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 700));
            panel.add(chartPanel);
            break;
         case PIE_3D:
            bridge = new ExecuteResultToCategoryDatasetBridge(_executeResult, _cubeSlicer);

            JTabbedPane jtp = new JTabbedPane(JTabbedPane.RIGHT);
            for(int col=0; col < bridge.getColumnCount(); col++){
               chart = createPieChart(bridge, chartType, col, (String) bridge.getColumnKey(col));
               rowPanel = new JPanel();
               rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
               rowPanel.add(Box.createHorizontalGlue());
               chartPanel = new ChartPanel(chart);
               chartPanel.setPreferredSize(new java.awt.Dimension(300, 270));
               rowPanel.add(chartPanel);
               rowPanel.add(Box.createHorizontalGlue(), BorderLayout.EAST);
               rowPanel.setBorder(AppColors.CELL_DEFAULT_BORDER);
               rowPanel.setBackground(chartPanel.getBackground());
               chartPanel.setOpaque(false);
               jtp.addTab((String) bridge.getColumnKey(col), rowPanel);
            }
            panel.add(jtp);
            break;
         case COMBINED:
            bridge = new ExecuteResultToCategoryDatasetBridge(_executeResult, _cubeSlicer);
            chart  = createCombinedChart(bridge, chartType);
            rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.add(Box.createHorizontalGlue());
            chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new java.awt.Dimension(400, 500));
            chartPanel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 700));
            rowPanel.add(chartPanel);
            rowPanel.add(Box.createHorizontalGlue(), BorderLayout.EAST);
            rowPanel.setBorder(AppColors.CELL_DEFAULT_BORDER);
            rowPanel.setBackground(chartPanel.getBackground());
            panel.add(rowPanel);
            break;
      }
      panel.add(Box.createVerticalGlue());
      return panel;
   }
   private JFreeChart createCategoryChart(CategoryDataset dataset, int chartType) {
      JFreeChart chart = null;
      CategoryPlot plot;
       // create the chart...
       switch(chartType){
          case BAR_VERTICAL:
             chart = ChartFactory.createBarChart(
                bridge.getVerticalChartCaption() + " vs. " + bridge.getHorizontalChartCaption(), // chart title
                bridge.getHorizontalChartCaption(), // domain axis label
                bridge.getVerticalChartCaption(), // range axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips?
                false // URLs?
                );
             break;
          case BAR_HORIZONTAL:
             chart = ChartFactory.createBarChart(
                bridge.getVerticalChartCaption() + " vs. " + bridge.getHorizontalChartCaption(), // chart title
                bridge.getVerticalChartCaption(), // domain axis label
                bridge.getHorizontalChartCaption(), // range axis label
                dataset, // data
                PlotOrientation.HORIZONTAL,
                true, // include legend
                true, // tooltips?
                false // URLs?
                );
             break;
          case BAR_3D_VERTICAL:
             chart = ChartFactory.createBarChart3D(
               bridge.getVerticalChartCaption() + " vs. " + bridge.getHorizontalChartCaption(), // chart title
               bridge.getHorizontalChartCaption(), // domain axis label
               bridge.getVerticalChartCaption(), // range axis label
               dataset, // data
               PlotOrientation.VERTICAL, // orientation
               true, // include legend
               true, // tooltips
               false // urls
               );
             plot = chart.getCategoryPlot();
             CategoryAxis axis = plot.getDomainAxis();
             CategoryLabelPosition position = new CategoryLabelPosition(
                                                   RectangleAnchor.TOP
                                                 , TextBlockAnchor.TOP_RIGHT
                                                 , TextAnchor.TOP_RIGHT, -Math.PI / 8.0
                                                 );
             axis.setBottomCategoryLabelPosition(position);

             break;
          case BAR_3D_HORIZONTAL:
             chart = ChartFactory.createBarChart3D(
               bridge.getVerticalChartCaption() + " vs. " + bridge.getHorizontalChartCaption(), // chart title
               bridge.getVerticalChartCaption(), // domain axis label
               bridge.getHorizontalChartCaption(), // range axis label
               dataset, // data
               PlotOrientation.HORIZONTAL, // orientation
               true, // include legend
               true, // tooltips
               false // urls
               );
             break;

       }


       // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

       // set the background color for the chart...
       chart.setBackgroundPaint(new Color(0xBBBBDD));

       // get a reference to the plot for further customisation...
       plot = chart.getCategoryPlot();

       // set the range axis to display integers only...
       NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

       // disable bar outlines...
       BarRenderer renderer = (BarRenderer) plot.getRenderer();
       renderer.setDrawBarOutline(false);

       // set up gradient paints for series...
       GradientPaint gp0 = new GradientPaint(
           0.0f, 0.0f, Color.blue,
           0.0f, 0.0f, Color.BLACK
       );
       GradientPaint gp1 = new GradientPaint(
           0.0f, 0.0f, Color.green,
           0.0f, 0.0f, Color.lightGray
       );
       GradientPaint gp2 = new GradientPaint(
           0.0f, 0.0f, Color.red,
           0.0f, 0.0f, Color.lightGray
       );
       renderer.setSeriesPaint(0, gp0);
       renderer.setSeriesPaint(1, gp1);
       renderer.setSeriesPaint(2, gp2);

       // OPTIONAL CUSTOMISATION COMPLETED.

       return chart;

   }
   private JFreeChart createPieChart(CategoryDataset dataset, int chartType, int col, String title) {
      JFreeChart chart = null;
       switch(chartType){
          case PIE_3D:
             chart = ChartFactory.createPie3DChart(
                title,
                new CategoryToPieDataset(dataset, CategoryToPieDataset.COLUMN, col),
                true, // include legend
                true, // tooltips
                false // urls
                );

             break;

       }
       // set the background color for the chart...
       chart.setBackgroundPaint(Color.yellow);
       Pie3DPlot plot = (Pie3DPlot) chart.getPlot();
       plot.setStartAngle(270);
       plot.setDirection(Rotation.CLOCKWISE);
       plot.setForegroundAlpha(0.5f);
       plot.setNoDataMessage("No data to display");

       return chart;

   }

   private JFreeChart createCombinedChart(CategoryDataset dataset, int chartType) {

//       CategoryDataset dataset1 = createDataset1();
       NumberAxis rangeAxis1 = new NumberAxis("Value");
       rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
       renderer1.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
       CategoryPlot subplot1 = new CategoryPlot(dataset, null, rangeAxis1, renderer1);
       subplot1.setDomainGridlinesVisible(true);

//       CategoryDataset dataset2 = createDataset2();
       NumberAxis rangeAxis2 = new NumberAxis("Value");
       rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       BarRenderer renderer2 = new BarRenderer();
       renderer2.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
       CategoryPlot subplot2 = new CategoryPlot(dataset, null, rangeAxis2, renderer2);
       subplot2.setDomainGridlinesVisible(true);

       CategoryAxis domainAxis = new CategoryAxis("Category");
       CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
       plot.add(subplot1, 2);
       plot.add(subplot2, 1);

       JFreeChart result = new JFreeChart(
           "Combined Domain Category Plot Demo",
           new Font("SansSerif", Font.BOLD, 12),
           plot,
           true
       );
       result.getLegend().setAnchor(Legend.SOUTH);
       return result;

   }



}
