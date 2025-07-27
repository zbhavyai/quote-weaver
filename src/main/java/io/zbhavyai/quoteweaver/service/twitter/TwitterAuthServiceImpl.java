package io.zbhavyai.quoteweaver.service.twitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
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

  @Override
  public String generateOAuth1Header(String method, String url) {
    LOG.info("generateOAuth1Header: method={}, url={}", method, url);

    String nonce = UUID.randomUUID().toString().replaceAll("-", "");
    String timestamp = String.valueOf(Instant.now().getEpochSecond());

    Map<String, String> oauthParams = new TreeMap<>();
    oauthParams.put("oauth_consumer_key", _apiKey);
    oauthParams.put("oauth_nonce", nonce);
    oauthParams.put("oauth_signature_method", "HMAC-SHA1");
    oauthParams.put("oauth_timestamp", timestamp);
    oauthParams.put("oauth_token", _accessToken);
    oauthParams.put("oauth_version", "1.0");

    // Step 1: Create Signature Base String
    String paramString =
        oauthParams.entrySet().stream()
            .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
            .collect(Collectors.joining("&"));

    String signatureBaseString =
        method.toUpperCase() + "&" + encode(url) + "&" + encode(paramString);

    // Step 2: Create Signing Key
    String signingKey = encode(_apiSecret) + "&" + encode(_accessTokenSecret);

    // Step 3: Calculate HMAC-SHA1
    String signature = hmacSha1(signatureBaseString, signingKey);
    oauthParams.put("oauth_signature", signature);

    // Step 4: Build Authorization header
    return "OAuth "
        + oauthParams.entrySet().stream()
            .map(e -> encode(e.getKey()) + "=\"" + encode(e.getValue()) + "\"")
            .collect(Collectors.joining(", "));
  }

  private String hmacSha1(String data, String key) {
    try {
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
      byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(rawHmac);
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate HMAC: " + e.getMessage(), e);
    }
  }

  private String encode(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8)
        .replace("+", "%20")
        .replace("*", "%2A")
        .replace("%7E", "~");
  }
}
