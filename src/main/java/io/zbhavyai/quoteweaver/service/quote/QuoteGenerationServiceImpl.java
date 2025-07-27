package io.zbhavyai.quoteweaver.service.quote;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.core.json.JsonObject;
import io.zbhavyai.quoteweaver.ai.QuoteGenerator;
import io.zbhavyai.quoteweaver.dto.quote.GenerateQuoteRes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuoteGenerationServiceImpl implements QuoteGenerationService {

  private static final Logger LOG = LoggerFactory.getLogger(QuoteGenerationServiceImpl.class);

  @Inject QuoteGenerator _quoteGenerator;

  @Override
  public Uni<GenerateQuoteRes> generateQuote(String celebrity) {
    LOG.info("generateQuote");

    return Uni.createFrom()
        .item(() -> _quoteGenerator.generateQuote(celebrity))
        .runSubscriptionOn(Infrastructure.getDefaultExecutor())
        .onItem()
        .invoke(
            quote -> LOG.info("Generated Quote: {}", JsonObject.mapFrom(quote).encodePrettily()));
  }
}
