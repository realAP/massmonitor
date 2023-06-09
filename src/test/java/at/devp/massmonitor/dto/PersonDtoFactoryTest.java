package at.devp.massmonitor.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.devp.massmonitor.telegram.helper.UpdateExtender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@ExtendWith(MockitoExtension.class)
class PersonDtoFactoryTest {

  @InjectMocks private PersonDtoFactory underTest;

  @Test
  void whenCreateGivenUpdateExtenderWithMessageAndWeightThenReturnPersonWithRelevantData() {
    final var expectedPerson = new PersonDto();
    expectedPerson.setWeight("80");
    expectedPerson.setMessageId(123);
    expectedPerson.setUserName("userName");
    expectedPerson.setCreationTime(1234567);

    final var message = new Message();
    message.setDate(expectedPerson.getCreationTime());

    final var chat = new Chat();
    message.setChat(chat);
    message.setMessageId(expectedPerson.getMessageId());
    final var user = new User();
    user.setUserName(expectedPerson.getUserName());
    message.setFrom(user);

    final var update = new Update();
    update.setMessage(message);

    final var updateExtender = new UpdateExtender(update);

    final var result = underTest.create(updateExtender, expectedPerson.getWeight());

    assertThat(result, is(expectedPerson));
  }

  @Test
  void
      whenCreateFromEditedGivenUpdateExtenderWithEditedMessageAndWeightThenReturnPersonWithRelevantData() {
    final var expectedPerson = new PersonDto();
    expectedPerson.setWeight("80");
    expectedPerson.setMessageId(123);
    expectedPerson.setUserName("userName");
    expectedPerson.setCreationTime(1234567);

    final var message = new Message();
    message.setDate(expectedPerson.getCreationTime());

    final var chat = new Chat();
    message.setChat(chat);
    message.setMessageId(expectedPerson.getMessageId());
    final var user = new User();
    user.setUserName(expectedPerson.getUserName());
    message.setFrom(user);

    final var update = new Update();
    update.setEditedMessage(message);

    final var updateExtender = new UpdateExtender(update);

    final var result = underTest.createFromEdited(updateExtender, expectedPerson.getWeight());

    assertThat(result, is(expectedPerson));
  }
}
