package io.zbhavyai.quoteweaver.dto.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record PostTweetReq(@JsonProperty("text") String textForTweet) {}
