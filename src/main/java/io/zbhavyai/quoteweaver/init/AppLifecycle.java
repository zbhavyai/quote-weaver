package io.zbhavyai.quoteweaver.init;

import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycle {

  private static final Logger LOG = LoggerFactory.getLogger(AppLifecycle.class);

  @Startup
  void start() {
    logMessage("Starting application");
  }

  @Shutdown
  void shutdown() {
    logMessage("Shutting down application");
  }

  private void logMessage(String message) {
    LOG.info("--------------------------------------------------------------------------------");
    LOG.info(message);
    LOG.info("--------------------------------------------------------------------------------");
  }
}
