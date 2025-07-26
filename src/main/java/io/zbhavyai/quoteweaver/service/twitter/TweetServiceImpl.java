package io.zbhavyai.quoteweaver.service.twitter;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.core.json.JsonObject;
import io.zbhavyai.quoteweaver.client.TwitterClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TweetServiceImpl implements TweetService {

  private static final Logger LOG = LoggerFactory.getLogger(TweetServiceImpl.class);

  @Inject TwitterAuthServiceImpl _authService;

  @Inject @RestClient TwitterClient _twitterClient;

  @Override
  public Uni<JsonObject> postTweet(String tweetText) {
    LOG.info("postTweet: tweetText={}", tweetText);

    String authHeader = _authService.generateAuthHeader(tweetText);

    return Uni.createFrom()
        .item(() -> _twitterClient.tweet(authHeader, tweetText))
        .runSubscriptionOn(Infrastructure.getDefaultExecutor())
        .onItem()
        .invoke(x -> LOG.info("twitter response = {}", x.encodePrettily()));
  }
}
