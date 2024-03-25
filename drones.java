/*
Manas Korada
COP 3503C - 0001
Spring 2024
Arup Guha
3/15/24
Drone Problem - P4
Use BFS to find fewest number of remote control positions to get drones to all make their deliveries
*/ 

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.*;

public class drones {
    // Directions of drone movement within grid
    final public static int[] DX = {-1,0,0,1};
    final public static int[] DY = {0,-1,1,0};
    
    // holds grid of no fly zones and arrays of ints holding the initial positions of the drones in binary and positions of groups in binary
    public static int[] initialPosArr;
    public static int[] finalPosArr;
    public static boolean[][] grid;
    

    public static void main(String[] args) {
        
        // Reads in n representing n number of drones
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        initialPosArr = new int[n];
        finalPosArr = new int[n];
        grid = new boolean[8][8];

        // Reads in the grid. Marks no fly zones as true in grid array. Marks group positions as no fly zones in grid as well. 
        // Fills initialPosArr and finalPosArr with positions of drones and groups in binary respectively
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String cell = scanner.next();
                if (cell.equals("XX")) {
                    grid[i][j] = true;
                } else if (cell.charAt(0) == 'D') {
                    int droneIndex = Character.getNumericValue(cell.charAt(1)) - 1;
                    initialPosArr[droneIndex] = i * 8 + j;
                } else if (cell.charAt(0) == 'G') {
                    int groupIndex = Character.getNumericValue(cell.charAt(1)) - 1;
                    finalPosArr[groupIndex] = i * 8 + j;
                    grid[i][j] = true; // Mark group location as no-fly zone
                }
            }
        }
        
        // Creates integers storing the drones and the groups as bitmasks
        int dronePos = 0;
        int groupPos = 0;
        for (int i = 0; i < n; i++) {
            dronePos += (initialPosArr[i] << (6 * i));
            groupPos += (finalPosArr[i] << (6 * i));
        }
        
        // runs bfs to find shortest number of moves to get drones to groups
        int result = bfs(dronePos, groupPos, n);
        // prints out the result
        System.out.println(result);
    }
    
    
    // BFS Algorithm to find minimum number of moves
    static int bfs(int initialPos, int finalPos, int n) {
        // Creates queue and puts the original position of the drones in the queue
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(initialPos);
        
        // Creates distance array to hold the possible positions of the drones and the number of moves to get to them
        int distLen = 1 << (6 * n);
        int[] distance = new int[distLen];
        Arrays.fill(distance, -1);
        distance[initialPos] = 0;

        // loops through until there are no more positions in the queue
        while (!queue.isEmpty()) {
            // Dequeues the first position in the queue
            int currPos = queue.poll();
            
            // Gets a set of integers representing the next 4 positions from the current one
            int[] nextMoves = getNext(currPos, n);
            
            for (int i = 0; i < 4; i++) {
                if (distance[nextMoves[i]] == -1) {
                    // Calculates minimum number of moves to get to this new position
                    distance[nextMoves[i]] = distance[currPos] + 1;
                    
                    // If this new position happens to be the final positon it is returned
                    if (nextMoves[i] == finalPos) {
                        return distance[finalPos];
                    }
                   
                    // New position is put into the queue
                    queue.offer(nextMoves[i]);
                }
            }
        }
        // returns -1 if a path can't be found
        return distance[finalPos];
    }
    
    // Gets set of next positions based on current position
    static int[] getNext(int currPos, int n) {
        int[] nextPos = new int[4];

            // Goes through each of the 4 possible directions from currPos
            for (int j = 0; j < 4; j++) {
                int newPos = 0;
                // Calculates the new position of all of the drones based on the direction we go in
                for (int i = 0; i < n; i++) {
                    // Finds the current position of a single drone
                    int currPosB = (currPos >> (6 * i)) & 63;
                    
                    // If this drone has already gotten to its group move onto the next drone
                    if (currPosB == finalPosArr[i]) {
                        newPos += (currPosB << (6 * i));
                        continue;
                    }
                    
                    // Finds the next row and column of the current drone 
                    int newRow = currPosB / 8 + DX[j];
                    int newCol = currPosB % 8 + DY[j];
                    
                    // Checks that the new row and column we be a valid position for the current drone to go in and puts it into the array it is
                    if (isValidMove(newRow, newCol, i)) {
                        newPos += ((newRow * 8 + newCol) << (6 * i));
                    } else {
                        newPos += (currPosB << (6 * i));
                    }
                }
                // finds the new position bitmask
                nextPos[j] = newPos;
            }
        
        return nextPos;
    }

    // Checks if the row and column are a valid positon for the current drone to be in on the grid
    static boolean isValidMove(int row, int col, int index) {
        // Checks that the row and column are within the grid
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            // Checks that the current position isn't a no fly zone
            if (!grid[row][col]) {
                return true;
            }
            // Checks if the current position is the group belonging to the current drone
            int newPos = row * 8 + col;
            if (finalPosArr[index] == newPos) {
                return true;
            }
        }

        return false;
    }

}