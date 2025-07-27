package io.zbhavyai.quoteweaver.service.celebrity;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CelebrityServiceImpl implements CelebrityService {

  private static final Logger LOG = LoggerFactory.getLogger(CelebrityServiceImpl.class);

  private final List<String> _celebrityNames;
  private final ObjectMapper _objectMapper;

  @Inject
  public CelebrityServiceImpl(ObjectMapper objectMapper) {
    _celebrityNames = new ArrayList<>();
    _objectMapper = objectMapper;
  }

  @Startup
  void loadCelebrities() {
    LOG.debug("loadCelebrities");

    try (InputStream is = getClass().getClassLoader().getResourceAsStream("celebrities.json")) {
      if (is == null) {
        throw new IllegalStateException("Celebrity list not found under resources!");
      }

      this._celebrityNames.addAll(
          _objectMapper.readValue(
              is,
              _objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)));

      LOG.info("loadCelebrities: {} celebrities names loaded.", _celebrityNames.size());
    } catch (Exception e) {
      LOG.error("Failed to load celebrity list: {}", e.getMessage());
      throw new RuntimeException("Error loading celebrities", e);
    }
  }

  public List<String> getCelebrityList() {
    return _celebrityNames;
  }

  public String getRandomCelebrity() {
    if (_celebrityNames.isEmpty()) {
      throw new IllegalStateException("Celebrity list is empty");
    }

    return _celebrityNames.get(ThreadLocalRandom.current().nextInt(_celebrityNames.size()));
  }
}
