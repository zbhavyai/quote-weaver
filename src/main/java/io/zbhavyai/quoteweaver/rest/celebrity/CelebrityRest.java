package io.zbhavyai.quoteweaver.rest.celebrity;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.service.celebrity.CelebrityService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.List;

@Path("/v1/celebrity")
public class CelebrityRest {

  @Inject private CelebrityService _service;

  @GET
  @Path("/list")
  public Uni<List<String>> getCelebrityList() {
    return _service.getCelebrityList();
  }

  @GET
  @Path("/random")
  public Uni<String> getRandomCelebrity() {
    return _service.getRandomCelebrity();
  }
}
