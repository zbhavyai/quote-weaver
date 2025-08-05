package io.zbhavyai.quoteweaver.service.orchestrator;

import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.zbhavyai.quoteweaver.dto.quote.Quote;
import io.zbhavyai.quoteweaver.service.celebrity.CelebrityService;
import io.zbhavyai.quoteweaver.service.quote.QuoteGenerationService;
import io.zbhavyai.quoteweaver.service.twitter.TweetService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class OrchestratorServiceImpl implements OrchestratorService {

  private static final Logger LOG = LoggerFactory.getLogger(OrchestratorServiceImpl.class);

  private static final String HASHTAGS =
      "#quoteweaver #project #rhymingquotes #technology #programming";

  @Inject private CelebrityService _celebrityService;
  @Inject private QuoteGenerationService _quoteService;
  @Inject private TweetService _tweetService;

  @Override
  public void postQuote() {
    LOG.info("postQuote");

    _celebrityService
        .getRandomCelebrity()
        .onItem()
        .invoke(celebrity -> LOG.info("Selected celebrity: {}", celebrity))
        .chain(celebrity -> _quoteService.generateQuote(celebrity))
        .onItem()
        .invoke(quote -> LOG.info("Generated quote: {}", quote.quote()))
        .onItem()
        .transform(this::transformQuoteForTweet)
        .chain(text -> _tweetService.tweet(text))
        .runSubscriptionOn(Infrastructure.getDefaultExecutor())
        .await()
        .indefinitely();
  }

  private String transformQuoteForTweet(Quote quotation) {
    String quote = quotation.quote();
    String author = quotation.celebrity();

    if (quote.length() > 200) {
      return quote.substring(0, 200) + "...";
    }

    quote += "\n\n- as if by " + author;
    quote += "\n\n" + HASHTAGS;
    return quote;
  }
}
