package ua.sirkostya009.evchargervalidator.station;

import jakarta.validation.constraints.NotNull;

public record Connector(
        @NotNull
        Long id,
        @NotNull
        StationType type,
        @NotNull
        Long maxPower
) {
}
