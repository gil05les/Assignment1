/*
 * the MapReduce functionality implemented in this program takes a single large text file to map i.e. split it into small chunks
 * Then, all words are assigned an initial count of one
 * Finally, it reduces by counting the unique words
 */

package io.grpc.filesystem.task2;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

public class MapReduce {

    private static final int CHUNK_SIZE = 500;

    /**
     * Splits the input file into smaller chunks and stores them in a temporary directory.
     *
     * @param inputFilePath The path to the input file to be split.
     * @return The path of the directory where chunks are stored.
     * @throws IOException If an error occurs during file I/O.
     */
    public static String makeChunks(String inputFilePath) throws IOException {
        int count = 1;
        File inputFile = new File(inputFilePath);
        File chunkDir = new File(inputFile.getParent() + "/temp");
        if (!chunkDir.exists()) {
            chunkDir.mkdirs();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line = br.readLine();

            while (line != null) {
                File chunkFile = new File(chunkDir, "chunk" + String.format("%03d", count++) + ".txt");
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(chunkFile))) {
                    int fileSize = 0;
                    while (line != null) {
                        byte[] bytes = (line + System.lineSeparator()).getBytes(Charset.defaultCharset());
                        if (bytes.length > CHUNK_SIZE) {
                            System.err.println("Skipping line exceeding chunk size: " + line);
                            line = br.readLine();
                            continue;
                        }
                        if (fileSize + bytes.length > CHUNK_SIZE)
                            break;
                        out.write(bytes);
                        fileSize += bytes.length;
                        line = br.readLine();
                    }
                }
            }
        }
        return chunkDir.getPath();
    }
    
    /**
     * Filters punctuations from the given line of text.
     *
     * @param line The line of text to be filtered.
     * @return The filtered line of text.
     */
    public static String filterPunctuations(String line) {
        /*
         * Insert your code here.
         * Remove any punctuation from the line using regex like "\\p{Punct}".
         */
        return null; // Replace with the filtered line
    }

    /**
     * Splits the given line of text into words.
     *
     * @param line The line of text to split.
     * @return An array of words from the input line.
     */
    public static String[] splitTextIntoWords(String line) {
        /*
         * Insert your code here.
         * Split the text using regex like "\\s+" to extract words.
         */
        return null; // Replace with an array of words
    }

    /**
     * Checks if a given word is valid (alphanumeric only).
     *
     * @param word The word to check.
     * @return True if the word is valid, false otherwise.
     */
    public static boolean isValidWord(String word) {
        /*
         * Insert your code here.
         * Use regex to ensure the word is valid (e.g., "^[a-zA-Z0-9]+$").
         */
        return false; // Replace with actual condition
    }

    /**
     * Maps the content of each file chunk by filtering, splitting, and counting words.
     *
     * @param inputFilePath The path of the file chunk to process.
     * @throws IOException If an error occurs during file I/O.
     */
    public static void map(String inputFilePath) throws IOException {

        /*
         * Insert your code here.
         * Use filterPunctuations, splitTextIntoWords, isValidWord, and mapWordToCount functions to map the words.
         * Save the map output in a file named "map-chunk001" in the folder of the inputFilePath.
         */
    }

    /**
     * Collects word-count pairs from map files.
     *
     * @param mapFiles An array of map file paths.
     * @return A map containing word counts.
     * @throws IOException If an error occurs during file I/O.
     */
    public static Map<String, Integer> collectWordCounts(String[] mapFiles) throws IOException {
        /*
         * Insert your code here.
         * Parse the map files to extract word-count pairs and return them in a map.
         */
        return null; // Replace with actual word count map
    }

    /**
     * Reduces the mapped word counts into a final result file.
     *
     * @param mapDirPath     The path of the directory containing map files.
     * @param outputFilePath The path of the final output file.
     * @throws IOException If an error occurs during file I/O.
     */
    public static void reduce(String mapDirPath, String outputFilePath) throws IOException {

        /*
         * Insert your code here.
         * Use collectWordCounts and aggregateWordCounts functions to aggregate word counts.
         * Save the output to the specified outputFilePath.
         */
    }

    /**
     * Sorts the word counts and stores them in the final output file.
     *
     * @param wordCounts The map of word counts to be sorted and stored.
     * @param outputFilePath The file to store the sorted word counts.
     * @throws IOException If an error occurs during file I/O.
     */
    public static void storeFinalCounts(Map<String, Integer> wordCounts, String outputFilePath) throws IOException {
        /*
         * Insert your code here.
         * Sort the wordCounts map and write it to the output file.
         */
    }

    public static void main(String[] args) throws IOException { // update the main function if required
        if (args.length < 2) {
            System.out.println("Usage: <inputFilePath> <outputFilePath>");
            return;
        }
        String inputFilePath = args[0];
        String outputFilePath = args[1];

        // Split input file into chunks
        String chunkDirPath = makeChunks(inputFilePath);

        // Map phase: Process each chunk
        File chunkDir = new File(chunkDirPath);
        File[] chunkFiles = chunkDir.listFiles((dir, name) -> name.startsWith("chunk"));

        if (chunkFiles != null) {
            for (File chunkFile : chunkFiles) {
                map(chunkFile.getPath());
            }
        }

        // Reduce phase: Aggregate map results
        reduce(chunkDirPath, outputFilePath);
    }
}
