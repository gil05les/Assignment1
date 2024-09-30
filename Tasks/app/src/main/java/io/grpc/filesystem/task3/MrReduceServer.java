/*
 * gRPC server node to accept calls from the clients and serve based on the method that has been requested
 */

package io.grpc.filesystem.task3;

import com.task3.proto.AssignJobGrpc;
import com.task3.proto.ReduceInput;
import com.task3.proto.ReduceOutput;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.filesystem.task2.MapReduce;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class MrReduceServer {

    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        final MrReduceServer mrServer = new MrReduceServer();
        for (String i : args) {
            mrServer.start(Integer.parseInt(i));
        }
        mrServer.server.awaitTermination();
    }

    private void start(int port) throws IOException {
        System.out.println("Starting server at port: " + port);
        server = ServerBuilder.forPort(port).addService(new MrReduceServerImpl()).build().start();
        System.out.println("Listening on: " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Terminating the server at port: " + port);
            try {
                server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
    }

    static class MrReduceServerImpl extends AssignJobGrpc.AssignJobImplBase {

        @Override
        public void reduce(ReduceInput request, StreamObserver<ReduceOutput> responseObserver) {
            System.out.println("Reduce request received");
            String mapDirPath = request.getInputfilepath();
            String outputFilePath = request.getOutputfilepath();
            try {
                // Call the existing MapReduce.reduce function to perform the reduce operation
                MapReduce.reduce(mapDirPath, outputFilePath);
                // Send success response
                responseObserver.onNext(ReduceOutput.newBuilder().setJobstatus(2).build());
            } catch (IOException e) {
                // Handle error and send failure response
                System.err.println("Error reducing files: " + e.getMessage());
                responseObserver.onNext(ReduceOutput.newBuilder().setJobstatus(1).build());
            }
            System.out.println("Reduce request processed succesfully!\n");

            responseObserver.onCompleted(); // Finish the stream
        }
    }
}