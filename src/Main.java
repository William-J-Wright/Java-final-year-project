/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Will
 */
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
 import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartPanel;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.plot.PlotOrientation;
 import org.jfree.data.xy.XYSeries;
 import org.jfree.data.xy.XYSeriesCollection;
 import org.jfree.ui.ApplicationFrame;
 import org.jfree.ui.RefineryUtilities;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import java.awt.*;
import static java.awt.Color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
/**
 *
 * @author jerome
 */
public class Main extends JPanel{
    //setting every variable.
    public static long totalMemoryUsed;
    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    public static Runtime runtime2 = Runtime.getRuntime();
    public static long freeMemory = runtime2.freeMemory();
    public static int len;
    public static int notPause = 0;
    public static ArrayList<Point> pathBFSPoint = new ArrayList();
    public static int end =0;
    public static long timeTaken;
    public static long total;
    public static long startTime;
    public static long endTime;
    public static int countSteps = 0; 
    final static XYSeries series = new XYSeries("Time AStar");
    final static XYSeries series2 = new XYSeries("Time BFS");
    final static XYSeries series3 = new XYSeries("AStar memory used");
    final static XYSeries series4 = new XYSeries("BFS memory used");
    final static JFrame dataFrame = new JFrame("DATA");
    final static JFrame dataFrame2 = new JFrame("MEMORY");
    public static int wasZero=1;
    public static int moved=1;
     public static int pl0x = 0;
    public static int time = 450;
    public static JButton times;
    public static JButton pause;
     public static JButton resume;
    public static Node path;
    public static NodeBFS pathBFS;
    public static int counter;
    public static int[][] mapforBFS;
    public static int[][] map;
    public static int[][] movingMap;
    public static int[][] a;
    final static JFrame frame = new JFrame("SIMULATOR");
    
    public static Color[][] worldMap;
    public static Color[][] movingWorld;
    private static int holdX;
    public static int moveCar = 0;
    private static int holdY;
    public static int countingNode =0;
    public static int xRows = 50;
    public static int move = 0;
    public static int yRows =50;
    public static final int sizeOfSquare = 10;
    /**
     * @param args the command line arguments
     */
    public Main(){
        Main.worldMap = new Color[xRows][yRows];
        // CREATING THE COLOURS FOR THE MAP BASED ON THE RANDOM MAP
        for (int i = 0; i < xRows; i++) {
            for (int j = 0; j < yRows; j++) {
                switch (map[i][j]) {
                    case 0:
                        Main.worldMap[i][j] = Color.GRAY;
                        break;
                    case 8:
                        Main.worldMap[i][j] = Color.CYAN;
                        break;
                    case 3:
                        Main.worldMap[i][j] = Color.GREEN;
                        holdX = i;
                        holdY = j;
                        break;
                    case 4:
                        Main.worldMap[i][j] = Color.RED;
                        break;
                    case 5:
                        Main.worldMap[i][j] = Color.ORANGE;
                        break;
                    case 7:
                        Main.worldMap[i][j] = Color.YELLOW;
                        break;    
                    default:
                        Main.worldMap[i][j] = Color.BLACK;
                        break;
                }
            }
        }
        movingWorld = worldMap;
        //setting the size of the map squares based on the map size.
        int preferredWidth = xRows * sizeOfSquare;
        int preferredHeight = yRows * sizeOfSquare;
        //setting the size of the map to display based on the above.
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
    
    }
    
