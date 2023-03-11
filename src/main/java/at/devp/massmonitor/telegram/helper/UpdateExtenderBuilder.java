package at.devp.massmonitor.telegram.helper;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateExtenderBuilder {
  public UpdateExtender createUpdateExtender(Update update) {
    return new UpdateExtender(update);
  }
}
