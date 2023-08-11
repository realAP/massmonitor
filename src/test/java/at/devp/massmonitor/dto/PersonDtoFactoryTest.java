package at.devp.massmonitor.dto;

import at.devp.massmonitor.telegram.helper.UpdateExtender;
import at.devp.massmonitor.validator.WeightValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.xml.bind.ValidationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class PersonDtoFactoryTest {

  @Mock
  private WeightValidator weightValidator;
  @InjectMocks
  private PersonDtoFactory underTest;

  @Test
  void whenCreateGivenUpdateExtenderWithMessageAndWeightThenReturnPersonWithRelevantData() throws ValidationException {
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
    verify(weightValidator).validate(expectedPerson.getWeight());
  }

  @Test
  void
  whenCreateFromEditedGivenUpdateExtenderWithEditedMessageAndWeightThenReturnPersonWithRelevantData() throws ValidationException {
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
    verify(weightValidator).validate(expectedPerson.getWeight());
  }
}
