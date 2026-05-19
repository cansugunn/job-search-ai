package com.jobsearch.ai.client.dto.response;

import java.util.UUID;

public record CompanyDto(UUID id,
                         String name,
                         String website) {

}
