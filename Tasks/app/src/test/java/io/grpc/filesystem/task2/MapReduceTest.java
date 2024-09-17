package io.grpc.filesystem.task2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MapReduceTest {
    
    private static Path tempDir;
    
    // This method is run once before all tests
    @BeforeAll
    public static void createTempDirectory() throws IOException {
        // Create a temporary directory for all test files
        tempDir = Files.createTempDirectory("mapreduce-test");
    }

    // This method is run once after all tests
    @AfterAll
    public static void deleteTempDirectory() throws IOException {
        if (tempDir != null && Files.exists(tempDir)) {
            try (Stream<Path> walk = Files.walk(tempDir)) {
                walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        if (!file.delete()) {
                            System.err.println("Failed to delete: " + file);
                        }
                    });
            }
        }
    }

    @Test
    public void testFilterPunctuations() {
        String input = "Hello, World!";
        String expected = "hello world";
        String result = MapReduce.filterPunctuations(input);
        assertEquals(expected, result);
    }

    @Test
    public void testSplitTextIntoWords() {
        String input = "Hello world from Java";
        String[] expected = {"Hello", "world", "from", "Java"};
        String[] result = MapReduce.splitTextIntoWords(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testIsValidWord() {
        assertTrue(MapReduce.isValidWord("hello"));
        assertTrue(MapReduce.isValidWord("Java123"));
        assertFalse(MapReduce.isValidWord("hello@"));
        assertFalse(MapReduce.isValidWord("123!"));
    }

    @Test
    public void testMapFunction() throws IOException {
        Path inputFilePath = tempDir.resolve("test-chunk.txt");
        Files.write(inputFilePath, List.of("Hello world! Hello everyone."));

        MapReduce.map(inputFilePath.toString());
        
        String resultFilePath = "map-test-chunk.txt";
        List<String> result = Files.readAllLines(tempDir.resolve(resultFilePath));

        List<String> expected = Arrays.asList("hello:1", "world:1", "hello:1", "everyone:1");
        assertEquals(expected, result);
    }

    @Test
    public void testCollectWordCounts() throws IOException {
        String[] mapFiles = {tempDir.resolve("map1.txt").toString(), tempDir.resolve("map2.txt").toString()};
        
        Files.write(Paths.get(mapFiles[0]), Arrays.asList("hello:1", "world:2"));
        Files.write(Paths.get(mapFiles[1]), Arrays.asList("hello:3", "java:1"));

        Map<String, Integer> result = MapReduce.collectWordCounts(mapFiles);
        
        Map<String, Integer> expected = new HashMap<>();
        expected.put("hello", 4);
        expected.put("world", 2);
        expected.put("java", 1);

        assertEquals(expected, result);
    }

    @Test
    public void testReduceFunction() throws IOException {
        Path mapDirPath = Files.createDirectory(tempDir.resolve("mapFiles"));
        Path outputFilePath = tempDir.resolve("output.txt");

        Files.write(mapDirPath.resolve("map1.txt"), Arrays.asList("hello:1", "world:2"));
        Files.write(mapDirPath.resolve("map2.txt"), Arrays.asList("hello:3", "java:1"));

        MapReduce.reduce(mapDirPath.toString(), outputFilePath.toString());

        List<String> result = Files.readAllLines(outputFilePath);

        List<String> expected = Arrays.asList("hello:4", "world:2", "java:1");
        assertEquals(expected, result);
    }

    @Test
    public void testStoreFinalCounts() throws IOException {
        Map<String, Integer> wordCounts = new HashMap<>();
        wordCounts.put("hello", 5);
        wordCounts.put("world", 10);
        wordCounts.put("java", 2);

        Path outputFilePath = tempDir.resolve("output.txt");

        MapReduce.storeFinalCounts(wordCounts, outputFilePath.toString());

        List<String> result = Files.readAllLines(outputFilePath);

        List<String> expected = Arrays.asList("world:10", "hello:5", "java:2");
        assertEquals(expected, result);
    }
}