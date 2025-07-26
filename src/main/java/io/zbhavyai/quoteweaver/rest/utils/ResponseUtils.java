package io.zbhavyai.quoteweaver.rest.utils;

import jakarta.inject.Singleton;
import jakarta.ws.rs.core.Response;

@Singleton
public class ResponseUtils {

  public static Response handleSuccess(Object obj) {
    return Response.ok().entity(obj).build();
  }
}
