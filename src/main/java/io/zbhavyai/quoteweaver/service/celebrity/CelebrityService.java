package io.zbhavyai.quoteweaver.service.celebrity;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CelebrityService {

  Uni<List<String>> getCelebrityList();

  Uni<String> getRandomCelebrity();
}
