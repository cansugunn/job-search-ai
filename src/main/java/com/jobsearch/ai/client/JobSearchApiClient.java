package com.jobsearch.ai.client;

import com.jobsearch.ai.client.dto.response.ApplyResponseDto;
import com.jobsearch.ai.client.dto.response.JobDetailResponseDto;
import com.jobsearch.ai.client.dto.response.JobPostingResponseDto;
import com.jobsearch.ai.client.dto.response.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface JobSearchApiClient {

  @GetExchange("/api/v1/jobs")
  ResponseEntity<Page<JobPostingResponseDto>> searchJobs(@RequestParam(required = false) String position,
                                                         @RequestParam(required = false) String city,
                                                         @RequestParam(required = false) String workingPreference,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestHeader("Authorization") String token);

  @GetExchange("/api/v1/jobs/{id}")
  ResponseEntity<JobDetailResponseDto> getJobDetail(@PathVariable String id,
                                                    @RequestHeader("Authorization") String token);

  @PostExchange("/api/v1/jobs/{id}/apply")
  ResponseEntity<ApplyResponseDto> applyToJob(@PathVariable String id,
                                              @RequestHeader("Authorization") String token);
}
