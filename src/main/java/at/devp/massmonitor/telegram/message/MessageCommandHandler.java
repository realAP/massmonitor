package at.devp.massmonitor.telegram.message;

import at.devp.massmonitor.business.action.CreateWeightConsumer;
import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.dto.PersonDtoFactory;
import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.commands.CrudTypeDetector;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class MessageCommandHandler implements HandlerIdentifier {
  private final CommandsParser commandsParser;
  private final CrudTypeDetector crudTypeDetector;
  private final PersonDtoFactory personDtoFactory;
  private final CreateWeightConsumer createWeightConsumer;


  public void consume(final UpdateExtender extendedUpdate, final Consumer<SendMessage> sendMessageConsumer) {

    // interpret an update and retrieve command / crud type / and build object with is going out
    // from which can be do an aciton

    //

    final CrudType crudType = crudTypeDetector.getType(extendedUpdate);
    final Commands commands = commandsParser.getCommand(extendedUpdate);
    final String commandArgument = commandsParser.getArgumentOfCommand(extendedUpdate);

    if (Objects.equals(commands, Commands.WEIGHT) && crudType.equals(CrudType.CREATE)) {
      // build entity dto and call business logic
      final PersonDto personDto = personDtoFactory.create(extendedUpdate, commandArgument);
      createWeightConsumer.createWeight(personDto);
      informUserForCreation(extendedUpdate, sendMessageConsumer);
    }

    // TODO use this information to create an Object which hold needed information
    //  to create from this point what the business logic need, i am tired

  }

  private void informUserForCreation(final UpdateExtender extendedUpdate, final Consumer<SendMessage> sendMessageConsumer) {
    final var message = new SendMessage();
    message.setChatId(extendedUpdate.getUpdate().getMessage().getChatId().toString());
    message.setReplyToMessageId(extendedUpdate.getUpdate().getMessage().getMessageId());
    message.setText("created weight");
    sendMessageConsumer.accept(message);
  }

  @Override
  public String getIdentifier() {
    return "CommandHandler";
  }
}
