package ua.sirkostya009.evchargervalidator.station;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.sirkostya009.evchargervalidator.validation.FieldsNotNullWhenPublic;

import java.util.List;

@FieldsNotNullWhenPublic
public record Station(
        @NotNull(message = "{validation.id.null}")
        Long id,
        String title,
        String description,
        String address,
        String coordinates,
        @NotNull(message = "{validation.is-private.null}")
        Boolean isPrivate,
        @NotNull(message = "{validation.connectors.null}")
        @Size(min = 1, max = 8, message = "{validation.connectors.size-range}")
        List<Connector> connectors
) {
}
