package io.zbhavyai.quoteweaver.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.langchain4j.model.output.structured.Description;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record GenerateQuoteRes(@JsonProperty("quote") @Description("quote") String quote) {}
