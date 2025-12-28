package io.zbhavyai.quoteweaver.rest.celebrity;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.rest.utils.ResponseUtils;
import io.zbhavyai.quoteweaver.service.celebrity.CelebrityService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/v1/celebrity")
public class CelebrityRest {

  @Inject private CelebrityService _service;

  @GET
  @Path("/list")
  public Uni<Response> getCelebrityList() {
    return _service.getCelebrityList().onItem().transform(ResponseUtils::handleSuccess);
  }

  @GET
  @Path("/random")
  public Uni<Response> getRandomCelebrity() {
    return _service.getRandomCelebrity().onItem().transform(ResponseUtils::handleSuccess);
  }
}
