package at.devp.massmonitor.dto;

import at.devp.massmonitor.telegram.helper.UpdateExtender;
import org.springframework.stereotype.Service;

@Service
public class PersonDtoFactory {

  public PersonDto create(UpdateExtender updateExtender, String weight) {
    final var person = new PersonDto();
    person.setWeight(weight);
    person.setMessageId(updateExtender.getUpdate().getMessage().getMessageId());
    person.setUserName(updateExtender.getUpdate().getMessage().getFrom().getUserName());
    person.setCreationTime(updateExtender.getUpdate().getMessage().getDate());

    return person;
  }

  public PersonDto createFromEdited(UpdateExtender updateExtender, String weight) {
    final var person = new PersonDto();
    person.setWeight(weight);
    person.setMessageId(updateExtender.getUpdate().getEditedMessage().getMessageId());
    person.setUserName(updateExtender.getUpdate().getEditedMessage().getFrom().getUserName());
    person.setCreationTime(updateExtender.getUpdate().getEditedMessage().getDate());

    return person;
  }
}
