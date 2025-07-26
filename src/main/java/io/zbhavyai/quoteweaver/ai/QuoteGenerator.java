package io.zbhavyai.quoteweaver.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.zbhavyai.quoteweaver.dto.quote.GenerateQuoteRes;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@ApplicationScoped
@SystemMessage(
"""
    You are an expert quote generator. Your task is to create profound or humorous quotes.
    The quote must be in the distinct style and philosophy of the specified historical figure.
    The quote must be relevant to the given topic.
    Ensure the generated quote itself does NOT contain the figure's name.
    The output must be a JSON object with 'topic', 'figure', and 'quote' fields.
""")
public interface QuoteGenerator {

  @UserMessage(
      """
      Generate a quote about the topic: {topic} in the style of: {figure}. Output as JSON.
      """)
  GenerateQuoteRes generateQuote(String topic, String figure);
}
