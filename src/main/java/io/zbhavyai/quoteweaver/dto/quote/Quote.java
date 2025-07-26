package io.zbhavyai.quoteweaver.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record Quote(
    @JsonProperty("topic") String topic,
    @JsonProperty("figure") String figure,
    @JsonProperty("quote") String quote) {}
