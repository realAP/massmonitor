package at.devp.massmonitor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import at.devp.massmonitor.repository.PersonRepository;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootTest
class MassMonitorTelegramApplicationIntegrationTest {

  @Autowired private PersonRepository personRepository;

  private Update createWeight;

  private Update updateWeight;

  // This is needed that the TelegramBotInitializer doesn't throw an exception and the test
  // container starts without credentials
  @MockBean private TelegramBotsApi telegramBotsApi;

  @Autowired private MassMonitor underTest;

  @BeforeEach
  void setUp() throws TelegramApiException {

    // doReturn(new DefaultBotSession())
    //   .when(telegramBotsApi.registerBot(ArgumentMatchers.any(LongPollingBot.class)));
    // when(telegramBotsApi.registerBot(ArgumentMatchers.any(LongPollingBot.class)))
    //   .thenReturn(new DefaultBotSession());

    initCreateWeight();
    initUpdateWeight();
    personRepository.deleteAll();
  }

  private void initCreateWeight() {
    final var gson = new Gson();
    String jsonFile;
    try {
      File file = ResourceUtils.getFile("classpath:create_weight.json");
      FileInputStream reader = new FileInputStream(file);
      jsonFile = new String(reader.readAllBytes(), StandardCharsets.UTF_8);
      reader.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    createWeight = gson.fromJson(jsonFile, Update.class);
  }

  private void initUpdateWeight() {
    final var gson = new Gson();
    String jsonFile;
    try {
      File file = ResourceUtils.getFile("classpath:update_weight.json");
      FileInputStream reader = new FileInputStream(file);
      jsonFile = new String(reader.readAllBytes(), StandardCharsets.UTF_8);
      reader.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    updateWeight = gson.fromJson(jsonFile, Update.class);
  }

  @Test
  void whenGivenUpdateWhichIsACreateWeightCommandThenStorePerson() {
    assertThat(personRepository.count(), is(0L));

    underTest.onUpdateReceived(createWeight);

    assertThat(personRepository.count(), is(1L));
    final var person =
        personRepository.findPeopleByMessageId(createWeight.getMessage().getMessageId()).get();
    assertThat(person.getWeight(), is("90"));
  }

  @Test
  void whenGivenUpdateWhichIsAUpdateWeightCommandTheUpdateStoredPerson() {
    assertThat(personRepository.count(), is(0L));

    underTest.onUpdateReceived(createWeight);

    assertThat(personRepository.count(), is(1L));

    underTest.onUpdateReceived(updateWeight);

    final var person =
        personRepository.findPeopleByMessageId(createWeight.getMessage().getMessageId());
    assertThat(person.get().getWeight(), is("80"));
  }
}
