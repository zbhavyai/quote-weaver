package io.zbhavyai.quoteweaver.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record GenerateQuoteRes(@JsonProperty("quote") String quote) {}
