package io.zbhavyai.quoteweaver.service.ping;

import io.smallrye.mutiny.Uni;

public interface PingService {

  Uni<String> ping();
}
