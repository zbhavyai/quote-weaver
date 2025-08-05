package io.zbhavyai.quoteweaver.service.twitter;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.zbhavyai.quoteweaver.client.TwitterClient;
import io.zbhavyai.quoteweaver.dto.twitter.PostTweetReq;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TweetServiceImpl implements TweetService {

  private static final Logger LOG = LoggerFactory.getLogger(TweetServiceImpl.class);

  private final TwitterAuthService _authService;
  private final TwitterClient _twitterClient;
  private final String _twitterBaseURL;

  @Inject
  public TweetServiceImpl(
      TwitterAuthService authService,
      @RestClient TwitterClient twitterClient,
      @ConfigProperty(name = "quarkus.rest-client.twitter.url") String twitterBaseURL) {
    _authService = authService;
    _twitterClient = twitterClient;
    _twitterBaseURL = twitterBaseURL;
  }

  @Override
  public Uni<JsonObject> tweet(String tweetText) {
    LOG.debug("tweet: text={}", tweetText);

    String endpoint = _twitterBaseURL + "/2/tweets";
    String authHeader = _authService.generateOAuth1Header(HttpMethod.POST, endpoint);

    return _twitterClient
        .tweet(authHeader, new PostTweetReq(tweetText))
        .onItem()
        .invoke(x -> LOG.debug("Twitter API response = {}", x.encodePrettily()));
  }
}
