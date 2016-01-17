import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
public class Map extends JPanel {

    final static JFrame frame = new JFrame("SIMULATOR");
    public static int holdj =0;
    public static int holdi =0;
    public static int left =0;
    public static int but = 1;
    public static int move = 1;
    public static final int NUM_ROWS = 100;
    public static final int NUM_COLS = 100;
    public Color black = Color.BLACK;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 10;
        boolean cantMoveUp = false;
        boolean cantMoveLeft = false;
        boolean cantMoveRight = false;
    // In reality you will probably want a class here to represent a map tile,
    // which will include things like dimensions, color, properties in the
    // game world.  Keeping simple just to illustrate.
    private final Color[][] worldMap;

    public Map(){
        Color gray = Color.GRAY;
        this.worldMap = new Color[NUM_ROWS][NUM_COLS];
        Random r = new Random();
        // Randomize the terrain
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                double rand = Math.random() * ( 1 - 0 );
                if(j > 95 & i > 40 & i < 60)
                {
                    if(j == 97 & i == 50)
                    {
                        holdj = j;
                        holdi = i;
                        this.worldMap[i][j] = Color.GREEN;
                    }else{
                        this.worldMap[i][j] = Color.GRAY;
                    }
                }
                else
                if(rand <0.5)
                {
                    if(j >0 & i >10 & i <90){
                    if(gray.getRGB() == worldMap[i][j-1].getRGB()){
                         int makemeGray = 1 + (int)(Math.random() * 7);
                         if(makemeGray > 3){
                         this.worldMap[i][j] = Color.GRAY;
                         }else{this.worldMap[i][j] = Color.BLACK;}
                    }
                    else{
                    this.worldMap[i][j] = Color.BLACK;
                }}else{
                    this.worldMap[i][j] = Color.BLACK;
                }}
                else {
                    this.worldMap[i][j] = Color.GRAY;
                }
            }
        }
        int preferredWidth = NUM_COLS * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        
        int counter = 0;
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        if(but == 0){
        int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                // Upper left corner of this terrain rect
                if(i==holdi & j ==holdj){
                int x = i * rectWidth;
                int y = j * rectHeight;
                this.worldMap[i][j] = Color.GRAY;
                g.fillRect(x, y, rectWidth, rectHeight);
               
                if(move != 0){//making sure its meant to move the car.
                    g.setColor(Color.GREEN);//setting the recolour to green.
                    if(worldMap[i][j-1] != black){//if the square to the north
                    //isnt black move to it.
                    left = 0;
                    }else{
                        left = 1 + (int)(Math.random() * 2);//else 
                        cantMoveUp = true;
                    }
                if(left == 0)//the movement for going up
                {
                if(worldMap[i][j-1] == black){
                    g.fillRect(x, y, rectWidth, rectHeight);
                    cantMoveUp = true;
                }else {//move up
                g.fillRect(x, y-10, rectWidth, rectHeight);
                holdj -=1;
                move = 0;
                
                counter = 0;
                }}else if(left == 1 ){
                    if(worldMap[i-1][j] == black){
                        g.fillRect(x, y, rectWidth, rectHeight);
                        }else{//move left
                        g.fillRect(x-10, y, rectWidth, rectHeight);
                        holdi -=1;
                        move = 0;
                            if(worldMap[i-1][j-1] != black){
                            cantMoveUp = false;}
                }}else if(left ==2 ){//move right
                if(worldMap[i+1][j] == black){
                    g.fillRect(x, y, rectWidth, rectHeight);
                }else {
                    g.fillRect(x+10, y, rectWidth, rectHeight);
                    holdi +=1;
                    move = 0;
                }}}
                }else if(holdi == i && holdj ==j){
                    g.setColor(Color.GREEN);
                    int x = i * rectWidth;
                    int y = j * rectHeight;
                    g.fillRect(x, y, rectWidth, rectHeight);}
                    else{
                        int x = i * rectWidth;
                        int y = j * rectHeight;
                        Color terrainColor = worldMap[i][j];
                        g.setColor(terrainColor);
                        g.fillRect(x, y, rectWidth, rectHeight);
                    }}
                }
                }else {
                    int rectWidth = getWidth() / NUM_COLS;
                    int rectHeight = getHeight() / NUM_ROWS;
                    for (int i = 0; i < NUM_ROWS; i++) {
                        for (int j = 0; j < NUM_COLS; j++) {
                        // Upper left corner of this terrain rect
                            int x = i * rectWidth;
                            int y = j * rectHeight;
                            Color terrainColor = worldMap[i][j];
                            g.setColor(terrainColor);
                            g.fillRect(x, y, rectWidth, rectHeight);
                        }
                    }
                }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Map map = new Map();
                JButton ba = new JButton("MAKE BIG");
                ba.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.scheduleAtFixedRate(helloRunnable, 0, 100, TimeUnit.MILLISECONDS);
                    }
                });
                
                frame.add(ba);
                ba.setSize(50,50);
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        Runnable helloRunnable = new Runnable() {
            public void run() {
                move = 1;
                but = 0;
                frame.repaint();
            }
        };
        });
    }
}