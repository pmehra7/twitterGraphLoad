system.graph('Twitter_Graph').ifNotExists().create()
:remote config alias g Twitter_Graph.g

schema.propertyKey("node_id").Text().ifNotExists().create()
schema.propertyKey("twitter_id").Int().ifNotExists().create()

schema.vertexLabel("TwitterProfile").
      partitionKey("node_id").
      properties(
        "twitter_id"
).ifNotExists().create()

schema.edgeLabel("follows").
    connection("TwitterProfile", "TwitterProfile").
    ifNotExists().create()