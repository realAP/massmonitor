package at.devp.massmonitor.business.action;

import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.entitiy.Person;
import at.devp.massmonitor.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateWeightConsumer {

  //    private final HashMap<Integer, PersonDto> personRepository;
  private final PersonRepository personRepository;

  public void createWeight(final PersonDto personDto) {
    final var person = new Person();
    person.setCreationTime(personDto.getCreationTime());
    person.setWeight(personDto.getWeight());
    person.setMessageId(personDto.getMessageId());
    person.setUserName(personDto.getUserName());

    personRepository.save(person);
    // personRepository.put(personDto.getMessageId(), personDto);
    System.out.println("person stored: ");
    System.out.println(personDto);
    System.out.println("Hello Yoshi");
  }
}
