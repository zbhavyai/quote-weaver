package io.zbhavyai.quoteweaver.service.twitter;

public interface TwitterAuthService {

  public String generateOAuth1Header(String method, String url);
}
