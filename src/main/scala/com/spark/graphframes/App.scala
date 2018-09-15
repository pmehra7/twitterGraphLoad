package com.twitterdata.pagerank

import com.datastax.bdp.graph.spark.graphframe._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object App {
  def main(args: Array[String]):Unit = {

    val graphName = "Twitter_Graph"

    val spark = SparkSession
      .builder
      .appName("Graph Load Application")
      .enableHiveSupport()
      .getOrCreate()

    val g = spark.dseGraph(graphName)

    def vertexSchema():StructType = {
      StructType(Array(
        StructField("node_id", StringType, true),
        StructField("twitter_id", IntegerType,true)))
    }

    def edgeSchema():StructType = {
      StructType(Array(
        StructField("node_id_in", StringType, true),
        StructField("node_id_from",StringType,true)))
    }

    val vertex_data = spark.sqlContext.read.format("csv").option("header", "true").schema(vertexSchema).load("dsefs:///data/twitter-2010-ids.csv")

    val edge_data = spark.sqlContext.read.format("csv").option("header", "false").option("delimiter", " ").schema(edgeSchema).load("dsefs:///data/twitter-2010.txt")

    val twitter_followers = vertex_data.select(col("node_id"), col("twitter_id")).withColumn("~label", lit("TwitterProfile"))

    println("\nWriting Twitter Profile Vertices")
    g.updateVertices(twitter_followers)

    val edge_data_withLabels = edge_data.withColumn("srcLabel", lit("TwitterProfile")).withColumn("dstLabel", lit("TwitterProfile")).withColumn("edgeLabel", lit("follows"))

    val twitter_edges = edge_data_withLabels.select(g.idColumn(col("srcLabel"), col("node_id_from")) as "src", g.idColumn(col("dstLabel"), col("node_id_in")) as "dst", col("edgeLabel") as "~label")

    println("\nWriting Twitter Follower Edges")
    g.updateEdges(twitter_edges)


  }
}
