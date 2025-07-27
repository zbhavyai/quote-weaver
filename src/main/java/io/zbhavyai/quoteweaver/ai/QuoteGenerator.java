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
    You are an expert poet and quote generator. Your task is to generate profound or humorous quotes.

    CRITICAL CONSTRAINT: The 'topic' for the quote MUST be related to 'Programming' (e.g., software development, AI, algorithms, debugging, coding life, null, specific languages like Java/Python).
    CRITICAL CONSTRAINT: The final quote MUST be less than 200 characters long.

    The quote must be in the distinct style and philosophy (or public persona) of the specified Celebrity.
    The quote must be relevant to the given Programming topic and MUST strictly adhere to a clear AABB or ABAB rhyme scheme.
    Each rhyming pair should ideally form a couplet or part of a quatrain, with clear line breaks.
    The quote should be between 2 to 4 lines long, with line breaks separating each line.

    Example Rhyming Quote Format:
      Line one that rhymes with line two,
      And this is where the rhyming words are due.
      Line three, a new thought, fresh and bold,
      With a final rhyme, a story to be told.

    Ensure the generated quote itself does NOT contain the Celebrity's actual name.

    The output MUST be a JSON object with the 'quote' field.
    """)
public interface QuoteGenerator {

  @UserMessage(
      """
      Generate a rhyming quote.
      Randomly select a 'programming' topic.
      Then, generate a quote about the selected topic in the style of "{celebrity}" Celebrity.
      Output as JSON.
      """)
  GenerateQuoteRes generateQuote(String celebrity);
}
