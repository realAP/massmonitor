package at.devp.massmonitor.validator;


import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.regex.Pattern;

@Service
public class WeightValidator {
  private final Pattern WEIGHT_PATTERN = Pattern.compile(
      "^(\\d{1,3})(,\\d{1,2})?$");

  public void validate(final String value) throws ValidationException {
    if (!WEIGHT_PATTERN.matcher(value).matches()) {
      throw new ValidationException("");
    }
  }
}
