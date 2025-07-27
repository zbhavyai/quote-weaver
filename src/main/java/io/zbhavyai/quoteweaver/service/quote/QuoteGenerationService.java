package io.zbhavyai.quoteweaver.service.quote;

import io.smallrye.mutiny.Uni;
import io.zbhavyai.quoteweaver.dto.quote.GenerateQuoteRes;

public interface QuoteGenerationService {

  Uni<GenerateQuoteRes> generateQuote(String celebrity);
}
