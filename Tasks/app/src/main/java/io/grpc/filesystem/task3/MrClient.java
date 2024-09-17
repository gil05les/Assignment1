/*
 * Client program to request for map and reduce functions from the Server
 */

package io.grpc.filesystem.task3;

import io.grpc.filesystem.task2.MapReduce;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MrClient {
    Map<String, Integer> jobStatus = new HashMap<String, Integer>();


    public static void main(String[] args) throws Exception {
        String ip = args[0];
        Integer mapPort = Integer.parseInt(args[1]);
        Integer reducePort = Integer.parseInt(args[2]);
        String inputFilePath = args[3];
        String outputFilePath = args[4];

        MrClient client = new MrClient();
        int response;

        String chunkPath = MapReduce.makeChunks(inputFilePath);
        File dir = new File(chunkPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                if (f.isFile()) {
                    client.jobStatus.put(f.getPath(), 1);
                }
            }
        }
        client.requestMap(ip, mapPort, inputFilePath, outputFilePath);

        Set<Integer> values = new HashSet<Integer>(client.jobStatus.values());
        if (values.size() == 1 && client.jobStatus.containsValue(2)) {
            response = client.requestReduce(ip, reducePort, chunkPath, outputFilePath);
            if (response == 2) {
                System.out.println("Reduce task completed!");
            } else {
                System.out.println("Try again! " + response);
            }
        }
    }

    public void requestMap(String ip, Integer portNumber, String inputFilePath, String outputFilePath) throws InterruptedException {

        /*
         1. Open a gRPC channel to the Map server using the IP and port.
         2. Create a non-blocking stub for map requests.
         3. Implement a StreamObserver to handle responses. 
         - Check if map tasks are completed (status == 2).
         - Print success or failure messages.
         4. Loop through jobStatus, send map requests, and update statuses.
         5. Call stream.onCompleted() to finish and wait for the server's response (for example, using a CountDownLatch).
         6. Close the gRPC channel after completion.
        */

    }

    public int requestReduce(String ip, Integer portNumber, String inputFilePath, String outputFilePath) {

        /*
         1. Open a gRPC channel to the Reduce server.
         2. Create a blocking stub for reduce requests.
         3. Build and send the reduce request.
         4. Check the job status from the server's response.
         5. Close the gRPC channel after completion.
         6. Return the job status (e.g., 2 for success).
        */

        return 0; // update this return statement
    }

}
