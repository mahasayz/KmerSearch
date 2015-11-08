package pattern.util;

import com.sun.tools.javac.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by malam on 11/8/15.
 */
public class Combine {

    public static void main(String[] args) {
//        // Create the initial vector of 2 elements (apple, orange)
//        ICombinatoricsVector<String> originalVector = Factory.createVector(new String[]{"A", "C", "G", "T"});
//
//        // Create the generator by calling the appropriate method in the Factory class.
//        // Set the second parameter as 3, since we will generate 3-elemets permutations
//        Generator<String> gen = Factory.createPermutationWithRepetitionGenerator(originalVector, 2);
//
//        // Print the result
//        for (ICombinatoricsVector<String> perm : gen)
//            System.out.println(perm);

        System.out.println("First Test");
        char set1[] = {'A', 'C', 'G', 'T'};
        int k = 2;
        List<String> result = new ArrayList<String>();
        printAllKLength(set1, k, result);
        for (String str : result) {
            System.out.println(str);
        }

    }

    // The method that prints all possible strings of length k.  It is
    //  mainly a wrapper over recursive function printAllKLengthRec()
    public static void printAllKLength(char set[], int k, List<String> result) {
        int n = set.length;
        printAllKLengthRec(set, "", n, k, result);
    }

    // The main recursive method to print all possible strings of length k
    static void printAllKLengthRec(char set[], String prefix, int n, int k, List<String> result) {

        // Base case: k is 0, print prefix
        if (k == 0) {
            result.add(prefix);
            return;
        }

        // One by one add all characters from set and recursively
        // call for k equals to k-1
        for (int i = 0; i < n; ++i) {

            // Next character of input added
            String newPrefix = prefix + set[i];

            // k is decreased, because we have added a new character
            printAllKLengthRec(set, newPrefix, n, k - 1, result);
        }
    }

}
