package io.zbhavyai.quoteweaver.service.quote;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.zbhavyai.quoteweaver.ai.QuoteGenerator;
import io.zbhavyai.quoteweaver.dto.quote.GenerateQuoteReq;
import io.zbhavyai.quoteweaver.dto.quote.Quote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuoteGenerationServiceImpl implements QuoteGenerationService {

  private static final Logger LOG = LoggerFactory.getLogger(QuoteGenerationServiceImpl.class);

  @Inject QuoteGenerator _quoteGenerator;

  @Override
  public Uni<Quote> generateQuote(GenerateQuoteReq req) {
    LOG.info("generateQuote: topic={} figure={}", req.topic(), req.figure());

    return Uni.createFrom()
        .item(() -> _quoteGenerator.generateQuote(req.topic(), req.figure()))
        .map(res -> new Quote(req.topic(), req.figure(), res.quote()))
        .runSubscriptionOn(Infrastructure.getDefaultExecutor());
  }
}
