package io.zbhavyai.quoteweaver.client;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "gemini")
@Path("/v1beta")
public interface GeminiImageClient {

  @POST
  @Path("/models/{modelId}:generateContent")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> generateImage(@PathParam("modelId") String modelId, JsonObject payload);
}
