package io.zbhavyai.quoteweaver.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record Quote(
    @JsonProperty("quote") String quote, @JsonProperty("celebrity") String celebrity) {}
