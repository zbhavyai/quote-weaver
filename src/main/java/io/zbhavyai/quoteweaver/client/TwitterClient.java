package io.zbhavyai.quoteweaver.client;

import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "twitter-api")
@Path("/1.1")
public interface TwitterClient {

  @POST
  @Path("/statuses/update.json")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  JsonObject tweet(
      @HeaderParam("Authorization") String authHeader, @FormParam("status") String textForTweet);
}
