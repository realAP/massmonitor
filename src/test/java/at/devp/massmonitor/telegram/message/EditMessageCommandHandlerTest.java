package at.devp.massmonitor.telegram.message;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.devp.massmonitor.business.action.UpdateWeightConsumer;
import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.dto.PersonDtoFactory;
import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.commands.CrudTypeDetector;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.xml.bind.ValidationException;
import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
class EditMessageCommandHandlerTest {

  @Mock private CrudTypeDetector crudTypeDetector;

  @Mock private CommandsParser commandsParser;

  @Mock private UpdateWeightConsumer updateWeightConsumer;

  @Mock private PersonDtoFactory personDtoFactory;

  @InjectMocks private EditMessageCommandHandler underTest;

  @Test
  void consumeGivenWeight103ChangeCommandVerifyChangePersonIsCalled() throws ValidationException {
    final var updateExtender = new UpdateExtender(new Update());
    when(crudTypeDetector.getType(updateExtender)).thenReturn(CrudType.UPDATE);

    when(commandsParser.getEditCommand(updateExtender)).thenReturn(Commands.WEIGHT);

    final var commandArgument = "89";
    when(commandsParser.getArgumentOfEditCommand(updateExtender)).thenReturn(commandArgument);

    final var expectedPerson = new PersonDto();
    when(personDtoFactory.createFromEdited(updateExtender, commandArgument))
        .thenReturn(expectedPerson);

    // TODO: FIX ME
    Consumer<SendMessage> sendMessageConsumer = (SendMessage) -> System.out.println("FIX ME");
    underTest.consume(updateExtender, sendMessageConsumer);

    verify(updateWeightConsumer).updateWeight(expectedPerson);
  }

  // TODO: add test for error case
}