    @Override
    //Painting the map. Will be used to rePaint when algorithms are run.
     public void paintComponent(Graphics g) {
           super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / xRows;
        int rectHeight = getHeight() / yRows;
        if(moveCar != 1){
                    for (int i = 0; i < xRows; i++) {
                        for (int j = 0; j < yRows; j++) {
                        // Upper left corner of this terrain rect
                            int x = i * rectWidth;
                            int y = j * rectHeight;
                            Color terrainColor = worldMap[i][j];
                            g.setColor(terrainColor);
                            g.fillRect(x, y, rectWidth, rectHeight);
                        }
                        }
        }else{
            for (int i = 0; i < xRows-1; i++) {
                        for (int j = 0; j < yRows-1; j++) {
                            int rectWidth2 = getWidth() / xRows;
                            int rectHeight2 = getHeight() / yRows;
                            int x = i * rectWidth2;
                            int y = j * rectHeight2;
                            if(Main.map[i][j] ==3){
                if(move != 0){//making sure its meant to move the car.
                        
                        move = 0;
//                        Main.map[i][j] = 1;
                        Main.worldMap[i][j] = Color.GRAY;
                        countingNode+=1;
                        try {//MOVING THE CAR TO ITS NEW POINT ON THE MAP
                        Main.map[path.getPoint(countingNode).x][path.getPoint(countingNode).y] = 3;
                        moved=0;
                        Main.worldMap[path.getPoint(countingNode).x][path.getPoint(countingNode).y] = GREEN;
                        }catch (IndexOutOfBoundsException e) {
                            end = 1;
                            System.out.println("Array is out of Bounds"+e);
                        } 
                }          
                            }else{
                if (Main.map[i][j] == 5){//5 IS WHAT THE RANDOM CARS ARE
                                        g.setColor(Color.ORANGE);
                                        Random r = new Random();
                                        int Low = 1;
                                        int High = 8;
                                        int Result = r.nextInt(High-Low) + Low;
                                        try {
                                        switch (Result) {//THIS SWITCH MOVES ALL OF THE RANDOM CARS
                    case 1:
                        if(Main.map[i][j+1] == 0 || Main.map[i][j+1] == 8 || Main.map[i][j+1] == 7 && Main.map[i][j+1] != 4){wasZero=0;
                        Main.worldMap[i][j+1] = ORANGE;
                        Main.map[i][j+1] = 5;
                        }
                        break;
                    case 2:
                        if( Main.map[i+1][j] == 0  || Main.map[i+1][j] == 8 || Main.map[i+1][j] == 7 && Main.map[i+1][j] != 4){wasZero=0;
                        Main.map[i+1][j] = 5;
                        Main.worldMap[i+1][j] = ORANGE;
                        }
                        break;
                    case 3:
                        if(Main.map[i-1][j] ==0 || Main.map[i-1][j] ==8 || Main.map[i-1][j] ==7 && Main.map[i-1][j] != 4){wasZero=0;
                        
                        Main.map[i-1][j] = 5;
                        Main.worldMap[i-1][j] = ORANGE;
                        g.fillRect(x-rectWidth2, y, rectWidth, rectHeight);
                        }
                        break;
                    case 4:
                        if(Main.map[i][j-1] ==0 || Main.map[i][j+1] ==8 || Main.map[i][j+1] ==7 && Main.map[i][j-1] != 4){wasZero=0;
                        
                        Main.map[i][j-1] = 5;
                        Main.worldMap[i][j-1] = ORANGE;
                        g.fillRect(x, y-rectHeight2, rectWidth, rectHeight);
                        }
                        break;
                    case 5:
                        if(Main.map[i+1][j+1] ==0 || Main.map[i+1][j+1] ==8 || Main.map[i+1][j+1] ==7 && Main.map[i+1][j+1] != 4){wasZero=0;
                        Main.map[i+1][j+1] = 5;
                        Main.worldMap[i+1][j+1] = ORANGE;
                        }
                        break;
                    case 6:
                        if(Main.map[i-1][j+1] == 0 || Main.map[i-1][j+1] == 8 || Main.map[i-1][j+1] == 7 && Main.map[i-1][j+1] != 4){wasZero=0;
                        Main.map[i-1][j+1] = 5;
                        Main.worldMap[i-1][j+1] = ORANGE;
                        }
                        break;
                    case 7:
                        if(Main.map[i+1][j-1] == 0 || Main.map[i+1][j-1] == 8 || Main.map[i+1][j-1] == 7 && Main.map[i+1][j-1] != 4){wasZero=0;
                        
                        Main.map[i+1][j-1] = 5;
                        Main.worldMap[i+1][j-1] = ORANGE;
                        g.fillRect(x+rectWidth2, y-rectHeight2, rectWidth, rectHeight);
                        }
                        break;
                    case 8:
                        if(Main.map[i-1][j-1] ==0 || Main.map[i-1][j-1] ==8 || Main.map[i-1][j-1] ==7 && Main.map[i-1][j-1] != 4){wasZero=0;
                        
                        Main.map[i-1][j-1] = 5;
                        Main.worldMap[i-1][j-1] = ORANGE;
                        g.fillRect(x-rectWidth2, y-rectHeight2, rectWidth, rectHeight);
                        }
                        break;
                    default:
                        Main.map[i][j] = 0;
                        break;
                }}catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("Array is out of Bounds"+e);
      }
                                        if(wasZero == 0)//NEEDED TO SET CERTAIN MAP PARTS AGAIN
                                        {
                                        g.setColor(Color.GRAY);
                                        Main.map[i][j] = 0;
                                        Main.worldMap[i][j] = Color.GRAY;
                                        g.fillRect(x, y, rectWidth, rectHeight);
                                        }wasZero=1;
                                        if(moved == 0){//NEEDED TO SET CERTAIN MAP PARTS AGAIN
                                        g.setColor(Color.GRAY);
                                        Main.map[i][j] = 0;
                                        Main.worldMap[i][j] = Color.GRAY;
                                        g.fillRect(x, y, rectWidth, rectHeight);
                                        }moved=1;
                                     }
                                    Color terrainColor = worldMap[i][j];
                                    g.setColor(terrainColor);
                                    g.fillRect(x, y, rectWidth, rectHeight);
                                    movingMap = Main.map;
                               }
                            
                        }           
}
     } 
     }
     public static void main(String[] args) {
        Random rand = new Random();
        //int x = Math.abs(rand.nextInt()%2500);
       // int y = Math.abs(rand.nextInt()%1000);
        int x = xRows;
        int y = yRows;
        //testing A* search
        map = new int[x][y];
        mapforBFS = new int[x][y];
        //set everything randomly
        for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++){
                if((int)(Math.random() * 100) > 97)
                {
                    //the randomised moving cars (1/33 chance)
                    map[i][j]=5;
                }
                else if((int)(Math.random() * 4) < 3)
                {
                    //setting road
                map[i][j]=0;
                }else
                    //setting building.
                {map[i][j]=1;}
            }
        }
        
