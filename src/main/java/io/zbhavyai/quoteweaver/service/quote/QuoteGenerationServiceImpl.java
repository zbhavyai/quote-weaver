package io.zbhavyai.quoteweaver.service.quote;

import dev.langchain4j.data.image.Image;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.core.json.JsonObject;
import io.zbhavyai.quoteweaver.ai.QuoteGenerator;
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
  public Uni<Quote> generateQuote(String celebrity) {
    LOG.info("generateQuote: {}", celebrity);

    return Uni.createFrom()
        .item(() -> _quoteGenerator.generateQuote(celebrity))
        .runSubscriptionOn(Infrastructure.getDefaultExecutor())
        .onItem()
        .invoke(
            quote -> LOG.info("generated quote: \n{}", JsonObject.mapFrom(quote).encodePrettily()))
        .map(quote -> new Quote(quote.quote(), celebrity));
  }

  @Override
  public Uni<String> generateImage(String quoteText) {
    LOG.info("generateImage: {}", quoteText);

    return Uni.createFrom()
        .item(() -> _quoteGenerator.generateImage(quoteText))
        .onItem()
        .transform(Image::base64Data)
        .runSubscriptionOn(Infrastructure.getDefaultExecutor());
  }
}
