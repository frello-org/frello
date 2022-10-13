package com.frello.services;

import com.frello.daos.service.SQLServiceRequestDAO;
import com.frello.daos.service.ServiceRequestDAO;
import com.frello.models.service.ServiceRequest;
import com.frello.models.user.User;
import com.frello.services.common.HttpException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ServiceRequestService {
    private static final ServiceRequestDAO serviceRequestDAO = new SQLServiceRequestDAO();

    public static ServiceRequest serviceRequest(UUID id) throws HttpException {
        return serviceRequestDAO
                .serviceRequest(id)
                .orElseThrow(() -> new HttpException(404, "Not found"));
    }

    public static List<ServiceRequest> serviceRequests() {
        return serviceRequestDAO.serviceRequests();
    }

    public static CreateServiceRequestResponse createService(CreateServiceRequestParams params, User creator)
            throws HttpException {
        if (!creator.isConsumer()) {
            throw new HttpException(403, "Only service consumer users may create service requests");
        }
        var serviceRequest = ServiceRequest.builder()
                .id(UUID.randomUUID())
                .consumerId(creator.getId())
                .expectedPrice(params.getExpectedPrice())
                .title(params.getTitle())
                .rawMarkdownPageBody(params.getRawMarkdownPageBody())
                // FIXME: Parse markdown.
                .parsedHTMLPageBody(params.getRawMarkdownPageBody())
                .creationTime(OffsetDateTime.now())
                .build();
        serviceRequestDAO.create(serviceRequest);

        return new CreateServiceRequestResponse(serviceRequest.getId());
    }

    @Data
    @Builder
    @Jacksonized
    public static class CreateServiceRequestParams {
        @NonNull
        private BigDecimal expectedPrice;
        @NonNull
        private String title;
        @NonNull
        private String rawMarkdownPageBody;
        @NonNull
        private List<UUID> categoryIDs;
    }

    @Data
    @AllArgsConstructor
    @Builder
    @Jacksonized
    public static class CreateServiceRequestResponse {
        @NonNull
        private UUID serviceRequestId;
    }
}
