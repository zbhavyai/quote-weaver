package io.zbhavyai.quoteweaver.service.quote;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.dto.quote.Quote;

public interface QuoteGenerationService {

  Uni<Quote> generateQuote(String celebrity);
}
