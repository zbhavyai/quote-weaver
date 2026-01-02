package io.zbhavyai.quoteweaver.service.twitter;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

public interface TweetService {

  Uni<JsonObject> tweet(String tweetText);

  String extractTweetId(JsonObject response);
}
