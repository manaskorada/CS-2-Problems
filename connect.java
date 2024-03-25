/*
Manas Korada
COP 3503C - 0001
Spring 2024
Arup Guha
2/18/24
Connect Problem - P3
uses disjoint set to find average connectivity between n computers after certain connections are made
*/ 

import java.util.*;


class connect {
    // Class for the disjoint set
    static class DisjointSet {
        int[] parent; // holds the parents of the disjoint set
        int[] size; // holds the size or number of computers at each respective parent
        long total; // holds the numerator of the average connectivity
        long components; // holds the denominator of the average connectivity
        int queries;

        // Constructor of Disjoint Set With n seperate trees
        public DisjointSet(int n, int m) {
            queries = m;
            parent = new int[n + 1];
            size = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
            
            total = n;
            components = n;
        }

        // Looks for the root of the tree that holds the x computer
        public int find(int x) {
            if (parent[x] == x)
                return x;
            return parent[x] = find(parent[x]);
        }

        // Union function between 2 computers int the disjoint set
        public void union(int x, int y) {
            x = find(x);
            y = find(y);
            if (x != y) {
                if (size[x] < size[y]) {
                    int temp = x;
                    x = y;
                    y = temp;
                }
                parent[y] = x;
                
                // When there is a union between 2 computers the numerator and denominator of the average connectivity are updated
                total -= (long) size[x] * size[x];
                total -= (long) size[y] * size[y];
                components--;
                
                // The size of the tree is updated
                size[x] += size[y];
                
                // The new tree size is included into the total
                total += (long) size[x] * size[x];
            }
        }

        // When query called it prints out the information on the average connectivty
        public void averageConnectivity(int iteration) {
            // Simplifies the fraction of the average connectivity with the GCD
            long GCD = gcd(total, components);
            long totalSimp = total / GCD;
            long CompSimp = components / GCD;
            
            
            if (iteration != queries) {
                System.out.print(totalSimp + "/" + CompSimp + "\n");
            } else {
                System.out.print(totalSimp + "/" + CompSimp);
            }
            
        }

        // Finds the gcd between 2 long variables
        private long gcd(long a, long b) {
            if (b == 0) {
                return a;
            }
            return gcd(b, a % b);
        }
    }

    // main function
    public static void main(String[] args) {
        // scans in values representing number of computers and number of queries 
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        
        // Creates disjoint set
        DisjointSet disSet = new DisjointSet(n, m);
        
        // Loops through m queries
        for (int i = 1; i <= m; i++) {
            int choice = scanner.nextInt();
            if (choice == 1) { // scans in the 2 computer values and unionizes them if choice is 1
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                disSet.union(u, v);
            } else if (choice == 2) { // Prints average connectivity if choice is 2
                disSet.averageConnectivity(i);
            }
        }
        
        scanner.close();
    }
}
