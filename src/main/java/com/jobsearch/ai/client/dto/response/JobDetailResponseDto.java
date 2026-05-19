package com.jobsearch.ai.client.dto.response;

import java.util.List;

public record JobDetailResponseDto(JobPostingResponseDto posting,
                                   List<JobPostingResponseDto> relatedJobs) {

}
