package at.devp.massmonitor.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@ExtendWith(MockitoExtension.class)
class CommandsParserTest {

  @InjectMocks private CommandsParser underTest;

  @Test
  void whenGetCommandGivenMessageWithWeightCommandThenReturnDomainCommand() {
    final var message = new Message();
    message.setText("/weight");
    message.setEntities(List.of(new MessageEntity(EntityType.BOTCOMMAND, 0, 7)));

    final var update = new Update();
    update.setMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    final var result = underTest.getCommand(extendedUpdate);

    assertThat(result, Matchers.is(Commands.WEIGHT));
  }

  @Test
  void whenGetArgumentOfCommandGivenMessageWithWeightCommandThenReturnArgument() {
    final var message = new Message();
    message.setText("/weight 82.9");
    message.setEntities(List.of(new MessageEntity(EntityType.BOTCOMMAND, 0, 7)));

    final var update = new Update();
    update.setMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    final var result = underTest.getArgumentOfCommand(extendedUpdate);

    assertThat(result, is("82.9"));
  }

  @Test
  void whenGetEditCommandGivenEditedMessageWithWeightCommandThenReturnDomainCommand() {
    final var message = new Message();
    message.setText("/weight");
    message.setEntities(List.of(new MessageEntity(EntityType.BOTCOMMAND, 0, 7)));

    final var update = new Update();
    update.setEditedMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    final var result = underTest.getEditCommand(extendedUpdate);

    assertThat(result, is(Commands.WEIGHT));
  }

  @Test
  void whenGetArgumentOfEditCommandGivenEditMessageWithWeightCommandThenReturnArgument() {
    final var message = new Message();
    message.setText("/weight 82.9");
    message.setEntities(List.of(new MessageEntity(EntityType.BOTCOMMAND, 0, 7)));

    final var update = new Update();
    update.setEditedMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    final var result = underTest.getArgumentOfEditCommand(extendedUpdate);

    assertThat(result, is("82.9"));
  }
}
