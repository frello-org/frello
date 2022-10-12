package com.frello.models.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.frello.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceRequest {
    private final UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ServiceCategory> categories;

    private UUID consumerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User consumer;

    private BigDecimal expectedPrice;
    private String title;
    private String rawMarkdownPageBody;
    private String parsedHTMLPageBody;

    private boolean deleted;
    private OffsetDateTime deletionTime;
    private final OffsetDateTime creationTime;
}
