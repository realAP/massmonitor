package at.devp.massmonitor.telegram;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.function.Consumer;

@Service
public class MessageSender {
  public void informUser(final Integer replyMessageId, final String chatId, final Consumer<SendMessage> sendMessageConsumer, final String text) {
    final var message = new SendMessage();
    message.setChatId(chatId);
    message.setReplyToMessageId(replyMessageId);
    message.setText(text);
    sendMessageConsumer.accept(message);
  }
}
