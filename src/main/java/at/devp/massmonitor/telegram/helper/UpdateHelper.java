package at.devp.massmonitor.telegram.helper;

import org.telegram.telegrambots.meta.api.objects.MessageEntity;

public interface UpdateHelper {
  boolean isCommandFromEditMessage();

  boolean isCommandFromMessage();

  boolean hasACommand();

  MessageEntity getCommand();
}
