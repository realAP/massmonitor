package at.devp.massmonitor.telegram.commands;

import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CrudTypeDetectorTest {

  private final CrudTypeDetector underTest = new CrudTypeDetector();

  @Test
  void whenUpdateIsNullThenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> underTest.getType(null));
  }

  @Test
  void whenGivenMessageWithWeightCommandThenReturnCrudTypeCreate() {
    final var command = new MessageEntity(EntityType.BOTCOMMAND, 0, 7);
    command.setText(Commands.WEIGHT.getCommand());

    final var message = new Message();
    message.setEntities(List.of(command));
    message.setText(Commands.WEIGHT.getCommand());

    final var update = new Update();
    update.setMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    final var result = underTest.getType(extendedUpdate);

    assertThat(result, is(CrudType.CREATE));
  }

  @Test
  void whenGivenEditedMessageWithWeightCommandThenReturnCrudTypeUpdate() {
    final var command = new MessageEntity(EntityType.BOTCOMMAND, 0, 7);
    command.setText(Commands.WEIGHT.getCommand());

    final var message = new Message();
    message.setEntities(List.of(command));
    message.setText(Commands.WEIGHT.getCommand());

    final var update = new Update();
    update.setEditedMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    final var result = underTest.getType(extendedUpdate);

    assertThat(result, is(CrudType.UPDATE));
  }

  @Test
  void whenGivenMessageWithUnknownCommandThenThrowIllegalArgumentException() {
    final var command = new MessageEntity(EntityType.BOTCOMMAND, 0, 8);
    command.setText(Commands.UNKNOWN.getCommand());

    final var message = new Message();
    message.setEntities(List.of(command));
    message.setText(Commands.UNKNOWN.getCommand());

    final var update = new Update();
    update.setMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    assertThrows(IllegalArgumentException.class, () -> underTest.getType(extendedUpdate));
  }

  @Test
  void whenGivenEditedMessageWithUnknownCommandThrowIllegalArgumentException() {
    final var command = new MessageEntity(EntityType.BOTCOMMAND, 0, 8);
    command.setText(Commands.UNKNOWN.getCommand());

    final var message = new Message();
    message.setEntities(List.of(command));
    message.setText(Commands.UNKNOWN.getCommand());

    final var update = new Update();
    update.setEditedMessage(message);
    final var extendedUpdate = new UpdateExtender(update);

    assertThrows(IllegalArgumentException.class, () -> underTest.getType(extendedUpdate));
  }
}
