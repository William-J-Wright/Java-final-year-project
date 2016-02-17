/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Will
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import java.awt.*;
import static java.awt.Color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
/**
 *
 * @author jerome
 */
public class Main extends JPanel{
     public static int pl0x = 0;
    public static int time = 550;
    public static JButton times;
    public static Node path;
    public static int counter;
    public static int[][] map;
    final static JFrame frame = new JFrame("SIMULATOR");
    public static Color[][] worldMap;
    private static int holdX;
    public static int moveCar = 0;
    private static int holdY;
    public static int xRows = 50;
    public static int move = 0;
    public static int yRows =50;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 10;
    /**
     * @param args the command line arguments
     */
    public Main(){
        Main.worldMap = new Color[xRows][yRows];
        // Randomize the terrain
        for (int i = 0; i < xRows; i++) {
            for (int j = 0; j < yRows; j++) {
                if(map[i][j] == 0 ){
                    Main.worldMap[i][j] = Color.GRAY;
                }else if(map[i][j] == 8 ){
                    Main.worldMap[i][j] = Color.CYAN;
                }else if(map[i][j] == 3 ){
                    Main.worldMap[i][j] = Color.GREEN;
                    holdX = i;
                    holdY = j;
                }else if(map[i][j] == 4 ){
                    Main.worldMap[i][j] = Color.RED;
                }else{ 
                    Main.worldMap[i][j] = Color.BLACK;
                }
            }
        }
//               for(int i = 0; i < xRows; i++){
//            for(int j = 0; j<yRows; j++)
//                System.out.print(map[i][j] + " ");
//            System.out.println();
//        }
        int preferredWidth = xRows * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = yRows * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    @Override
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
                            if(i==holdX && j ==holdY){
                
                g.fillRect(x, y, rectWidth, rectHeight);
                if(move != 0){//making sure its meant to move the car.
                    g.setColor(Color.GREEN);//setting the recolour to green.
                    if (Main.map[i][j+1] == 8){
                        g.fillRect(x, y+rectHeight2, rectWidth, rectHeight);
                        holdY +=1;
                        move = 0;
                        Main.map[i][j+1] = 0;
                    }else if (Main.map[i+1][j] == 8){
                        g.fillRect(x+rectWidth2, y, rectWidth, rectHeight);
                        holdX +=1;
                        move = 0;
                        Main.map[i+1][j] = 0;
                    }else if (Main.map[i-1][j] == 8 ) {
                        g.fillRect(x-rectWidth2, y, rectWidth, rectHeight);
                        holdX -=1;
                        move = 0;
                        Main.map[i-1][j] = 0;
                    }else if (Main.map[i][j+1] == 8) {
                        g.fillRect(x, y-rectHeight2, rectWidth, rectHeight);
                        holdY +=1;
                        move = 0;
                        Main.map[i][j+1] = 0;
                    }else if (Main.map[i+1][j+1] == 8) {
                        g.fillRect(x+rectWidth2, y+rectHeight2, rectWidth, rectHeight);
                        holdY +=1;
                        holdX +=1;
                        move = 0;
                        pl0x=1;
                        Main.map[i+1][j+1] = 0;
                    }else if (Main.map[i-1][j+1] == 8) {
                        g.fillRect(x-rectWidth2, y+rectHeight2, rectWidth, rectHeight);
                        holdY +=1;
                        holdX -=1;
                        move = 0;
                        Main.map[i-1][j+1] = 0;
                    }else if (Main.map[i+1][j-1] == 8) {
                        g.fillRect(x+rectWidth2, y-rectHeight2, rectWidth, rectHeight);
                        holdY -=1;
                        holdX +=1;
                        move = 0;
                        Main.map[i+1][j-1] = 0;
                    }else if (Main.map[i-1][j-1] == 8) {
                        g.fillRect(x-rectWidth2, y-rectHeight2, rectWidth, rectHeight);
                        holdY -=1;
                        holdX -=1;
                        move = 0;
                        Main.map[i-1][j-1] = 0;
                    }
                }Main.worldMap[i][j] = Color.GRAY;
                            }else if(pl0x == 1){
                                    Color terrainColor = worldMap[i][j];
                                    g.setColor(terrainColor);
                                    g.fillRect(x, y, rectWidth, rectHeight);
                                    g.setColor(Color.GREEN);
                                    g.fillRect(x+rectWidth2, y+rectHeight2, rectWidth, rectHeight);
                                    pl0x=0;
                            }else{
//                                if(Main.map[i][j] == 5){
//                                g.setColor(YELLOW);}
//                                else{
                                    Color terrainColor = worldMap[i][j];
                                    g.setColor(terrainColor);
                                    g.fillRect(x, y, rectWidth, rectHeight);
//                                }
                            }
                        }
            
        //            System.out.println(" X: "+temp.getX() + " Y: " + temp.getY());
            //System.out.println(temp);
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
        //set everything randomly
        for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++){
                if((int)(Math.random() * 4) < 3)
                {
                map[i][j]=0;
                }else
                {map[i][j]=1;}
            }
        }//set entrance
        map[30][0] = 3;
        //set exit
        map[Math.abs(rand.nextInt())%x-1][y-1] = 4;
        
        //set some immpassable spots
        map[2][2] = 1;
        map[2][3] = 1;
        map[2][4] = 1;
        map[1][2] = 1;
        
        //why is the plane backwards?
        /*for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print("("+i+","+j+")");
            System.out.println();
        }*/
        
        //print map:
        for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }
        
        //test A*
        AStar search = new AStar(map, 95000);
        search.startSearch();
        path = search.getBest();
        
        //adjust map
        
        for(int i = 0; i < path.getPathSize(); i++){
            Point temp = path.getPoint(i);
//            System.out.println(temp + " X: "+temp.getX() + " Y: " + temp.getY());
            //System.out.println(temp);
            if(map[(int)temp.getX()][(int)temp.getY()] != 3 && map[(int)temp.getX()][(int)temp.getY()] != 4)
                map[(int)temp.getX()][(int)temp.getY()] = 8;
        }
        
       
        //reprent map
        /*for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }*/
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
               
                JButton ba = new JButton("MAKE RUN");
                
                counter = path.getPathSize();
                double timeTaken = (time * path.getPathSize())/1000;
                times = new JButton("time: "+ timeTaken);
                ba.addActionListener((ActionEvent ae) -> {
                    moveCar=1;
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.scheduleAtFixedRate(helloRunnable, 0, time, TimeUnit.MILLISECONDS);
                });
                
                ba.setSize(20,20);
                times.setSize(20,20);
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                frame.add(main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                buttonPanel.add(ba);
                buttonPanel.add(times);
                frame.add(buttonPanel,BorderLayout.SOUTH);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
                 
                
            }
        Runnable helloRunnable = new Runnable() {
            public void run() {
                if(counter >= 0 ){
                frame.repaint();
                move = 1;
                times.setText("Time: " + (time * counter)/1000);
                String s = "";
                counter -=1;
                }
//        for (int[] row : map) {
//            s += Arrays.toString(row) + "\n";
//        }
//                System.out.println(s);
            }
        };
        });
    }
}