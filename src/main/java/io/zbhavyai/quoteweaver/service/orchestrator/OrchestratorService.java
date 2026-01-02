package io.zbhavyai.quoteweaver.service.orchestrator;

import io.smallrye.mutiny.Uni;

public interface OrchestratorService {

  Uni<Void> postQuote();
}
