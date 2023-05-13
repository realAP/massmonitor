package at.devp.massmonitor.telegram.commands;

import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CrudTypeDetector {

  public CrudType getType(@NonNull UpdateExtender update) {

    if (update.isCommandFromMessage()) {
      final var command = update.getCommand();
      if (command.getText().equals(Commands.WEIGHT.getCommand())) {
        return CrudType.CREATE;
      }
    } else if (update.isCommandFromEditMessage()) {
      final var command = update.getCommand();
      if (command.getText().equals(Commands.WEIGHT.getCommand())) {
        return CrudType.UPDATE;
      }
    }
    throw new IllegalArgumentException("Unknown CrudType");
  }
}
