package ua.sirkostya009.evchargervalidator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.i18n.LocaleContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import ua.sirkostya009.evchargervalidator.station.Station;

@RequiredArgsConstructor
public class FieldsNotNullWhenPublicValidator implements ConstraintValidator<FieldsNotNullWhenPublic, Station> {
    private final MessageSource messageSource;

    @Override
    public boolean isValid(Station station, ConstraintValidatorContext context) {
        if (station.isPrivate() == null || station.isPrivate()) {
            return true;
        }

        boolean valid = true;

        if (station.title() == null) {
            addValidationError(context, "title", "validation.title.null");
            valid = false;
        }

        if (station.description() == null) {
            addValidationError(context, "description", "validation.description.null");
            valid = false;
        }

        if (station.address() == null) {
            addValidationError(context, "address", "validation.address.null");
            valid = false;
        }

        if (station.coordinates() == null) {
            addValidationError(context, "coordinates", "validation.coordinates.null");
            valid = false;
        }

        return valid;
    }

    private void addValidationError(ConstraintValidatorContext context, String property, String messageCode) {
        String message = messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(property).addConstraintViolation();
    }
}
