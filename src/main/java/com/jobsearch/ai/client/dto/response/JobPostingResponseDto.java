package com.jobsearch.ai.client.dto.response;

import com.jobsearch.ai.common.enums.WorkingPreference;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record JobPostingResponseDto(UUID id,
                                    String title,
                                    String description,
                                    CompanyDto company,
                                    String town,
                                    String city,
                                    String country,
                                    WorkingPreference workingPreference,
                                    BigDecimal salary,
                                    Integer applicationCount,
                                    LocalDateTime lastUpdatedDate) {

}
