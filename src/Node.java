/**
 *
 * @author Will
 *  Changed and modfied version of 
 * https://community.oracle.com/thread/2078343?tstart=0
 * last accessed at 05/04/2016
 */
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Node extends JPanel{
    public static final int NUM_ROWS = 5000;
    public static final int NUM_COLS = 5000;
    ArrayList<Point> path = new ArrayList();
        int h = -1,g = -1;
        private int f = -1;
        public Point parent;
        //here we add the point to the parent point
        public Node(Point parent){
            this.parent = parent;
            path.add(parent);
        }
        public Node(Node n){
            //this loop is getting the size of the current path and adding all the points
            for(int i = 0; i < n.path.size(); i++)
                this.path.add(n.path.get(i));
            this.parent = n.getLastParent();
            this.h = n.h;
            this.g = n.g;
            this.updateCost(h, g);
        }
        //adding a new point to the parent
        public void addPoint(Point p){
            this.parent = path.get(path.size()-1);
            path.add(p);
        }
        //changing the size 
        public void updateCost(int h, int g){
            this.g = g;
            this.h = h;
            this.f = this.h + this.g;
        }
        /**
         * @return f, total cost
         */        
        public int getCost(){
            return f;
        }
        //getting path size
        public int getPathSize(){
            return path.size();
        }//getting a certain point based on a integer
        public Point getPoint(int i){
            return path.get(i);
        }//getting the last point in the path
        public Point getLastPoint(){
            return path.get(path.size()-1);
        }//getting the current parent
        public Point getLastParent(){
            return parent;
        }//echoing the point map
        public void echoAllPoints(){
            for(int i=0; i<path.size();i++)
                System.out.print(this.getPoint(i) + "\n");
            System.out.println();
        }//testing too see if the new point would be pointless to do
        public boolean isMoveRedundant(Point p){
            //Searches last 8 moves if possible.
            for(int i = path.size()-1; i>0 && i>path.size()-9;i--){
                if(p.equals(path.get(i)))
                    return true;
            }
            return false;
        }
}
