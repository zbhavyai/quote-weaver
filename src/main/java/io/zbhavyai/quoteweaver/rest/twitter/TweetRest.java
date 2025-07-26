package io.zbhavyai.quoteweaver.rest.twitter;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.rest.utils.ResponseUtils;
import io.zbhavyai.quoteweaver.service.twitter.TweetServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/tweet")
public class TweetRest {

  @Inject private TweetServiceImpl _service;

  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> tweet(String textForTweet) {
    return _service.postTweet(textForTweet).onItem().transform(ResponseUtils::handleSuccess);
  }
}
