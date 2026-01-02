package io.zbhavyai.quoteweaver.init;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.zbhavyai.quoteweaver.service.orchestrator.OrchestratorService;
import jakarta.inject.Inject;
import picocli.CommandLine;

@QuarkusMain
@CommandLine.Command
public class Main implements Runnable, QuarkusApplication {

  @Inject CommandLine.IFactory factory;

  @Inject OrchestratorService _service;

  @Override
  public void run() {
    _service.postQuote().subscribe().with(ignored -> {});
  }

  @Override
  public int run(String... args) throws Exception {
    return new CommandLine(this, factory).execute(args);
  }
}
