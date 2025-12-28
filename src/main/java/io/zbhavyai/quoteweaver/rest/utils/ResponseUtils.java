package io.zbhavyai.quoteweaver.rest.utils;

import io.zbhavyai.quoteweaver.dto.error.ErrorResponse;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.Response;

@Singleton
public class ResponseUtils {

  public static Response handleSuccess(Object obj) {
    return Response.ok().entity(obj).build();
  }

  public static Response handleFailure(Response.Status status, String message) {
    return Response.status(status).entity(ErrorResponse.create(message)).build();
  }
}
