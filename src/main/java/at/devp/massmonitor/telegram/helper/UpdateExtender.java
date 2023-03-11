package at.devp.massmonitor.telegram.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateExtender implements UpdateHelper {

  private final Update update;

  public boolean isCommandFromEditMessage() {
    return update.hasEditedMessage() && update.getEditedMessage().isCommand();
  }

  public boolean isCommandFromMessage() {
    return update.hasMessage() && update.getMessage().isCommand();
  }

  public boolean hasACommand() {
    return isCommandFromMessage() || isCommandFromEditMessage();
  }

  public MessageEntity getCommand() {
    if (update.hasMessage() && update.getMessage().isCommand()) {
      return update.getMessage().getEntities().stream().findFirst().orElseThrow();
    }
    if (update.hasEditedMessage() && update.getEditedMessage().isCommand()) {
      return update.getEditedMessage().getEntities().stream().findFirst().orElseThrow();
    }
    return null;
  }
}
