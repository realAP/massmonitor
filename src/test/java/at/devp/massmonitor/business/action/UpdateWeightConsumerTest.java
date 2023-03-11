package at.devp.massmonitor.business.action;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.entitiy.Person;
import at.devp.massmonitor.repository.PersonRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class UpdateWeightConsumerTest {

  private Person oldPerson = new Person();

  private PersonDto updatePersonDto = new PersonDto();

  @Mock private PersonRepository personRepository;

  @InjectMocks private UpdateWeightConsumer underTest;

  @BeforeEach
  void setUp() {
    oldPerson = new Person();
    oldPerson.setWeight("800");
    oldPerson.setMessageId(1337);
    oldPerson.setCreationTime(12345);
    oldPerson.setUserName("userName123");

    updatePersonDto = new PersonDto();
    updatePersonDto.setWeight("80");
    updatePersonDto.setMessageId(1337);
    updatePersonDto.setCreationTime(99999);
    updatePersonDto.setUserName("userName123");
  }

  @Test
  void whenGivenPersonToUpdateThenChangeRequestedPersonsWeight() {
    when(personRepository.findPeopleByMessageId(updatePersonDto.getMessageId()))
        .thenReturn(Optional.of(oldPerson));

    underTest.updateWeight(updatePersonDto);

    final var argumentCaptorForChangedPerson = ArgumentCaptor.forClass(Person.class);
    verify(personRepository).save(argumentCaptorForChangedPerson.capture());

    final var updatedPerson =
        argumentCaptorForChangedPerson.getAllValues().stream().findFirst().get();
    assertThat(updatedPerson.getWeight(), is(updatePersonDto.getWeight()));
    assertThat(updatedPerson.getUserName(), is(updatePersonDto.getUserName()));
    assertThat(updatedPerson.getMessageId(), is(updatePersonDto.getMessageId()));
    assertThat(updatedPerson.getCreationTime(), is(updatePersonDto.getCreationTime()));
  }

  @Test
  void whenUpdateWeightThenMethodIsCalledWithinTransaction() throws NoSuchMethodException {
    final var result =
        underTest
            .getClass()
            .getMethod("updateWeight", PersonDto.class)
            .getAnnotation(Transactional.class);

    assertThat(result, is(notNullValue()));
  }

  @Test
  void whenGivenPersonToUpdateWhichDoesNotExistsThenThrowRunTimeException() {
    final var personToChangeDto = new PersonDto();
    personToChangeDto.setWeight("80");
    personToChangeDto.setMessageId(1337);
    personToChangeDto.setCreationTime(99999);
    personToChangeDto.setUserName("userName123");

    doThrow(new RuntimeException("person not found"))
        .when(personRepository)
        .findPeopleByMessageId(personToChangeDto.getMessageId());

    assertThrows(RuntimeException.class, () -> underTest.updateWeight(personToChangeDto));
  }

  @Test
  void whenGivenPersonToUpdateWhichExistsThenRetrievePerson() {
    when(personRepository.findPeopleByMessageId(updatePersonDto.getMessageId()))
        .thenReturn(Optional.of(oldPerson));

    underTest.updateWeight(updatePersonDto);
  }
}
