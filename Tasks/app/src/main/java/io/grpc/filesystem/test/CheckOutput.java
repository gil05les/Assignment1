/*
 * Use this to check your output against the expected output file (output/output.txt).
 * Both tasks are expected to have the same word count and order.
 */

package io.grpc.filesystem.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckOutput {
    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];
        String l1;
        String l2;

        // Read and map the original file
        Map<String, Integer> original = new HashMap<>();
        BufferedReader bf1 = new BufferedReader(new FileReader("output/output.txt"));
        while ((l1 = bf1.readLine()) != null) {
            if (!l1.isEmpty()) {
                String[] subStrings = l1.split(":");
                original.put(subStrings[0], Integer.parseInt(subStrings[1]));
            }
        }

        // Read and map the file that needs to be compared
        Map<String, Integer> copy = new HashMap<>();
        BufferedReader bf2 = new BufferedReader(new FileReader(outputFilePath));
        while ((l2 = bf2.readLine()) != null) {
            if (!l2.isEmpty()) {
                String[] subStrings = l2.split(":");
                copy.put(subStrings[0], Integer.parseInt(subStrings[1]));
            }
        }

        // Print the verdict
        if (original.equals(copy)) {
            System.out.println("Both files are the same!");
        } else {
            System.out.println("Both files are NOT the same!");
        }

        bf1.close();
        bf2.close();
    }
}
