package at.devp.massmonitor.business.action;

import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.entitiy.Person;
import at.devp.massmonitor.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateWeightConsumer {
  private final PersonRepository personRepository;

  public void createWeight(final PersonDto personDto) {
    final var person = new Person();
    person.setCreationTime(personDto.getCreationTime());
    person.setWeight(personDto.getWeight());
    person.setMessageId(personDto.getMessageId());
    person.setUserName(personDto.getUserName());

    personRepository.save(person);
    log.info("weight of person: " + person + "stored");
  }
}
