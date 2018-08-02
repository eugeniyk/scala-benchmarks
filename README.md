# scala-benchmarks
Some scala / java JVM benchmarks

## How to run particular test
```bash
sbt "jmh:run -i 10 -wi 3 -f1 -t1 LazyTest"  //  1 thread
sbt "jmh:run -i 10 -wi 3 -f1 -t4 LazyTest"  //  4 thread
```