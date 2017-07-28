Performance Benchmarks using the JMH framework
==============================================

JMH: 
http://openjdk.java.net/projects/code-tools/jmh/
http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/

How to run benchmarks:
 * Build 'mvn install' in the root of this project.
 * Run 'java -jar target/benchmark.jar'

Note: Its important to run the build step every time you make a change to cytoscape code.
The build step pulls all the cytoscape jars into benchmark.jar creating a single "uber" jar.
If you change cytoscape code you need to rebuild benchmark.jar or the changes won't get picked up.



Update History
==============


Mar 22, 2017
------------

Rewrote PartitionUtil.traverse() to be iterative instead of recursive. 
This was done to fix bug #3747 (a StackOverflowError caused by the recursion). 

Benchmark: PartitionBenchmark.testPartition()

Results:
Recursive traverse()
Benchmark                          Mode  Cnt    Score   Error  Units
PartitionBenchmark.testPartition  thrpt  200   75.085 ± 1.355  ops/s

Iterative traverse()
Benchmark                          Mode  Cnt    Score   Error  Units
PartitionBenchmark.testPartition  thrpt  200  482.088 ± 5.552  ops/s

Conclusion: Patitioning the graph using an iterative traverse() method
fixes the bug and is several times faster.


