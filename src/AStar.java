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
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import static java.awt.Color.BLUE;
import static java.awt.Color.CYAN;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
public class AStar {
    
    /**
     * This arraylist holds the 'current' nodes where current
     * is all the nodes around the specified point, excluding
     * points with the parent node == target node
     */
    ArrayList<Node> currentNodes = new ArrayList();
    
    /**
     * This holds the best node that has been found so far. If null,
     * search has not been started.
     */
    private Node currentBest;
    
    /**
     * Map, 0 is passable, 1 is not
     * 3 is entrance, 4 is exit
     */
    int[][] map;
    
    /**
     * Point where AStar seach will give up.
     */
    int maxOut = 5000;
    
    /**
     * Points representing the starting and ending position
     * of this search.
     */
    private Point start, end;
    
    public AStar(int[][] map) {
        this.map = map;
    }
    public AStar(int[][] map, int maxOut) {
        this.map = map;
        this.maxOut = maxOut;
    }
    public AStar(int[][] map, Point start, Point end) {
        this.map = map;
        this.start = start;
        this.end = end;
    }
    public AStar(int[][] map, int maxOut, Point start, Point end) {
        this.map = map;
        this.maxOut = maxOut;
        this.start = start;
        this.end = end;
    }
    
    public boolean startSearch(){
        if(this.start == null || this.end == null) {
            start = findStart();
            end = findEnd();
        }
        System.out.println("Start:" + start + "\nEnd: " + end);
        boolean found = false;
        currentBest = new Node(start);
        System.out.println(currentBest.path.get(0));
        do{
            currentNodes = findNewNodes(currentBest);
            currentBest = findBestNode();
            found = isFound(currentBest);
            if(currentBest.getCost() > maxOut)
                return false;
        }while(!found);
        System.out.println("FOUND!");
        return true;
    }

    /**
     * Search edges first
     * @return point where the end/goal is found
     */
    private Point findEnd() {
        //searching sides
        /*for(int i = 0; i < map.length; i++) {
            if(map[0] == 4) 

return new Point(0,i);

if(map[map.length-1][i] == 3)

return new Point(map.length-1,i);

}

for(int i = 0; i < map.length; i++) {

if(map[i][0] == 4) 

return new Point(i,0);

if(map[i][map.length-1] == 4)

return new Point(i, map.length-1);

}*/

//not found on sides

//System.out.println("Not found on sides");

for(int i = 0; i < map.length; i++) {

for(int j = 0; j< map[1].length; j++){

if(map[i][j]==4)

return new Point(i,j);

}

}

//if not found

throw new RuntimeException("Ending Point could not be found! Invalid Map.");

}

/**

* Search edges first

* @return point where the start/init is found

*/

private Point findStart(){        

//searching sides

/*for(int i = 0; i < map.length; i++) {

if(map[0][i] == 3) 

return new Point(0,i);

if(map[map.length-1][i] == 3)

return new Point(map.length-1,i);

}

System.out.println("Searching x's");

for(int i = 0; i < map.length; i++) {

if(map[i][0] == 3) 

return new Point(i,0);

if(map[i][map.length-1] == 3)

return new Point(i, map.length-1);

}*/

//not found on sides
String s = "";
for(int i = 0; i < map.length - 1; i++) {

for(int j = 0; j< map[1].length - 1; j++){

if(map[i][j]==3)

return new Point(i,j);

}

}

//if not found

throw new RuntimeException("Starting Point could not be found! Invalid Map.");

}



private boolean isFound(Node currentBest) {

if(currentBest.getLastPoint().equals(end)){

System.out.println("TRUE");

System.out.println(currentBest.getLastPoint());

System.out.print(end);

return true;

}

else{

//System.out.println("FALSE");

//System.out.println(currentBest.getLastPoint());

//System.out.println(end);

return false;

}


}
   
private ArrayList<Node> findNewNodes(Node currentBest) {

ArrayList<Node> found = new ArrayList();

int x,y;



x = (int)currentBest.getLastPoint().getX();

y = (int)currentBest.getLastPoint().getY();



Point lastParent = currentBest.getLastParent();



//find possible new Points.

/* possiblities:

* x+1, y == a

* x-1, y == b

* x, y+1 == c

* x, y-1 == d

* x+1, y+1

* x+1, y-1

* x-1, y-1

* x-1, y+1

*/

try {

if (map[x + 1][y] != 1 && !currentBest.isMoveRedundant(new Point(x+1,y))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x + 1, y));

calcCost(temp, false);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x - 1][y] != 1 && !currentBest.isMoveRedundant(new Point(x-1,y))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x - 1, y));

