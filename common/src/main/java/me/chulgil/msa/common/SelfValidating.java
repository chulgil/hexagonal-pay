package me.chulgil.msa.common;

import javax.validation.*;
import java.util.Set;

public abstract class SelfValidating<T> {

  private Validator validator;
  private Class<T> entityClass;


  public SelfValidating() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Evaluates all Bean Validations on the attributes of this
   * instance.
   */
  protected void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate(entityClass.cast(this));
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
