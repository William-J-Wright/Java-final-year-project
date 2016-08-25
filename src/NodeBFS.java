/**
 *
 * @author Will
 *  *  Changed and modfied version of 
 * https://community.oracle.com/thread/2078343?tstart=0
 * last accessed at 05/04/2016
 * used for the BFS shortest path
 */
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class NodeBFS extends JPanel{
    public static final int NUM_ROWS = 5000;
    public static final int NUM_COLS = 5000;
    ArrayList<Point> pathBFS2 = new ArrayList();
    int y = -1,x = -1;
    private int z = -1;
    public Point parentB;
        
        public NodeBFS(Point parent){
            this.parentB = parent;
            pathBFS2.add(parent);
        }
        public NodeBFS(Node n){
            //this.path = n.path;
            for(int i = 0; i < n.path.size(); i++)
                this.pathBFS2.add(n.path.get(i));
            this.parentB = n.getLastParent();
            this.y = n.h;
            this.x = n.g;
            this.updateTotal(y, x);
        }
        //adding a point of the shortest path to the arrayList(point)
        public void addPointB(Point p){
            this.parentB = pathBFS2.get(pathBFS2.size()-1);
            pathBFS2.add(p);
        }//changing the sizes.
        public void updateTotal(int y, int x){
            this.x = x;
            this.y = y;
            this.z = this.y + this.x;
        }
//getting how long the path actually is. 
        public int getPathSize(){
            return pathBFS2.size();
        }
        //getting a set point of the map
        public Point gettingPoint(int i){
            return pathBFS2.get(i);
        }
        //getting the last point of the whole array. 
        public Point lastPoint(){
            return pathBFS2.get(pathBFS2.size()-1);
        }//finding the aprent node.
        public Point parent(){
            return parentB;
        }//seeing if the next move would be pointless in doing it. 
        public boolean isMoveRedundant(Point p){
            //Searches last 8 moves
            for(int i = pathBFS2.size()-1; i>0 && i>pathBFS2.size()-9; i--){
                if(p.equals(pathBFS2.get(i)))
                    return true;
            }
            return false;
        }
        public int getTotalCost(){
            return z;
        }
}
