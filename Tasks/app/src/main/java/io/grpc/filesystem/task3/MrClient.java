/*
 * Client program to request for map and reduce functions from the Server
 */

package io.grpc.filesystem.task3;

import com.task3.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.filesystem.task2.MapReduce;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
            System.out.println("testettssetstes");
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

        // 1. Open a gRPC channel to the Map server using the IP and port.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, portNumber)
                .usePlaintext() // Use plaintext for non-encrypted communication
                .build();

        // 2. Create a non-blocking stub for map requests.
        AssignJobGrpc.AssignJobStub stub = AssignJobGrpc.newStub(channel);

        // 3. Implement a StreamObserver to handle responses.
        StreamObserver<MapInput> requestObserver = stub.map(new StreamObserver<MapOutput>() {
            @Override
            public void onNext(MapOutput mapOutput) {
                int status = mapOutput.getJobstatus();
                String chunkFilePath = mapOutput.getInputfilepath(); // Retrieve inputfilepath

                if (status == 2) {
                    System.out.println("Map task completed successfully for chunk: " + chunkFilePath);
                    // Update job status to completed using the correct chunk file path
                    jobStatus.put(chunkFilePath, status);
                    System.out.println("Updated jobStatus: " + jobStatus);
                    System.out.println("Job status successfully updated.");
                } else {
                    System.err.println("Map task failed for chunk " + chunkFilePath + " with status: " + status);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error occurred: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("All map responses received.");
            }
        });

        // 4. Loop through jobStatus, send map requests, and update statuses.
        for (Map.Entry<String, Integer> entry : jobStatus.entrySet()) {
            MapInput input = MapInput.newBuilder()
                    .setIp(ip)
                    .setPort(portNumber)
                    .setInputfilepath(entry.getKey())
                    .setOutputfilepath(outputFilePath)
                    .build();
            requestObserver.onNext(input); // Send each map request
        }

        // 5. Call stream.onCompleted() to finish and wait for the server's response.
        requestObserver.onCompleted();

        // 6. Close the gRPC channel after completion.
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);


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
        // 1. Open a gRPC channel to the Reduce server.
        System.out.println("Requesting Reduce task1");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, portNumber)
                .usePlaintext()
                .build();

        // 2. Create a blocking stub for reduce requests.
        System.out.println("Requesting Reduce task2");
        AssignJobGrpc.AssignJobBlockingStub stub = AssignJobGrpc.newBlockingStub(channel);

        // 3. Build and send the reduce request.
        System.out.println("Requesting Reduce task3");
        ReduceInput input = ReduceInput.newBuilder()
                .setIp(ip)
                .setPort(portNumber)
                .setInputfilepath(inputFilePath)
                .setOutputfilepath(outputFilePath)
                .build();

        // 4. Check the job status from the server's response.
        System.out.println("Requesting Reduce task4");
        ReduceOutput response = stub.reduce(input);
        int jobStatus = response.getJobstatus();

        // 5. Close the gRPC channel after completion.
        System.out.println("Requesting Reduce task5");
        channel.shutdown();

        // 6. Return the job status (e.g., 2 for success).
        System.out.println("Requesting Reduce task6");
        return jobStatus;
    }
}