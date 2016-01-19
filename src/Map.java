import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
public class Map extends JPanel {

    final static JFrame frame = new JFrame("SIMULATOR");
    final static int TRIED = 2;
    final static int PATH = 3;
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
    private int[][] map;
    private int height;
    private int width;
    public Map(){
        Color gray = Color.GRAY;
        this.worldMap = new Color[NUM_ROWS][NUM_COLS];
        this.map = new int[NUM_ROWS][NUM_COLS];
        Random r = new Random();
        // Randomize the terrain
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                double rand = Math.random() * ( 1 - 0 );
                if(j > 80 & i > 40 & i < 60)
                {
                    if(j == 97 & i == 50)
                    {
                        holdj = j;
                        holdi = i;
                        this.worldMap[i][j] = Color.GREEN;
                        this.map[i][j] = 2;
                    }else{
                        this.map[i][j] = 1;
                        this.worldMap[i][j] = Color.GRAY;
                    }
                }
                else
                if(rand <0.5)
                {
                    if(j >0 & i >1 & i <99){
                    if(gray.getRGB() == worldMap[i][j-1].getRGB()){
                         int makemeGray = 1 + (int)(Math.random() * 10);
                         if(makemeGray > 3){
                         this.worldMap[i][j] = Color.GRAY;
                         this.map[i][j] = 1;
                         }else{this.worldMap[i][j] = Color.BLACK;
                         this.map[i][j] = 0;}
                    }
                    else{
                    this.worldMap[i][j] = Color.BLACK;
                    this.map[i][j] = 0;
                }}else{
                    this.worldMap[i][j] = Color.BLACK;
                    this.map[i][j] = 0;
                }}
                else {
                    this.worldMap[i][j] = Color.GRAY;
                    this.map[i][j] = 1;
                }
            }
        }
        this.height = 100;
        this.width = 100;
        int preferredWidth = NUM_COLS * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
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
                    if (this.map[i][j-1] == 3){
                    left = 0;
                    this.map[i][j-1] = 2;
                    }else if (this.map[i-1][j] == 3){
                    left = 1;
                    this.map[i-1][j] = 2;
                    }else if (this.map[i+1][j] == 3) {
                    left = 2;
                    this.map[i+1][j] = 2;
                    }
                if(left == 0)//the movement for going up
                {
                g.fillRect(x, y-10, rectWidth, rectHeight);
                holdj -=1;
                move = 0;
                }else if(left == 1 ){
                        g.fillRect(x-10, y, rectWidth, rectHeight);
                        holdi -=1;
                        move = 0;
                }else if(left ==2 ){//move right
                    g.fillRect(x+10, y, rectWidth, rectHeight);
                    holdi +=1;
                    move = 0;
                }}
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
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
                 boolean solved = map.solve();
                 System.out.println(map.toString());
                System.out.println("Solved: " + solved);
                
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
    public boolean solve() {
        return traverse(20,20);
    }

    private boolean traverse(int i, int j) {
        if (!isValid(i,j)) {
            return false;
        }

        if ( isEnd(i, j) ) {
            map[i][j] = PATH;
            return true;
        } else {
            map[i][j] = TRIED;
        }

        // North
        if (traverse(i - 1, j)) {
            map[i-1][j] = PATH;
            return true;
        }
        // East
        if (traverse(i, j + 1)) {
            map[i][j + 1] = PATH;
            return true;
        }
        // South
        if (traverse(i + 1, j)) {
            map[i + 1][j] = PATH;
            return true;
        }
        // West
        if (traverse(i, j - 1)) {
            map[i][j - 1] = PATH;
            return true;
        }

        return false;
    }

    private boolean isEnd(int i, int j) {
        return i == 97 && j == 50;
    }

    private boolean isValid(int i, int j) {
        if (inRange(i, j) && isOpen(i, j) && !isTried(i, j)) {
            return true;
        }
        return false;
    }

    private boolean isOpen(int i, int j) {
        return map[i][j] == 1;
    }

    private boolean isTried(int i, int j) {
        return map[i][j] == TRIED;
    }

    private boolean inRange(int i, int j) {
        return inHeight(i) && inWidth(j);
    }

    private boolean inHeight(int i) {
        return i >= 0 && i < height;
    }

    private boolean inWidth(int j) {
        return j >= 0 && j < width;
    }

    public String toString() {
        String s = "";
        for (int[] row : map) {
            s += Arrays.toString(row) + "\n";
        }

        return s;
    }
}