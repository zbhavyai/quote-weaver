package io.zbhavyai.quoteweaver.service.twitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TwitterAuthServiceImpl implements TwitterAuthService {

  private static final Logger LOG = LoggerFactory.getLogger(TwitterAuthServiceImpl.class);

  private final String _apiKey;
  private final String _apiSecret;
  private final String _accessToken;
  private final String _accessTokenSecret;

  @Inject
  public TwitterAuthServiceImpl(
      @ConfigProperty(name = "twitter-api.api-key") String apiKey,
      @ConfigProperty(name = "twitter-api.api-key-secret") String apiSecret,
      @ConfigProperty(name = "twitter-api.access-token") String accessToken,
      @ConfigProperty(name = "twitter-api.access-token-secret") String accessTokenSecret) {

    _apiKey = apiKey;
    _apiSecret = apiSecret;
    _accessToken = accessToken;
    _accessTokenSecret = accessTokenSecret;
  }

  public String generateAuthHeader(String tweetText) {
    String method = "POST";
    String baseUrl = "https://api.x.com/1.1/statuses/update.json";
    String nonce =
        new SecureRandom()
            .ints(32, 'a', 'z' + 1)
            .mapToObj(i -> String.valueOf((char) i))
            .collect(Collectors.joining());
    String timestamp = String.valueOf(Instant.now().getEpochSecond());

    Map<String, String> oauthParams =
        Map.of(
            "oauth_consumer_key", _apiKey,
            "oauth_nonce", nonce,
            "oauth_signature_method", "HMAC-SHA1",
            "oauth_timestamp", timestamp,
            "oauth_token", _accessToken,
            "oauth_version", "1.0");

    // Form body param
    String encodedStatus = encode(tweetText);

    // Collect everything for signature base string
    var allParams = new java.util.TreeMap<String, String>(oauthParams);
    allParams.put("status", tweetText); // Not encoded yet

    String paramString =
        allParams.entrySet().stream()
            .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
            .collect(Collectors.joining("&"));

    String signatureBase = method + "&" + encode(baseUrl) + "&" + encode(paramString);
    String signingKey = encode(_apiSecret) + "&" + encode(_accessTokenSecret);

    String signature = hmacSha1(signatureBase, signingKey);

    // Build OAuth header
    StringJoiner header = new StringJoiner(", ", "OAuth ", "");
    for (var entry : oauthParams.entrySet()) {
      header.add(encode(entry.getKey()) + "=\"" + encode(entry.getValue()) + "\"");
    }
    header.add("oauth_signature=\"" + encode(signature) + "\"");

    return header.toString();
  }

  private String hmacSha1(String data, String key) {
    try {
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
      byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(rawHmac);
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate HMAC : " + e.getMessage(), e);
    }
  }

  private String encode(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8)
        .replace("+", "%20")
        .replace("*", "%2A")
        .replace("%7E", "~");
  }
}
