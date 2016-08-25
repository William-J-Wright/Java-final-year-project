import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//modifed and changed version of 
//https://www.careercup.com/question?id=5725353829990400
//last accessed 01/04/2016
public class ShortestPath {

	public static class Position {
		public int x;
		public int y;
                
		public Position predecessor;

                //setting a postion 
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
                //setting postion
		public Position(int x, int y, Position predecessor) {
			this(x, y);
			this.predecessor = predecessor;
		}
                //prints out the points.
		@Override
		public String toString() {
			return x + "," + y;
                        
		}
	}
//setting variables..
	public int[][] matrix;

	public Position[] shortestPath;

	private Stack<Position> path;

	public Position start;

        //the shortest path.
	public ShortestPath(int[][] matrix) {
		this.matrix = matrix;
	}
        //BFS start.
	public Position[] getPathBFS() {
		findStart();
		path = new Stack<Position>();
		shortestPath = null;

		LinkedList<Position> predecessors = new LinkedList<Position>();
		Queue<Position> queue = new LinkedList<Position>();
		queue.offer(start);
		visit(start);

		if (start == null) {
			return null;
		}

		while (!queue.isEmpty()) {
			Position position = queue.poll();
			predecessors.add(position);

			if (!endFound(position)) {
                            //finding the end point
                             //testing if the position that the car wants to move too 
                            //can be moved.
                            //the next 4 if statements are checking squares that directly touch
                            //the car. 
				Position nextPosition = new Position(position.x + 1, position.y, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}

				nextPosition = new Position(position.x, position.y + 1, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}

				nextPosition = new Position(position.x - 1, position.y, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}

				nextPosition = new Position(position.x, position.y - 1, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}
                                //the next 4 if statements are checking the diagonal moves for the car.
                                nextPosition = new Position(position.x+1, position.y - 1, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}
                                nextPosition = new Position(position.x+1, position.y + 1, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}
                                nextPosition = new Position(position.x-1, position.y - 1, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}
                                nextPosition = new Position(position.x-1, position.y + 1, position);
				if (isVisitable(nextPosition)) {
					queue.offer(nextPosition);
					visit(nextPosition);
				}
			} else {
				break;
			}
		}

		Position position = predecessors.getLast();

		if (position != null) {
			do {
				path.push(position);
				position = position.predecessor;
			} while (position != null);
                        //adding the size of the path to it
			shortestPath = new Position[path.size()];
			int i = 0;
                        //while the path isnt empty add it to an array.
			while (!path.isEmpty()) {
				shortestPath[i++] = path.pop();
			}
		}
                cleanUp();
		return shortestPath;
	}

	public void next(Position position) {
		stepForward(position);

		if (shortestPath == null || path.size() < shortestPath.length) {
			if (!endFound(position)) {
                             //testing if the position that the car wants to move too 
                            //can be moved.
                            //the next 4 if statements are checking squares that directly touch
                            //the car. 
				Position nextPosition = new Position(position.x + 1, position.y);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}

				nextPosition = new Position(position.x, position.y + 1);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}

				nextPosition = new Position(position.x - 1, position.y);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}

				nextPosition = new Position(position.x, position.y - 1);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}
                                nextPosition = new Position(position.x+1, position.y - 1);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}
                                nextPosition = new Position(position.x+1, position.y + 1);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}
                                nextPosition = new Position(position.x-1, position.y - 1);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}
                                nextPosition = new Position(position.x-1, position.y + 1);
				if (isVisitable(nextPosition)) {
					next(nextPosition);
				}
			} else {
				shortestPath = path.toArray(new Position[0]);
			}
		}

		stepBack();
	}

	public boolean isVisitable(Position position) {
            //finding out if the square the car is trying to move too is actually moveable. 
            //this is saying that if it is a road, or it is the other shortest paths road
            //you may move to it. 
		return position.y >= 0 
				&& position.x >= 0 
				&& position.y < matrix.length
				&& position.x < matrix[position.y].length
				&& (matrix[position.y][position.x] == 0 || matrix[position.y][position.x] == 8 || endFound(position));
	}
        //finding end of the map, and setting it. 
	public boolean endFound(Position position) {
		return matrix[position.y][position.x] == 4;
	}
        //adding a tested node to the matrix
	public void stepForward(Position position) {
		path.push(position);
		if (matrix[position.y][position.x] == 0 || matrix[position.y][position.x] == 8) {
			matrix[position.y][position.x] = 2;
		}
	}
//moving back on itself. so changing it back to a road (better path found)
	public void stepBack() {
		Position position = path.pop();
		if (matrix[position.y][position.x] == 2 || matrix[position.y][position.x] == 8) {
			matrix[position.y][position.x] = 0;
		}
	}
//adding a tested node to the matrix
	public void visit(Position position) {
		if (matrix[position.y][position.x] == 0 || matrix[position.y][position.x] == 8) {
			matrix[position.y][position.x] = 2;
		}
	}
//finding the starting point for the search. and then adding it to the start postion. 
	public void findStart() {
		if (start != null) {
			return;
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 3) {
					start = new Position(j, i);
				}
			}
		}
	}
//changing the map back too 0's once the search is completed. 
	public void cleanUp() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 2) {
					matrix[i][j] = 0;
				}
			}
		}
	}

}