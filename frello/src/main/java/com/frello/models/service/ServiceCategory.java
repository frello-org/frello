package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceCategory {
    private final UUID id;
    private String name;
    private String description;
    private String hexCSSColor;
}
