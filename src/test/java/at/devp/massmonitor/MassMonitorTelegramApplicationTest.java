package at.devp.massmonitor;

import at.devp.massmonitor.telegram.TelegramMessageHandler;
import at.devp.massmonitor.telegram.helper.TestDataCollector;
import at.devp.massmonitor.telegram.helper.UpdateExtenderBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

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

    verify(telegramMessageHandler).consume(updateExtender);
  }
}
