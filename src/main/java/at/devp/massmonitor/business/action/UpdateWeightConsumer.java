package at.devp.massmonitor.business.action;

import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UpdateWeightConsumer {

  private final PersonRepository personRepository;

  @Transactional
  public void updateWeight(final PersonDto updatePersonDto) {

    final var updatePerson =
        personRepository
            .findPeopleByMessageId(updatePersonDto.getMessageId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "could not find personDto: " + updatePersonDto + " to change"));

    updatePerson.setCreationTime(updatePersonDto.getCreationTime());
    updatePerson.setWeight(updatePersonDto.getWeight());
    //        personRepository.deletePersonByMessageId(personDto.getMessageId());
    System.out.println(updatePerson);
    personRepository.save(updatePerson);
  }
}
