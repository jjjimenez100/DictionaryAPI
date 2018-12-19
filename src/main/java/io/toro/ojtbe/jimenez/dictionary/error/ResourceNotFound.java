package io.toro.ojtbe.jimenez.dictionary.error;

import lombok.Data;

@Data
public final class ResourceNotFound {
    private final String error;
    private final String message;
}
