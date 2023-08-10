package at.devp.massmonitor;

import at.devp.massmonitor.telegram.TelegramMessageHandler;
import at.devp.massmonitor.telegram.helper.TestDataCollection;
import at.devp.massmonitor.telegram.helper.UpdateExtenderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class MassMonitor extends TelegramLongPollingBot {

  private final TelegramMessageHandler telegramMessageHandler;
  private final UpdateExtenderBuilder updateExtenderBuilder;
  private final TestDataCollection testDataCollector;

  @Value(value = "${telegram.floss.botUsername}")
  private String botUsername;

  @Value(value = "${telegram.floss.botToken}")
  private String botToken;

  private static void telegramUpdateDiscovery(Update update) {
    System.out.println("update:");
    System.out.println(update.getUpdateId());
    System.out.println("-------------");
    if (update.hasEditedMessage()) {
      System.out.println("editedMessage:");
      System.out.println(update.getEditedMessage().getText());
      System.out.println(update.getEditedMessage().toString());
      System.out.println(update.getEditedMessage().getChatId());
      System.out.println("isCommand: " + update.getEditedMessage().isCommand());
      System.out.println(
          "editedMessage HashId " + update.getEditedMessage().getChatId().hashCode());
      System.out.println(update.getEditedMessage().getMigrateFromChatId());
      System.out.println(update.getEditedMessage().getMigrateToChatId());
      System.out.println("-------------");
    }

    if (update.hasMessage()) {
      System.out.println("message: ");
      System.out.println(update.getMessage().getText());
      System.out.println(update.getMessage().toString());
      System.out.println(update.getMessage().getChatId());
      System.out.println("message HashId " + update.getMessage().getChatId().hashCode());
      System.out.println(update.getMessage().getMigrateFromChatId());
      System.out.println(update.getMessage().getMigrateToChatId());
      System.out.println("--------------");
    }
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    testDataCollector.collect(update);
    final var updateExtender = updateExtenderBuilder.createUpdateExtender(update);
    Consumer<SendMessage> sendMessageConsumer = this::sendMessage;
    telegramMessageHandler.consume(updateExtender, sendMessageConsumer);
  }

  // TODO: need testing
  @Scheduled(cron = "0 */5 * * * *")
  public void reminder() {
    final var sendMessage = new SendMessage();
    sendMessage.setChatId("-100855719721");
    sendMessage.setText("hey fetti, ja du!!! steh auf und geh dich wiegen!");
    sendMessage(sendMessage);
  }

  public void sendMessage(@Nullable SendMessage message) {
    try {
      execute(message);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

}
