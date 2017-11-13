package a_star;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	//Node node = new Node(0, 0, 0);
	
	static int ROWS = 15;
	static int COLS = 15;
	static String[][] env = new String [ROWS][COLS];
	
	static Scanner input = new Scanner(System.in);
	static Random number = new Random();
	
	static String startingNode = "";
	static String goalNode = "";
	
	static int startrow = 0;
	static int startcol = 0;
	static int goalrow = 0;
	static int goalcol = 0;
	static int h = 0; // heuristic
	static int g = 0; // parent node + move
	static int f = 0; // g + h
	static int parent = 0; //
	static String get = " ";
	
	static ArrayList<Integer> openList = new ArrayList<Integer>();
	static ArrayList<String> closedList = new ArrayList<String>();  
	static ArrayList<Integer> tempList = new ArrayList<Integer>();
	
	public static void main(String [] args) {
		
		System.out.println("a* search \n");
		
		// start methods
		newEnvironment(); // creates environment
		nodeSelect(); // user input
		fCalc(startrow, startcol, goalrow, goalcol, parent); // checks neighbors for G values
		//manhattan(startrow, startcol, goalrow, goalcol, h); // finds heuristic value
		environment(); // displays new board
		//move(startrow, startcol, goalrow, goalcol); // moves from Parent Node to best F value
		
	}
	
	public static void newEnvironment() {
		
		int randomRowNodes = 0;
		int randomColNodes = 0;
		
		// calculates 10% of obstacles
		for(int a = 0; a < 23; a++) {
			randomRowNodes = number.nextInt(14) + 1;
			randomColNodes = number.nextInt(14) + 1;
			
			if(env[randomRowNodes][randomColNodes] != " X") {
				env[randomRowNodes][randomColNodes] = " X";
			} else {
				randomRowNodes = number.nextInt(14) + 1;
				randomColNodes = number.nextInt(14) + 1;
				env[randomRowNodes][randomColNodes] = " X";
			}
		}
		
		// display board
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(env[i][j] != " X") {
					env[i][j] = " -";
				}
				System.out.print(env[i][j] + " ");
			}
			System.out.println();
		}	
		
	}

	
	public static void nodeSelect() {
		
		// user input for start node and goal node
		System.out.print("Starting node(row,col): ");
		startingNode = input.nextLine();
		System.out.print("Goal node(row,col): ");
		goalNode = input.nextLine();
	
		// cleans input
		startingNode = startingNode.replaceAll("\\s","");
		
		String[] startingcord = startingNode.split(",");
		startrow = Integer.parseInt(startingcord[0]);
		startcol = Integer.parseInt(startingcord[1]);
		
		goalNode = goalNode.replaceAll("\\s","");
		
		String[] goalcord = goalNode.split(",");
		goalrow = Integer.parseInt(goalcord[0]);
		goalcol = Integer.parseInt(goalcord[1]);
		
		// prints new board with start and goal nodes
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(env[startrow][startcol] == " X" && env[goalrow][goalcol] == " X") {
					env[startrow][startcol] = " S"; 
					env[goalrow][goalcol] = " G"; 
				} else {
//							System.out.println("There is no Node at: [" + startrow + "," + 
//									startcol + "] or [" + goalrow + "," + goalcol + "]");
					//nodeSelect();
				}
			}
		}
		
	}
	
	private static Node manhattan(int startrow, int startcol, int goalrow, int goalcol, int h) {
		
		// heuristic value
		for(int row = startrow; row <= goalrow; row++) {
			for(int col = startcol; col <= goalcol; col++) {
				if(env[row][col] != " G" && env[row][col] != " S" && env[row][col] != " X") {
					h = (Math.abs(row - goalrow) + Math.abs(col - goalcol));
					//env[row][col] = (" " + h);
				}
			}
		}
		
		h = (Math.abs(startrow - goalrow) + Math.abs(startcol - goalcol));
		System.out.println("hh " + (h-1));	
		
		return new Node(startrow, startcol, 0);
	
	}
	
	
	private static void environment() {
		
		// display board
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				System.out.print(env[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	

	private static void move(int startrow, int startcol, int goalrow, int goalcol, int smallest) {
		
		// moves around start
		int startX = (startrow - 1 < 0) ? startrow : startrow - 1;
		int startY = (startcol - 1 < 0) ? startcol : startcol - 1;
		int goalX =   (startrow + 1 > 14) ? startrow : startrow + 1;
		int goalY =   (startcol + 1 > 14) ? startcol : startcol + 1;
	
		
		System.out.println("GET" + get);
		// checking nodes around PARENT node
		for (int rowNum = startX; rowNum <= goalX; rowNum++) {
		    for (int colNum = startY; colNum <= goalY; colNum++) {
		    	System.out.print(env[rowNum][colNum]);
		    	if(env[rowNum][colNum] == String.valueOf(get)) {
	    			System.out.println((rowNum + "," + colNum) + "GET");
	    		} else {
	    			System.out.println("f");
	    		}
		    }
		}
		
	}

	private static Node fCalc(int startrow, int startcol, int goalrow, int goalcol, int parent) {
		
		// moves around start
		int startX = (startrow - 1 < 0) ? startrow : startrow - 1;
		int startY = (startcol - 1 < 0) ? startcol : startcol - 1;
		int goalX =   (startrow + 1 > 14) ? startrow : startrow + 1;
		int goalY =   (startcol + 1 > 14) ? startcol : startcol + 1;
		
		int smallest = 0;
		
		
		// checking nodes around PARENT node
		for (int rowNum = startX; rowNum <= goalX; rowNum++) {
		    for (int colNum = startY; colNum <= goalY; colNum++) {
		    	if(env[rowNum][colNum] != " G" && env[rowNum][colNum] != " S" && env[rowNum][colNum] != " X") {
					h = (Math.abs(rowNum - goalrow) + Math.abs(colNum - goalcol));
					//env[row][col] = (" " + h);
				}
		    	if((rowNum != startrow && colNum != startcol) && (env[rowNum][colNum] != " X")) { // if diag places D
		    		g = parent + 14;
		    		f = g + h;
		    		
		    		// condition for min F
		    		smallest = (smallest == 0) ? smallest = f 
		    				: (smallest > f) ? smallest = f 
		    				: smallest;
		    		
		    		env[rowNum][colNum] = Integer.toString(f);; // displays F values
		    	}
		    	if((rowNum == startrow || colNum == startcol) && (rowNum != startrow || colNum != startcol) && (env[rowNum][colNum] != " X")) {
		    		g = parent + 10;
		    		f = g + h;
		    		
		    		// condition for min F
		    		smallest = (smallest == 0) ? smallest = f 
		    				: (smallest > f) ? smallest = f 
		    				: smallest;
		    		
		    		env[rowNum][colNum] = Integer.toString(f); // displays F values
		    	} 
		    	if(env[rowNum][colNum] == " S"){ // any Start Node
		    		closedList.add((rowNum + "," + colNum));
		    	}
		    	if(env[rowNum][colNum] == " X") { // any obstacles
		    		closedList.add((rowNum + "," + colNum));
		    	}
		    	
		    	if((env[rowNum][colNum] != " S" && env[rowNum][colNum] != " X")) { // if diag places D
		    		openList.add((Integer.parseInt(env[rowNum][colNum]))); // add
		    	}
		    	
		    }
		}
		
		for(Integer min : openList) {
			if(min == smallest) {
				tempList.add(min);
			}
		}
		
		
		parent = g;
		
		System.out.println(openList + "Final openList");
		System.out.println(closedList + "Final closedList");
		int hh = (Math.abs(startrow - goalrow) + Math.abs(startcol - goalcol));
		System.out.println("hh " + (hh-1));
		System.out.println(tempList + "temp");
		
		
		get = tempList.get(0).toString();
		
		move(startrow, startcol, goalrow, goalcol, smallest);
		
		return new Node(startrow, startcol, 0);
		
	}

	
	
}
















