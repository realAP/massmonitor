package at.devp.massmonitor;

import at.devp.massmonitor.telegram.TelegramMessageHandler;
import at.devp.massmonitor.telegram.helper.TestDataCollector;
import at.devp.massmonitor.telegram.helper.UpdateExtenderBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MassMonitorTelegramApplicationTest {

  @Mock
  private TelegramMessageHandler telegramMessageHandler;

  @Mock
  private UpdateExtenderBuilder updateExtenderBuilder;

  @Mock
  private TestDataCollector testDataCollector;

  @InjectMocks
  private MassMonitor underTest;

  @Test
  void whenOnUpdateReceivedGivenUpdateThenPassUpdateExtenderToTelegramMessageHandler() {
    final var update = new Update();
    final var updateExtender = updateExtenderBuilder.createUpdateExtender(update);

    underTest.onUpdateReceived(update);

    // TODO: FIX ME
    Consumer<SendMessage> sendMessageConsumer = (SendMessage) -> System.out.println("FIX ME");
    verify(telegramMessageHandler).consume(updateExtender, sendMessageConsumer);
  }
}
