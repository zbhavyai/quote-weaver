package io.zbhavyai.quoteweaver.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record GenerateQuoteReq(@JsonProperty("celebrity") String celebrity) {}
