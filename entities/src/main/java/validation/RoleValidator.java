package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class RoleValidator implements ConstraintValidator<Role, String> {
    private List<String> roles = Arrays.asList("ROLE_CASHIER", "ROLE_ADMIN");

    @Override
    public void initialize(Role role) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return roles.contains(s);
    }
}
