package at.devp.massmonitor;

import at.devp.massmonitor.telegram.TelegramMessageHandler;
import at.devp.massmonitor.telegram.helper.TestDataCollection;
import at.devp.massmonitor.telegram.helper.UpdateExtenderBuilder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Nullable;

@Service
public class SenderService extends MassMonitor {

  public SenderService(TelegramMessageHandler telegramMessageHandler, UpdateExtenderBuilder updateExtenderBuilder, TestDataCollection testDataCollector) {
    super(telegramMessageHandler, updateExtenderBuilder, testDataCollector);
  }

  public void sendMessage(@Nullable SendMessage message) {
    try {
      execute(message);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

}
