# Map Reduce and gRPC

The repository contains a gradle applications project template for completing task 2 and 3 for Assignment 1.

> **_NOTE:_**
> After loading the project depending on which IDE (if using), you might see some file not found errors. However, they will disappear after you have successfully built the project using following command from the task folder.

## Project Structure

```bash
├── Readme.md 
├── Report.md
└── Tasks
    ├── app
    │   ├── build.gradle # Gradle build script for the app module
    │   ├── input
    │   │   ├── pigs.txt # main input file for the task
    │   │   └── temp
    │   │       ├── chunk001.txt - chunk012.txt # split chunks of the pigs.txt input file
    │   ├── output
    │   │   └── output.txt # solution output after processing the input
    │   └── src
    │       └── main
    │       │   ├── java
    │       │   │   └── io
    │       │   │       └── grpc
    │       │   │           └── filesystem
    │       │   │               ├── task2
    │       │   │               │   ├── Mapper.java # map object class
    │       │   │               │   └── MapReduce.java # map and reduce logic for input processing
    │       │   │               ├── task3
    │       │   │               │   ├── MrClient.java # client to request map and reduce tasks
    │       │   │               │   ├── MrMapServer.java # server to handle map tasks
    │       │   │               │   └── MrReduceServer.java # server to handle reduce tasks
    │       │   │               └── test
    │       │   │                   └── CheckOutput.java # test to validate output against expected results
    │       │   └── proto
    │       │       └── communicate.proto # gRPC protocol definition for communication
    │       └── test
    │           └── java
    │               └── io
    │                   └── grpc
    │                       └── filesystem
    │                           └── task2
    │                               └── MapReduceTest.java
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar # Gradle wrapper JAR for version management
    │       └── gradle-wrapper.properties # properties file for the Gradle wrapper
    ├── gradlew # Unix shell script to run Gradle wrapper
    ├── gradlew.bat # Windows batch script to run Gradle wrapper
    └── settings.gradle # settings for Gradle project structure
```

## Running Instructions

### Task 2

We have provided tests in `MapReduceTest.java`, which give you guidance on how to implement the functions. 

**Note: The tests are expected to fail at first.** They should work with your code additions.

In the terminal, navigate to the `Tasks` folder using `cd`. Then, build and run the program:

#### MacOS / Linux

To run:

```bash
./gradlew build
./gradlew run -PchooseMain=io.grpc.filesystem.task2.MapReduce --args="input/pigs.txt output/output-task2.txt"
```

To test:

```bash
./gradlew test
```

Check your output:

```bash
./gradlew run -PchooseMain=io.grpc.filesystem.test.CheckOutput --args="output/output-task2.txt"
```

#### Windows

To run:

```bash
.\gradlew build
.\gradlew run -PchooseMain="io.grpc.filesystem.task2.MapReduce" --args="input/pigs.txt output/output-task2.txt"
```

To test:

```bash
.\gradlew test
```

Check your output:

```bash
.\gradlew run -PchooseMain="io.grpc.filesystem.test.CheckOutput" --args="output/output-task2.txt"
```

### Task 3

In the terminal, navigate to the `task3` directory, then, execute the following commands to run the client and servers.

#### MacOS / Linux

```bash
./gradlew build
```

After a successful build, you are ready to start communication using gRPC through the following commands:

Map Server:

```bash
./gradlew run -PchooseMain=io.grpc.filesystem.task3.MrMapServer --args="50551"
```

Reduce Server:

```bash
./gradlew run -PchooseMain=io.grpc.filesystem.task3.MrReduceServer --args="50552"
```

Client:

```bash
./gradlew run -PchooseMain=io.grpc.filesystem.task3.MrClient --args="127.0.0.1 50551 50552 input/pigs.txt output/output-task3.txt"
```

Check your output:

```bash
./gradlew run -PchooseMain=io.grpc.filesystem.test.CheckOutput --args="output/output-task3.txt"
```

#### Windows

```bash
.\gradlew build
```

Map Server:

```bash
.\gradlew run run -PchooseMain="io.grpc.filesystem.task3.MrMapServer"" --args="50551"
```

Reduce Server:

```bash
.\gradlew run -PchooseMain="io.grpc.filesystem.task3.MrReduceServer" --args="50552"
```

Client:

```bash
.\gradlew run -PchooseMain="io.grpc.filesystem.task3.MrClient" --args="127.0.0.1 50551 50552 input/pigs.txt output/output-task3.txt"
```

Check your output:

```bash
.\gradlew run -PchooseMain="io.grpc.filesystem.test.CheckOutput" --args="output/output-task3.txt"
```
