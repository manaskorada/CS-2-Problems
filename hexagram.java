/*
Manas Korada
COP 3503C - 0001
Spring 2024
Arup Guha
1/28/24
Hexagram Problem - P2
Calculate the number of valid ways to assign numbers to the vertices of a hexagram using backtracking
*/ 

import java.util.*;
        
public class hexagram {
    public static void main(String[] args) {
        // Scans in the first 12 integers
        Scanner stdin = new Scanner(System.in);
        int[] puzzle = new int[12];
        for (int i=0; i<12; i++) puzzle[i] = stdin.nextInt();
        
        // Checks if the 12 integers are 0s, if they are then the program ends
        while (!zero(puzzle)) {
            // Count represents the number of valid arrangements for the hexagram
            int count = 0;
            // propLineSum calculates the sum value of each line in the hexagram that would have to have in order to be valid
            int propLineSum = linesumVal(puzzle);
            // currentArrangement is an array to hold the arrangements of the hexagram to test
            int[] currentArrangement = new int[12];
            // used is an array of booleans to help move through each of the different arrangements
            boolean[] used = new boolean[12];
            for (int j = 0; j < 12; j++) used[j] = false;
            
            // If propLineSum it means that no line sum value could be found to keep all the lines the same
            if (propLineSum != -1) {
                // 
                count = backtrack(propLineSum, puzzle, currentArrangement, used, 0);
                // divides the count by 12 to account for rotations and reflections
                count = count / 12;
            }
            
            // Output answer for one case.
            System.out.println(count);
            for (int i=0; i<12; i++) puzzle[i] = stdin.nextInt();
        }
    }
    
    // Checks to see if all integers in puzzle array are 0's, if they are then the program ends
    public static boolean zero(int[] puzzle) {
        for (int i = 0; i < 12; i++) {
            if (puzzle[i] != 0) {
                return false;
            }
        }

        return true;
    }
    
    // Checks to see if there is a possible valid arrangement where all the lines could be equal to the same value. If there isn't then the program skips the backtracking and prints out 0
    public static int linesumVal(int[] puzzle) {
        // Calculates the sum of all the 12 values in the puzzle array
        int sum = 0;
        for (int i = 0; i < 12; i++) sum += puzzle[i];
        
        // Checks that the sum is a multiple of 3
        if (sum % 3 != 0) {
            return -1;
        }
        
        // The line value is sum / 3
        return sum / 3;
    }
    
    // backtrack function that goes through the arrangements to calculate the number of valid arrangements of the hexagram
    public static int backtrack(int propLineSum, int[] puzzle, int[] currentArrangement, boolean[] used, int index) {
        // backtracks through the current arrangement whenever another line or lines has been completely filled.
        // if a certain line has been filled but the sum doesn't equal the propLineSum, it backtracks and doesn't pursue this arrangement further
        switch (index) {
            case 5: 
                if (currentArrangement[1] + currentArrangement[2] + currentArrangement[3] + currentArrangement[4] != propLineSum) {
                    return 0;
                }
                break;
            case 8: 
                if (currentArrangement[0] + currentArrangement[2] + currentArrangement[5] + currentArrangement[7] != propLineSum) {
                    return 0;
                }
                break;
            case 11:
                if (currentArrangement[0] + currentArrangement[3] + currentArrangement[6] + currentArrangement[10] != propLineSum) {
                    return 0;
                }
                if (currentArrangement[7] + currentArrangement[8] + currentArrangement[9] + currentArrangement[10] != propLineSum) {
                    return 0;
                }
                break;
            case 12:
                if (currentArrangement[4] + currentArrangement[6] + currentArrangement[9] + currentArrangement[11] != propLineSum) {
                    return 0;
                }
                if (currentArrangement[1] + currentArrangement[5] + currentArrangement[8] + currentArrangement[11] != propLineSum) {
                    return 0;
                } 
                return 1;
            default: break;
        }
        
        // Changes the arrangement to check if they would work 
        int count = 0;
        for (int i = 0; i < 12; i++) {
            if (!used[i]) {
                currentArrangement[index] = puzzle[i];
                used[i] = true;
                
                count += backtrack(propLineSum, puzzle, currentArrangement, used, index+1);
                
                used[i] = false;
            }
        }

        // Returns the count of valid arrangements
        return count;
    }
}
