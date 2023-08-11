package at.devp.massmonitor.business.action;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.bind.ValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WeightValidatorTest {

  @InjectMocks
  private WeightValidator underTest;

  @ParameterizedTest
  @ValueSource(strings = {"1", "45,67", "89,01", "123", "12", "56,78", "90,23", "3,45", "78", "2,34"})
  void whenGivenValidInputThenPassValidation(final String input) {
    assertDoesNotThrow(() -> underTest.validate(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"45.67", "89.01", "56.78", "90.23", "3.45", "2.34", "1234.567", "12345", "6789.01", //
      "23456.789", "12.345", "4567", "8901.23", "567.89", "1234", "0.123", "a", " ", "", "1.1a", "a.a", "1.1kg", //
      "1,2kg", "1,1 kg", "#fettmussweg", "#"})
  void whenGivenInvalidInputThenThrowValidationException(final String input) {
    assertThrows(ValidationException.class, () -> underTest.validate(input));
  }

}