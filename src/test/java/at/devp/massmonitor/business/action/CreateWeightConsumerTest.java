package at.devp.massmonitor.business.action;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.entitiy.Person;
import at.devp.massmonitor.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateWeightConsumerTest {

  @Mock private PersonRepository personRepository;

  @InjectMocks private CreateWeightConsumer underTest;

  @Test
  void whenGivenPersonDtoTheStoreCorrespondingPersonEntity() {
    final var personDto = new PersonDto();
    personDto.setWeight("80");
    personDto.setCreationTime(1234);
    personDto.setUserName("userName123");
    personDto.setMessageId(1337);

    underTest.createWeight(personDto);

    final var argumentCaptorForCreatedPerson = ArgumentCaptor.forClass(Person.class);
    verify(personRepository).save(argumentCaptorForCreatedPerson.capture());

    assertThat(argumentCaptorForCreatedPerson.getValue().getWeight(), is("80"));
    assertThat(argumentCaptorForCreatedPerson.getValue().getCreationTime(), is(1234));
    assertThat(argumentCaptorForCreatedPerson.getValue().getUserName(), is("userName123"));
    assertThat(argumentCaptorForCreatedPerson.getValue().getMessageId(), is(1337));
  }
}
