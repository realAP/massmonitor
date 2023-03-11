package at.devp.massmonitor.telegram.helper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;

@ConditionalOnProperty(value = "test.data.collector.enabled", havingValue = "false")
@Configuration
public class TestDataCollectorDummy implements TestDataCollection {

  @Override
  public void collect(Update updateExtender) {
    // do nothing
  }
}