calcCost(temp, false);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x][y + 1] != 1 && !currentBest.isMoveRedundant(new Point(x,y+1))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x, y + 1));

calcCost(temp, false);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x][y - 1] != 1 && !currentBest.isMoveRedundant(new Point(x,y-1))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x, y - 1));

calcCost(temp, false);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x + 1][y + 1] != 1 && !currentBest.isMoveRedundant(new Point(x+1,y+1))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x + 1, y + 1));

calcCost(temp, true);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x + 1][y - 1] != 1 && !currentBest.isMoveRedundant(new Point(x+1,y-1))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x + 1, y - 1));

calcCost(temp, true);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x - 1][y - 1] != 1 && !currentBest.isMoveRedundant(new Point(x-1,y-1))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x - 1, y - 1));

calcCost(temp, true);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

try {

if (map[x - 1][y + 1] != 1 && !currentBest.isMoveRedundant(new Point(x-1,y+1))) {

Node temp = new Node(currentBest);

temp.addPoint(new Point(x - 1, y + 1));

calcCost(temp, true);



found.add(temp);

}

} catch (IndexOutOfBoundsException ex) {

//can safely be ignored

}

return found;

}



private void calcCost(Node temp, boolean diagonal) {

Point last = temp.getLastPoint();

int x = (int)last.getX();

int y = (int)last.getY();



int goalX = (int)end.getX();

int goalY = (int)end.getY();



int h = 0, g = temp.g;



if(diagonal)

g += 13; //favor diagonal approch

else

g+=10;



//find huristic guess (h)

//Manhattan style

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

//System.out.println("x: " + x +" y: " + y + " cost: " + (h+g));

}



private Node findBestNode() {

int index = -1;

int x = Integer.MAX_VALUE;

if (currentNodes.size() == 0) {

System.out.println(currentBest.getLastPoint() + " end:" + end);

Point p = currentBest.getLastPoint();

int xBlocked = (int)p.getX(), yBlocked = (int)p.getY();

//unsafe, check bounds

for(int i = xBlocked-3; i < xBlocked+3 && i < map.length;i++){

for(int j = yBlocked-3; j< yBlocked+3 && j < map[1].length;j++){

if(j==yBlocked && i==xBlocked)

System.out.print("*");

else

System.out.print(map[i][j]);

}

System.out.println();

}

throw new RuntimeException("Empty Node list, blocked?");

//return currentBest;

}

/*else{*/

for(int i=0;i<currentNodes.size(); i++) {

int f = currentNodes.get(i).getCost();

//System.out.println(currentNodes.get(i) + " Cost: " + currentNodes.get(i).getCost());

//System.out.println("i: " + i + " f: " + f);

if(x >= f){

//System.out.println("Being reset, x was" + x +" f is " + f);

x = f;

index = i;

}

}

//System.out.print("Nodes: " + currentNodes.size());

//System.out.println(" " currentNodes.get(index).getLastPoint()  " Cost: " + currentNodes.get(index).getCost());

return currentNodes.get(index);

//}

}



/**

* Note: Will start the search if best path has

* not been determined!

* @return the node containing the best path

*/

public Node getBest(){

if(currentBest == null)

startSearch();

return currentBest;

}

}

