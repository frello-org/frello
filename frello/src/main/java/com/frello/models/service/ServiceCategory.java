package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceCategory {
    @NonNull
    private final UUID id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String hexCSSColor;
}
