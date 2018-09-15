# twitterGraphLoad
Loading 2010 Twitter Dataset to DSE Graph for Page Rank


## To Run: 

Create the schema in the resources directory 

```sh
$ cd twitterGraphLoad/
$ mvn clean install
```

```sh
$ cd twitterGraphLoad/target
$ dse spark-submit --executor-memory=<SET MEM HERE> --driver-memory=<SET MEM HERE> --class com.twitterdata.pagerank.App dataLoad-1.0-SNAPSHOT.jar 
```

Example: 

```sh
$ cd twitterGraphLoad/target
$ dse spark-submit --executor-memory=20G --driver-memory=4G --class com.twitterdata.pagerank.App dataLoad-1.0-SNAPSHOT.jar 
```


## For more information on inserting data using DSE GraphFrames:

1. [DSE Graph Frames Docs]

2. [Detailed Code Example]

[DSE Graph Frames Docs]: <https://www.datastax.com/dev/blog/dse-graph-frame>

[Detailed Code Example]: <https://github.com/pmehra7/dseGraphFrameLoad/>
