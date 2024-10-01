# Assignment 1

# Team Members
- Yanik Almer
- Gilles Harder

# GitHub link to your (forked) repository

https://github.com/gil05les/Assignment1

# Task 4
#### 1. (0.2pt) How does RPC differ from a local procedure call? How does it allow for horizontal scaling?

*A local procedure call handles communication with exchanging arguments and results
between a Server and a Client. A RPC(remote procedure call) has some things in between. The 
client exchanges arguments and results with a Client Stub which requests messages  through the 
Network to the Server Stub, this again sends the received arguments to the Server and receives
the results. Through this steps we can outsource the workload to multiple servers, we did this 
in task 3, we just needed to set up 3 terminals by editing the ports we could also do the program 
on three different devices.*
--------------------
#### 2. (0.2pt) What are Interface Definition Languages (IDL) used for? Name and explain the IDL you use for Task 3.
*Interface Definition Languages are used to define the interface which enables different software 
components to communicate with each other. The IDL specifies all the different data types, fuction,
methods and parameters which are implemented by developers in their respective programming languages.
The IDL used for Task 3 is Protocol Buffers. Protocol Buffers (protobuf) is a language-neutral, 
platform-neutral, extensible mechanism for serializing structured data developed by google. Protobuf 
is used in many modern distributed Systems for defining the structure of the data for the use of gRPC.*

---------------------
#### 3. (0.2pt) Data locality7 is often discussed as a way to improve MapReduce performance. Explain what data locality is and how it affects efficiency.
*Data locality is the concept of moving the computation of data closer to where the data is stored. 
This eliminates the need to transfer large amounts of data across the network to a computation node. 
In the context of MapReduce, datalocality is imporantant because the MapReduce job are distributed 
between a cluster of different nodes. Where data is partitioned and stored across these nodes. When 
a Mapreduce Job is exicuted the performance of this job can be imporved significantly if the data
needed for the job is stored at the same node. This is becuase of Reduced network transfers which 
can be the bottleneck in many cases. Also the Computer cluster can use it's bandwidth more 
efficiently because the overall load of the network is reduced. Computing the job on the same node 
which stores the data also improves latency which leads to a faster job completion.*

--------------------
#### 4. (0.4pt) Describe, in your own words, how MapReduce works. Do you agree that MapReduce may have latency issues? What could be the cause of this? Is this programming model suitable for implementing iterative algorithms (e.g., machine learning or data analysis applications)? Please explain your argument.
*Our implementation of MapReduce divides the given input (text about pigs) into little chunks (multiple 
lines) these chunks are added to the chunk-files. Theses chunks will then be processed seperately. So 
every word that is in the line will be placed into a chunk file and the words are seperated and each 
one gets a 1 next to it. (i.e. if there are multiple the's in one line they won't be added to each 
other they are handled differently) Then these word counts of these map-chunk-files are collected in 
the function collectWordCounts and in the end the mapped wordCounts are reduced and stored in a new 
output file.MapReduce is not ideal for ML, or data analysis tasks because it isn't good in in-memory 
processing Some results are always saved to a disk or a comparable device and the reloaded from it. 
This is way to slow for iterative algorithms and develops a big latency. Moreover, MapReduce lacks 
native support for iterative operations, so each iteration would necessitate a separate MapReduce job,
resulting in additional overhead.*

For this assignment we used Co-Pilot for coding, ChatGPT for better understanding of the different 
classes and Google for RPC explanation
