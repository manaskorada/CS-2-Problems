
import java.util.*;

public class dobra {

    
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        String original = s.nextLine();
        int length = original.length();
        
        // turns string into an array of characters
        char[] origArr = original.toCharArray();
        
        // Checks all defining characteristics of string
        boolean hasL = false;
        int sVowels = highestVowelCount(origArr, length, 0);
        int sConsonants = highestConCount(origArr, length, 0);
        
        
        int count = 0;
        if (sVowels < 3 && sConsonants < 3) {
            count += backtrack(origArr, length, 0, sVowels, sConsonants, hasL);
        }
        
        
        
        System.out.println(count);
        
        
    }
    
    
    // Backtracking function
    public static int backtrack(char[] original, int length, int index, int sVowels, int sConsonants, boolean hasL) {
        // Where backtracking starts
        if (index == length && !hasL) {
            return 0;
        }
        
        if (index > 0) {
            char a = original[index - 1];
            if(a == 'A' || a == 'E' || a == 'I' || a == 'O' || a == 'U') {
                if (index >= 3) {
                    if (length > index + 1) {
                        sVowels = highestVowelCount(original, index + 2, index - 3);
                    } else {
                        sVowels = highestVowelCount(original, index, index - 3);
                    }
                }
                
                if (sVowels > 2) {
                    return 0;
                }
            } else {
                if (index >= 3) {
                    if (length > index + 1) {
                        sConsonants = highestConCount(original, index + 2, index - 3);
                    } else {
                        sConsonants = highestConCount(original, index, index - 3);
                    }
                }
                
                if (sConsonants > 2) {
                    return 0;
                }
            }
        }

        if (index == length) {      
            return 1;
        }
        
        // Where backtracking ends
        
        int count = 0;
        
        if (original[index] == '_') {
            boolean VowelNotWork = false;
            boolean ConNotWork = false;
            for (int i = 65; i < 91; i++) {
                original[index] = (char) i;
                
                if (i == 65) {
                    int newVowel;
                    if (index >= 2) {
                        
                        if (length > index + 2) {
                            newVowel = highestVowelCount(original, index + 3, index - 2);
                        } else {
                            newVowel = highestVowelCount(original, index, index - 2);
                        }
                        if (newVowel >= 3) {
                            VowelNotWork = true;
                        }
                    } else if (length > 2) {
                        if (index == 0) {
                            newVowel = highestVowelCount(original, 2, 0);
                            if (newVowel >= 3) {
                                VowelNotWork = true;
                            }
                        } else {
                            if (length > 3) {
                                newVowel = highestVowelCount(original, 3, 0);
                                if (newVowel >= 3) {
                                    VowelNotWork = true;
                                }
                            }
                            
                        }
                        
                    }
                }
                if (i == 66) {
                    int newCon;
                    if (index >= 2) {
                        
                        if (length > index + 2) {
                            newCon = highestConCount(original, index + 3, index - 2);
                        } else {
                            newCon = highestConCount(original, index, index - 2);
                        }
                        if (newCon >= 3) {
                            ConNotWork = true;
                        }
                    } else if (length > 2) {
                        if (index == 0) {
                            newCon = highestConCount(original, 2, 0);
                            if (newCon >= 3) {
                                ConNotWork = true;
                            }
                        } else {
                            if (length > 3) {
                                newCon = highestConCount(original, 3, 0);
                                if (newCon >= 3) {
                                    ConNotWork = true;
                                }
                            }
                            
                        }
                        
                    }
                }
                
                if (i == 65 || i == 69 || i == 73 || i == 79 || i == 85) {
                    if (VowelNotWork) {
                        continue;
                    }
                } else {
                    if (ConNotWork) {
                        continue;
                    }
                }
                
                
                
                boolean isL = false;
                if (i == 76) {
                    isL = true;
                }
                
                count += backtrack(original, length, index + 1, sVowels, sConsonants, hasL || isL);
                original[index] = '_';
            }
            
        } else {
            boolean isL = false;
                if (original[index] == 'L') {
                    isL = true;
                }
            count += backtrack(original, length, index + 1, sVowels, sConsonants, hasL || isL);
        }
        
        
        
        return count; 
    }
    
    
    // returns 3 if the highest number of sequential vowels is 3 or over in a certain range
    public static int highestVowelCount(char[] original, int length, int start) {
        int highestV = 0;
        
        for (int i = start; i < length; i++) {
            char a = original[i];
            
            if (a == 'A' || a == 'E' || a == 'I' || a == 'O' || a == 'U') {
                highestV++;
                
                if (highestV > 2) {
                    return highestV;
                }
                
            } else {
                highestV = 0;
            }
        }
        
        return highestV;
    }
    
    // returns 3 if the highest number of sequential consonants is 3 or over in a certain range
    public static int highestConCount(char[] original, int length, int start) {
        int highestC = 0;

        for (int i = start; i < length; i++) {
            char a = original[i];
            
            if (a == 'A' || a == 'E' || a == 'I' || a == 'O' || a == 'U' || a == '_') {
                highestC = 0;
            } else {
                highestC++;
                
                if (highestC > 2) {
                    return highestC;
                }
            }
        }
        
        return highestC;
    }
}
