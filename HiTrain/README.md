# HiTrain

HiTrain enhances the Java Executor framework to process High Transaction requests.  The transactions are received on an UDP port and uses Threads from a Configurable ThreadPool to process each request. A Monitoring thread displays Thread statistics at regular intervals.

# Features!

  - Multi-Threaded application
  - Can provide high transaction rates, tested in excess of 10K per second
  - Number of threads can be configured along with few other parameters
  - Test Data Generators using Python can be found in the Python repository

HiTrain has been developed using the following tools/apps:

* [Java] - Core Java 
* [Log4j2] - Logging utility by Apache.
* [IntelliJ] - IDE
* [Python] - Test data generators were developed using Python

And of course HiTrain itself is open source on on GitHub.

### Installation

Use Maven to build HiTrain.  the pom file can be found along with the source.

