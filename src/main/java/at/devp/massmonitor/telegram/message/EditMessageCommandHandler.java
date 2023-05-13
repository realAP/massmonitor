package at.devp.massmonitor.telegram.message;

import at.devp.massmonitor.business.action.UpdateWeightConsumer;
import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.dto.PersonDtoFactory;
import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.commands.CrudTypeDetector;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditMessageCommandHandler implements HandlerIdentifier {
  private final CrudTypeDetector crudTypeDetector;
  private final CommandsParser commandsParser;
  private final PersonDtoFactory personDtoFactory;
  private final UpdateWeightConsumer updateWeightConsumer;

  public void consume(@NonNull UpdateExtender extendedUpdate) {
    final CrudType crudType = crudTypeDetector.getType(extendedUpdate);
    final Commands commands = commandsParser.getEditCommand(extendedUpdate);
    final String commandArgument = commandsParser.getArgumentOfEditCommand(extendedUpdate);

    if (Commands.WEIGHT.equals(commands) && CrudType.UPDATE.equals(crudType)) {
      final PersonDto personDto = personDtoFactory.createFromEdited(extendedUpdate, commandArgument);
      updateWeightConsumer.updateWeight(personDto);
    }
  }

  @Override
  public String getIdentifier() {
    return "EditHandler";
  }
}
