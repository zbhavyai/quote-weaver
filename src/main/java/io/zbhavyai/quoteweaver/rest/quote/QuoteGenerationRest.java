package io.zbhavyai.quoteweaver.rest.quote;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.dto.quote.GenerateQuoteReq;
import io.zbhavyai.quoteweaver.rest.utils.ResponseUtils;
import io.zbhavyai.quoteweaver.service.quote.QuoteGenerationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/quote")
public class QuoteGenerationRest {

  @Inject private QuoteGenerationService _service;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> generateQuote(GenerateQuoteReq quoteReq) {
    return _service
        .generateQuote(quoteReq.celebrity())
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }
}
