package at.devp.massmonitor.telegram.helper;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;

@ConditionalOnProperty(value = "test.data.collector.enabled", havingValue = "true")
@Configuration
@RequiredArgsConstructor
public class TestDataCollector implements TestDataCollection {
  private final Gson gson;

  @Override
  public void collect(Update updateExtender) {
    System.out.println(gson.toJson(updateExtender, Update.class));
  }
}
