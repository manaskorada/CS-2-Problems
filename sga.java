import java.util.HashMap;
import java.util.Scanner;

public class sga {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Takes in integer representing number of students
        int n = scanner.nextInt();
        scanner.nextLine(); 

        // Creates a hashmap holding characters and another hashmap holding strings and integers
        HashMap<Character, HashMap<String, Integer>> fullMap = new HashMap<>();

        // Goes through each student and adds them to the full hashmap under a sub hashmap under the appropriate character
        for (int i = 0; i < n; i++) {
            // Takes in the name string
            String name = scanner.nextLine();
            char firstLetter = name.charAt(0);

            // Gets the hashmap for names starting with the character firstLetter
            HashMap<String, Integer> nameMap = fullMap.getOrDefault(firstLetter, new HashMap<>());

            if (nameMap.containsKey(name)) {
                // Increment the occurrence count for the current name if it is already there
                nameMap.put(name, nameMap.get(name) + 1);
            } else {
                // Adds the name to the hashmap with an occurence count of 1 if it is already there
                nameMap.put(name, 1);
            }
            
            // Put the updated HashMap back into the Full HashMap
            fullMap.put(firstLetter, nameMap);
        }
        
        // Calculates the number of possible President and Vice President pairs
        long totalPairs = 0;
        for (HashMap<String, Integer> nameMap : fullMap.values()) {
            if (nameMap.size() > 1) {
                // If there are multiple names with the same first letter it contributes to the total pairs
                for (String i : nameMap.keySet()) {
                    for(String j : nameMap.keySet()) {
                        // Checks to make sure the names in the current pair aren't equal to each other before adding to totalPairs
                        if (!i.equals(j)) {
                            totalPairs = nameMap.get(i) * nameMap.get(j) +totalPairs;
                        }
                    }
                }
            }
        }

        System.out.println(totalPairs);
        scanner.close();
    }
}
