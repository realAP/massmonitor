package at.devp.massmonitor.telegram;

import at.devp.massmonitor.telegram.helper.UpdateExtender;
import at.devp.massmonitor.telegram.message.EditMessageCommandHandler;
import at.devp.massmonitor.telegram.message.MessageCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TelegramMessageHandler {
  private final MessageCommandHandler messageCommandHandler;

  private final EditMessageCommandHandler editMessageCommandHandler;

  public void consume(final UpdateExtender update, Consumer<SendMessage> sendMessageConsumer) {

    // breaks open close principle
    if (update.isCommandFromMessage()) {
      messageCommandHandler.consume(update, sendMessageConsumer);
    } else if (update.isCommandFromEditMessage()) {
      editMessageCommandHandler.consume(update, sendMessageConsumer);
    }
  }
}