//set entrance
        
        map[30][0] = 3;
        //set exit
        map[Math.abs(rand.nextInt())%x-1][y-1] = 4;
        
       //setting search to the map.
        AStar search = new AStar(map, 95000);
        //starting timer for the first bit of data for the graph.
        startTime = System.currentTimeMillis();
        //starting the A-star search
        search.startSearch();
        //getting the best path from algorithm
        path = search.getBest();
        //ending the timer.
        endTime = System.currentTimeMillis();
        //printing out a test time. 
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        total = endTime - startTime;
        //setting a backup map to the map. 
        movingMap = map;
        
        //uncoment to print out map to terminal.
//        for(int i = 0; i < x; i++){
//            for(int j = 0; j<y; j++)
//                System.out.print(map[i][j] + " ");
//            System.out.println();
//        }
        
        
        //changing the map by adding the shortest path created by A-Star
        for(int i = 0; i < path.getPathSize(); i++){
            Point temp = path.getPoint(i);
            if(map[(int)temp.getX()][(int)temp.getY()] != 3 && map[(int)temp.getX()][(int)temp.getY()] != 4)
                map[(int)temp.getX()][(int)temp.getY()] = 8;
        }
        
        
                //turning the map around to work with this BFS
                for(int i = 0; i < xRows; i++){
                    for(int j = 0; j<yRows; j++){
                        mapforBFS[i][j] = map [j][i];
                    }
                }
                //adding the map to the bfs
                ShortestPath sp = new ShortestPath(mapforBFS); 
                       //starting search for bfs shortest path.
		ShortestPath.Position[] path2  = sp.getPathBFS();
		if (path2 != null) {                        
		} else {
			System.out.println("No path found!");
		}
                try{//converting the returned array into Point so the car can follow 
                        for(int a=0; a < path2.length; a++){
                            String test = String.valueOf(path2[a]);
                            String[] test1 = test.split(",");
                            System.out.println(Arrays.toString(test1));
                            pathBFSPoint.add(new Point( Integer.parseInt(test1[0]), Integer.parseInt(test1[1])));
                        }
                }catch(NullPointerException e) {
                            System.out.println("oh no Null points suck");
                                }
                //adding the found BFS shortest path to the map.
                for(int i = 0; i < pathBFSPoint.size(); i++){
                    Point temp = pathBFSPoint.get(i);
                    if(map[(int)temp.getX()][(int)temp.getY()] != 3 && map[(int)temp.getX()][(int)temp.getY()] != 4)
                    map[(int)temp.getX()][(int)temp.getY()] = 7;
             }
                
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
               
                JButton ba = new JButton("RUN");
                //CREATING FRAME AND SETTING UP BUTTON
                counter = path.getPathSize();
                double timeTaken = (time * path.getPathSize())/1000;
                
                times = new JButton("time: "+ timeTaken);
                resume = new JButton("Pause");
                //BUTTON TO RUN SHORTESTPATH (ASTAR)
                ba.addActionListener((ActionEvent ae) -> {
                    if(notPause == 0){
                    moveCar=1;
                    executor.scheduleAtFixedRate(helloRunnable, 0, time, TimeUnit.MILLISECONDS);
                    }
                });
                
                resume.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if(notPause==0){
                            notPause = 1;
                            resume.setText("Resume");
                            executor.shutdownNow();
                        }
                        else if(notPause==1){
                            notPause=2;
                            resume.setText("Pause");
                            executor = Executors.newScheduledThreadPool(1);
                            executor.scheduleAtFixedRate(helloRunnable, 0, time, TimeUnit.MILLISECONDS);
                        }
                        else if(notPause ==2){
                             resume.setText("Resume");
                             notPause = 1;
                             executor.shutdownNow();
                        }
                    }
                });
                ba.setSize(20,20);
                times.setSize(20,20);
                resume.setSize(20,20);
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                frame.add(main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                buttonPanel.add(ba);
                buttonPanel.add(resume);
                buttonPanel.add(times);
                
                frame.add(buttonPanel,BorderLayout.SOUTH);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
                
                //SETTING UP GRAPH 
                final XYSeriesCollection data = new XYSeriesCollection();
                final JFreeChart chart = ChartFactory.createXYLineChart(
                "Data",
                "Steps", 
                "Time Taken in miliseconds", 
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );
                data.addSeries(series);
                data.addSeries(series2);
                series.add(countSteps, (int) total);
                
                final XYSeriesCollection data2 = new XYSeriesCollection();
                final JFreeChart chart2 = ChartFactory.createXYLineChart(
                "Memory used",
                "Amount used", 
                "Memory", 
                data2,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );
                NumberAxis xAxis = new NumberAxis();
                xAxis.setTickUnit(new NumberTickUnit(2));
                XYPlot plot = (XYPlot) chart2.getPlot();
                plot.setDomainAxis(xAxis);
                
                data2.addSeries(series3);
                data2.addSeries(series4);
                //series4.add(countSteps, (int) total);
                
                long startingMem = freeMemory / 1024;
                //series3.add(timeTaken, startingMem);
                
//                startTime = System.currentTimeMillis();
//                endTime = System.currentTimeMillis();
//                total = endTime - startTime;
                
                
//setting up graphs for display
                final ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
                dataFrame.add(chartPanel);
                
                dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dataFrame.pack();
                dataFrame.setVisible(true);
                
                final ChartPanel chartPanel2 = new ChartPanel(chart2);
                chartPanel2.setPreferredSize(new java.awt.Dimension(500, 270));
                dataFrame2.add(chartPanel2);
                
                dataFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dataFrame2.pack();
                dataFrame2.setVisible(true);
            }
            
        Runnable helloRunnable = new Runnable() {
            public void run() {
                if(counter >= 0 ){
                frame.repaint();
                move = 1;
                times.setText("Time: " + (time * counter)/1000);
                counter -=1;
                
                
                startTime = System.currentTimeMillis();
                search.startSearch();
                path = search.getBest();
                endTime = System.currentTimeMillis();
                total = endTime - startTime;
                timeTaken += total;
                countSteps +=1;
                series.add(countSteps, (int) total);
                long b = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                totalMemoryUsed += b / 1024;
                series3.add(countSteps, b / 1024);
                startTime = System.currentTimeMillis();
                ShortestPath.Position[] path2  = sp.getPathBFS();
		if (path2 != null) {
		} else {
			System.out.println("No path found!");
		}
                try{//converting the returned array into Point so the car can follow 
                        for(int a=0; a < path2.length; a++){
                            String test = String.valueOf(path2[a]);
                            String[] test1 = test.split(",");
                            //System.out.println(Arrays.toString(test1));
                            pathBFSPoint.add(new Point( Integer.parseInt(test1[0]), Integer.parseInt(test1[1])));
                            
                        }
                }catch(NullPointerException e) {
                            System.out.println("oh no Null points suck");
                                }
                endTime = System.currentTimeMillis();
                total = endTime - startTime;
                series2.add(countSteps, (int) total);
                long c = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                totalMemoryUsed += b / 1024;
                series4.add(countSteps, c / 1024);

                    for(int i = 0; i < path.getPathSize(); i++){
                    Point temp = path.getPoint(i);
//            System.out.println(temp + " X: "+temp.getX() + " Y: " + temp.getY());
            //System.out.println(temp);
            
                    if(map[(int)temp.getX()][(int)temp.getY()] != 3 && map[(int)temp.getX()][(int)temp.getY()] != 4)
                        map[(int)temp.getX()][(int)temp.getY()] = 8;
                        worldMap[(int)temp.getX()][(int)temp.getY()] = CYAN;
                    }
                    
                    for(int i = 0; i < path2.length; i++){
                        String test = String.valueOf(path2[i]);
                        String[] test1 = test.split(",");
                        if(map[Integer.parseInt(test1[0])][Integer.parseInt(test1[1])] != 3 && map[Integer.parseInt(test1[0])][Integer.parseInt(test1[1])] != 4)
                            map[Integer.parseInt(test1[0])][Integer.parseInt(test1[1])] = 7;
                            worldMap[Integer.parseInt(test1[0])][Integer.parseInt(test1[1])] = YELLOW;
                    }
                
                
                
                } 
            }
        };
        
        });
        
    }
    
}
 
//         String s = "";
//        for (int[] row : map) {
//            s += Arrays.toString(row) + "\n";
//        }
//                System.out.println(s + "\n"  + "NEW");
//                
//                for (int[] row : movingMap) {
//            s += Arrays.toString(row) + "\n";
//        }
//                System.out.println(s);
//            
        
        //why is the plane backwards?
        /*for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print("("+i+","+j+")");
            System.out.println();
        }*/
        
        //print map:
//        for(int i = 0; i < x; i++){
//            for(int j = 0; j<y; j++)
//                System.out.print(map[i][j] + " ");
//            System.out.println();
//        }
        
        //test A*


        //reprent map
        /*for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }*/