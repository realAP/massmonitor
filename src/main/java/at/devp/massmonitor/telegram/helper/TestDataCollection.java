package at.devp.massmonitor.telegram.helper;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TestDataCollection {
  void collect(final Update updateExtender);
}
