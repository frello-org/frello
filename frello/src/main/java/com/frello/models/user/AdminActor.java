package com.frello.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class AdminActor {
    private final UUID id;
}
