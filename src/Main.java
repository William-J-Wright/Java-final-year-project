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
import static java.awt.Color.BLUE;
import static java.awt.Color.CYAN;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
/**
 *
 * @author jerome
 */
public class Main extends JPanel{
    public static int[][] map;
    final static JFrame frame = new JFrame("SIMULATOR");
    private Color[][] worldMap;
    private int height;
    private int width;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 10;
    /**
     * @param args the command line arguments
     */
    public Main(){
        Color gray = Color.GRAY;
        this.worldMap = new Color[200][200];
        this.map = new int[200][200];
        Random r = new Random();
        // Randomize the terrain
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if(map[i][j] ==0 ){
                    this.worldMap[i][j] = Color.BLACK;
                }else{
                    this.worldMap[i][j] = Color.GRAY;
                    this.map[i][j] = 1;
                }
            }
        }
        this.height = 25;
        this.width = 25;
        int preferredWidth = 200 * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = 200 * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    @Override
     public void paintComponent(Graphics g) {
        // Important to call super class method
        paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / 200;
        int rectHeight = getHeight() / 200;
                    for (int i = 0; i < 200; i++) {
                        for (int j = 0; j < 200; j++) {
                        // Upper left corner of this terrain rect
                            int x = i * rectWidth;
                            int y = j * rectHeight;
                            Color terrainColor = worldMap[i][j];
                            if(map[i][j] == 2)
                            {
                                g.setColor(BLUE);
                            }else if(map[i][j] == 3)
                            {
                                g.setColor(CYAN);
                            }
                            else{
                            g.setColor(terrainColor);
                            }
                            g.fillRect(x, y, rectWidth, rectHeight);
                        }
                    }
                
}
    public static void main(String[] args) {
        Random rand = new Random();
        //int x = Math.abs(rand.nextInt()%2500);
       // int y = Math.abs(rand.nextInt()%1000);
        int x = 200;
        int y = 200;
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
                Main Main = new Main();
                frame.add(Main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
        
        //reprent map
        /*for(int i = 0; i < x; i++){
            for(int j = 0; j<y; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }*/
        path.echoAllPoints();
       
    }
    

}
