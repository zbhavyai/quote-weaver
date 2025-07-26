package io.zbhavyai.quoteweaver.rest.ping;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.rest.utils.ResponseUtils;
import io.zbhavyai.quoteweaver.service.ping.PingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ping")
public class PingRest {

  private final PingService _service;

  @Inject
  public PingRest(PingService service) {
    _service = service;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<Response> greet() {
    return _service.ping().onItem().transform(ResponseUtils::handleSuccess);
  }
}
