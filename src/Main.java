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
import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.CYAN;
import static java.awt.Color.GRAY;
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
    public static int[][] map;
    final static JFrame frame = new JFrame("SIMULATOR");
    public static Color[][] worldMap;
    private final int height;
    private final int width;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 10;
    /**
     * @param args the command line arguments
     */
    public Main(){
        Main.worldMap = new Color[50][50];
        Random r = new Random();
        // Randomize the terrain
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if(map[i][j] == 0 ){
                    this.worldMap[i][j] = Color.BLACK;
                }else{
                    this.worldMap[i][j] = Color.GRAY;
                    this.map[i][j] = 1;
                }
            }
        }
        this.height = 25;
        this.width = 25;
        int preferredWidth = 50 * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = 50 * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    @Override
     public void paintComponent(Graphics g) {
           super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / 50;
        int rectHeight = getHeight() / 50;
                    for (int i = 0; i < 50; i++) {
                        for (int j = 0; j < 50; j++) {
                        // Upper left corner of this terrain rect
                            int x = i * rectWidth;
                            int y = j * rectHeight;
                            Color terrainColor = worldMap[i][j];
                            double ran = Math.random() * ( 1 - 0 );
                            if(map[i][j] >= 0.5)
                            {
                                g.setColor(BLACK);
                            }else if(map[i][j] == 1)
                            {
                                g.setColor(GRAY);
                            }
                            else{
                            g.setColor(GRAY);
                            }
                            g.fillRect(x, y, rectWidth, rectHeight);
                        }
                        }
}
    public static void main(String[] args) {
        Random rand = new Random();
        //int x = Math.abs(rand.nextInt()%2500);
       // int y = Math.abs(rand.nextInt()%1000);
        int x = 50;
        int y = 50;
        System.out.println("Size: ("+x+","+y+")");
        //testing A* search
        map = new int[x][y];
        //set everything randomly
        for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++){
                map[i][j]=(Math.abs(rand.nextInt())%4==0)?1:0;
            }
        }
        System.out.println(map.length + " " + map[1].length + " Area: " +(map.length * map[1].length));
        //set entrance
        map[3][0] = 3;
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
        /*for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }*/
        
        //test A*
        AStar search = new AStar(map, 95000);
        search.startSearch();
        Node path = search.getBest();
        
        //adjust map
        for(int i = 0; i < path.getPathSize(); i++){
            Point temp = path.getPoint(i);
            //System.out.println(temp + " X: "+temp.getX() + " Y: " + temp.getY());
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
               
                JButton ba = new JButton("MAKE BIG");
                ba.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.scheduleAtFixedRate(helloRunnable, 0, 500, TimeUnit.MILLISECONDS);
                    }
                });
                
                frame.add(ba);
                ba.setSize(50,50);
                frame.add(main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
                 
                
            }
        Runnable helloRunnable = new Runnable() {
            public void run() {
                frame.repaint();
                String s = "";
        for (int[] row : map) {
            s += Arrays.toString(row) + "\n";
        }
                System.out.println(s);
            }
        };
        });
    }
}
