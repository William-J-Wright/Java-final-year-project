/**
 *
 * @author Will
 * Changed and modfied version of 
 * https://community.oracle.com/thread/2078343?tstart=0
 * last accessed at 05/04/2016
 */

import java.awt.Point;
import java.util.ArrayList;
public class AStar {
    ArrayList<Node> currentNode = new ArrayList();
  
    private Node bestNode;
    
    /**
     * Map, 0 is passable, 1 is not
     * 3 is entrance, 4 is exit
     */
    
    int[][] map;
    //the max counter will stop the program after this amount
    int maxCounter = 25000;
    //setting the start and end points.
    private Point start, end;
    //getting the map sent from main program.
    public AStar(int[][] map) {
        this.map = map;
    }//taking in the map and the maxCounter and setting them
    public AStar(int[][] map, int maxCounter) {
        this.map = map;
        this.maxCounter = maxCounter;
    }//this will set the map and start/end points for the program
    public AStar(int[][] map, Point start, Point end) {
        this.map = map;
        this.start = start;
        this.end = end;
    }//similar to the last but with maxCounter too
    public AStar(int[][] map, int maxCounter, Point start, Point end) {
        this.map = map;
        this.maxCounter = maxCounter;
        this.start = start;
        this.end = end;
    }
    //starting the search, must find the start of the map and end first./
    public boolean startSearch(){
        if(this.start == null || this.end == null) {
            start = startPoint();
            end = endPoint();
        }
        boolean nodePath = false;
        bestNode = new Node(start);
        System.out.println(bestNode.path.get(0));
        do{
            currentNode = newNode(bestNode);
            bestNode = findBestNode();
            nodePath = isFound(bestNode);
            if(bestNode.getCost() > maxCounter)
                return false;
        }while(!nodePath);
        return true;
    }
    //seaches the map for the end point
    private Point endPoint() {
   

for(int i = 0; i < map.length; i++) {

for(int j = 0; j< map[1].length; j++){

if(map[i][j]==4)

return new Point(i,j);

}

}

throw new RuntimeException("Ending Point could not be nodePath! Invalid Map.");

} //seaches the map for the start point
private Point startPoint(){        

String s = "";
for(int i = 0; i < map.length - 1; i++) {

for(int j = 0; j< map[1].length - 1; j++){

if(map[i][j]==3)

return new Point(i,j);

}

}
throw new RuntimeException("Starting Point could not be nodePath! Invalid Map.");

}

private boolean isFound(Node bestNode) {

if(bestNode.getLastPoint().equals(end)){

return true;

}
else{
    return false;
}
}
   
private ArrayList<Node> newNode(Node bestNode) {

    ArrayList<Node> nodePath = new ArrayList();
    int x;
    int y;
    x = (int)bestNode.getLastPoint().getX();
    y = (int)bestNode.getLastPoint().getY();

try {
//there are multiple if statements, they are checking each point of the map too see if there isnt a building
//and isnt a moving car in the postion it wants to move, if there is it will move onto the next if.
//the next 4 if statments are checking the squares that touch the car.
if (map[x + 1][y] != 1 &&map[x + 1][y] != 5&& !bestNode.isMoveRedundant(new Point(x+1,y))) {
    Node temp = new Node(bestNode);
    temp.addPoint(new Point(x + 1, y));
    calcCost(temp, false);
    nodePath.add(temp);
}
} catch (IndexOutOfBoundsException ex) {
}

try {

if (map[x - 1][y] != 1 &&map[x - 1][y] != 5&& !bestNode.isMoveRedundant(new Point(x-1,y))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x - 1, y));
calcCost(temp, false);
nodePath.add(temp);
}

} catch (IndexOutOfBoundsException ex) {
}

try {

if (map[x][y + 1] != 1 &&map[x][y+1] != 5&& !bestNode.isMoveRedundant(new Point(x,y+1))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x, y + 1));
calcCost(temp, false);
nodePath.add(temp);

}

} catch (IndexOutOfBoundsException ex) {
}

try {

if (map[x][y - 1] != 1 &&map[x][y-1] != 5&& !bestNode.isMoveRedundant(new Point(x,y-1))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x, y - 1));
calcCost(temp, false);
nodePath.add(temp);

}

} catch (IndexOutOfBoundsException ex) {
}

try {
//these next four if statements are doing the diagonal moves of the car. 
//I've chosen to include diagonal moves as it makes the car move more natually
if (map[x + 1][y + 1] != 1 &&map[x + 1][y+1] != 5&& !bestNode.isMoveRedundant(new Point(x+1,y+1))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x + 1, y + 1));
calcCost(temp, true);
nodePath.add(temp);

}

} catch (IndexOutOfBoundsException ex) {
}

try {

if (map[x + 1][y - 1] != 1 &&map[x + 1][y-1] != 5&& !bestNode.isMoveRedundant(new Point(x+1,y-1))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x + 1, y - 1));
calcCost(temp, true);
nodePath.add(temp);

}

} catch (IndexOutOfBoundsException ex) {
}

try {
if (map[x - 1][y - 1] != 1 &&map[x - 1][y-1] != 5&& !bestNode.isMoveRedundant(new Point(x-1,y-1))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x - 1, y - 1));
calcCost(temp, true);
nodePath.add(temp);

}
} catch (IndexOutOfBoundsException ex) {
}

try {

if (map[x - 1][y + 1] != 1 &&map[x - 1][y+1] != 5&& !bestNode.isMoveRedundant(new Point(x-1,y+1))) {

Node temp = new Node(bestNode);
temp.addPoint(new Point(x - 1, y + 1));
calcCost(temp, true);
nodePath.add(temp);

}
} catch (IndexOutOfBoundsException ex) {
}

return nodePath;

}

//finished the testing of the possible moves of the car.

//getting the cost of the car (the amount of moves)

private void calcCost(Node temp, boolean diagonal) {

Point last = temp.getLastPoint();
int x = (int)last.getX();
int y = (int)last.getY();
int goalX = (int)end.getX();
int goalY = (int)end.getY();
int h = 0, g = temp.g;
g+=10;
if(x <= goalX)
for(int i = x; i < goalX; i++)
h+=10;
else
for(int i = x; i > goalX; i--)
h+=10;
if(y <= goalY)
for(int i = y; i < goalY; i++)
h+=10;
else
for(int i = y; i > goalY; i--)
h+=10;
//now, update cost
temp.updateCost(h, g);
}


//gets the best position that the car can move too.
private Node findBestNode() {
int index = -1;
int x = Integer.MAX_VALUE;
if (currentNode.size() == 0) {
Point p = bestNode.getLastPoint();
int xBlocked = (int)p.getX(), yBlocked = (int)p.getY();

//getting the map size so it doesnt check outside the map.
for(int i = xBlocked-3; i < xBlocked+3 && i < map.length;i++){
for(int j = yBlocked-3; j< yBlocked+3 && j < map[1].length;j++){
if(j==yBlocked && i==xBlocked)

System.out.print("blocked....");

else
System.out.print(map[i][j]);
}
System.out.println();
}
throw new RuntimeException("Empty Node list, blocked? Why how?");


}


for(int i=0;i<currentNode.size(); i++) {

int f = currentNode.get(i).getCost();


if(x >= f){
x = f;
index = i;
}
}
return currentNode.get(index);
}

//if the best path hasn't been found, start the serach!
public Node getBest(){
if(bestNode == null)
startSearch();
return bestNode;
}

}

