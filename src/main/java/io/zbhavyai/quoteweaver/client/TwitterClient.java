package io.zbhavyai.quoteweaver.client;

import io.vertx.core.json.JsonObject;
import io.zbhavyai.quoteweaver.dto.twitter.PostTweetReq;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "twitter-api")
@Path("/2")
public interface TwitterClient {

  @POST
  @Path("/tweets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  JsonObject tweet(@HeaderParam("Authorization") String authHeader, PostTweetReq tweetReq);
}
