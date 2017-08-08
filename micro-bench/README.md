Performance Benchmarks using the JMH framework
==============================================

JMH: 
http://openjdk.java.net/projects/code-tools/jmh/
http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/

How to run benchmarks:
 * Run 'mvn clean install' in the root of this project.
 * Run 'java -jar target/benchmark.jar' to run the entire suite
 * Run 'java -jar target/benchmarks.jar CyTableUtilBenchmark' to run an individual benchmark.
 

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


July, 2017
----------

* Using HashMap for Boolean columns
Benchmark                                          (tableSize)   Mode  Cnt        Score       Error  Units
TableLookupBooleanBenchmark.testCountMatchingRows          100  thrpt   20  2025409.010 ± 50725.266  ops/s
TableLookupBooleanBenchmark.testCountMatchingRows         1000  thrpt   20   291869.869 ± 10291.130  ops/s
TableLookupBooleanBenchmark.testCountMatchingRows        10000  thrpt   20    29354.553 ±   847.842  ops/s
TableLookupBooleanBenchmark.testGetMatchingKeys            100  thrpt   20          N/A
TableLookupBooleanBenchmark.testGetMatchingKeys           1000  thrpt   20          N/A
TableLookupBooleanBenchmark.testGetMatchingKeys          10000  thrpt   20          N/A
TableLookupBooleanBenchmark.testGetMatchingRows            100  thrpt   20  1074227.469 ± 21895.245  ops/s
TableLookupBooleanBenchmark.testGetMatchingRows           1000  thrpt   20   138386.963 ±  1551.041  ops/s
TableLookupBooleanBenchmark.testGetMatchingRows          10000  thrpt   20    10529.893 ±   315.299  ops/s

Benchmark                             (networkSize)   Mode  Cnt       Score       Error  Units
CyTableUtilBenchmark.testCyTableUtil             10  thrpt   20  610416.914 ± 10934.607  ops/s
CyTableUtilBenchmark.testCyTableUtil            100  thrpt   20   76821.100 ±  1006.417  ops/s
CyTableUtilBenchmark.testCyTableUtil           1000  thrpt   20    7269.652 ±   136.187  ops/s
CyTableUtilBenchmark.testCyTableUtil          10000  thrpt   20     494.036 ±    12.927  ops/s



* Using BooleanColumnLookup for Boolean columns
Benchmark                                          (tableSize)   Mode  Cnt         Score         Error  Units
TableLookupBooleanBenchmark.testCountMatchingRows          100  thrpt   20  43891027.311 ± 1159081.455  ops/s
TableLookupBooleanBenchmark.testCountMatchingRows         1000  thrpt   20  44533124.898 ± 1167780.764  ops/s
TableLookupBooleanBenchmark.testCountMatchingRows        10000  thrpt   20  45376097.541 ± 1282724.263  ops/s
TableLookupBooleanBenchmark.testGetMatchingKeys            100  thrpt   20   4527807.108 ±  140998.318  ops/s
TableLookupBooleanBenchmark.testGetMatchingKeys           1000  thrpt   20    402984.178 ±    5057.445  ops/s
TableLookupBooleanBenchmark.testGetMatchingKeys          10000  thrpt   20     46489.317 ±     420.385  ops/s
TableLookupBooleanBenchmark.testGetMatchingRows            100  thrpt   20   1781023.101 ±   25517.564  ops/s
TableLookupBooleanBenchmark.testGetMatchingRows           1000  thrpt   20    184063.553 ±    2252.923  ops/s
TableLookupBooleanBenchmark.testGetMatchingRows          10000  thrpt   20     17350.350 ±     659.190  ops/s

Benchmark                             (networkSize)   Mode  Cnt        Score       Error  Units
CyTableUtilBenchmark.testCyTableUtil             10  thrpt   20  2022813.390 ± 20216.839  ops/s
CyTableUtilBenchmark.testCyTableUtil            100  thrpt   20   333365.172 ±  4346.523  ops/s
CyTableUtilBenchmark.testCyTableUtil           1000  thrpt   20    24064.681 ±   209.669  ops/s
CyTableUtilBenchmark.testCyTableUtil          10000  thrpt   20     3223.956 ±    65.209  ops/s