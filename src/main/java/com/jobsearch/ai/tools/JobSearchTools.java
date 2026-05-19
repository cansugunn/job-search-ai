package com.jobsearch.ai.tools;

import com.jobsearch.ai.client.JobSearchApiClient;
import com.jobsearch.ai.client.dto.response.ApplyResponseDto;
import com.jobsearch.ai.client.dto.response.JobDetailResponseDto;
import com.jobsearch.ai.client.dto.response.JobPostingResponseDto;
import com.jobsearch.ai.client.dto.response.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.jobsearch.ai.data.constant.ChatClientConstants.TOKEN;
import static java.util.Objects.isNull;
import static java.util.UUID.fromString;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobSearchTools {

    private final JobSearchApiClient jobSearchApiClient;

    @Tool(description = """
            Search for job postings by position title, city, and optionally working preference.
            Returns a list of matching job postings.
            """)
    public Page<JobPostingResponseDto>
    searchJobs(@ToolParam(description = "Job position or title keyword to search for, e.g. 'web developer', 'frontend'",
                       required = false)
               String position,
               @ToolParam(description = "Country name to search in, e.g. 'Turkey', 'Spain' as lower case in english",
                       required = false) String countryName,
               @ToolParam(description = "City name to search in, e.g. 'Istanbul', 'Ankara' as lower case in english",
                       required = false) String cityName,
               @ToolParam(description = "Town name to search in, e.g. 'Karşıyaka', 'Bornova' as lower case in english",
                       required = false) String townName,
               @ToolParam(description = "Working preference: FULLTIME, PARTTIME, REMOTE, or HYBRID. Optional.",
                       required = false) String workingPreference,
               ToolContext toolContext) {
        String token = (String) toolContext.getContext().get(TOKEN);
        log.info("AI tool: searchJobs position={}, city={}, workingPreference={}", position, cityName, workingPreference);
        try {
            ResponseEntity<Page<JobPostingResponseDto>> response =
                    jobSearchApiClient.searchJobs(
                            position,
                            null,
                            null,
                            null,
                            countryName,
                            cityName,
                            townName,
                            workingPreference,
                            0,
                            5,
                            token);
            if (isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException();
            }
            return response.getBody();
        } catch (Exception exception) {
            log.error("searchJobs tool error: {}", exception.getMessage());
            throw exception;
        }
    }

    @Tool(description = """
            Get detailed information about a specific job posting including description, location, company,
            and related jobs. Use the job ID from search results.
            """)
    public JobDetailResponseDto
    getJobDetail(@ToolParam(description = "The UUID of the job posting to retrieve details for") String jobId,
                 ToolContext toolContext) {
        String token = (String) toolContext.getContext().get(TOKEN);
        log.info("AI tool: getJobDetail jobId={}", jobId);
        try {
            ResponseEntity<JobDetailResponseDto> response =
                    jobSearchApiClient.getJobDetail(fromString(jobId), token);
            if (isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException();
            }
            return response.getBody();
        } catch (Exception exception) {
            log.error("getJobDetail tool error: {}", exception.getMessage());
            throw exception;
        }
    }

    @Tool(description = """
            Apply to a job posting on behalf of the authenticated user. Requires the user to be logged in. "
            Use the job ID from search results.
            """)
    public ApplyResponseDto applyToJob(@ToolParam(description = "The UUID of the job posting to apply to") String jobId,
                                       ToolContext toolContext) {
        String token = (String) toolContext.getContext().get(TOKEN);
        log.info("AI tool: applyToJob jobId={}", jobId);
        try {
            ResponseEntity<ApplyResponseDto> response = jobSearchApiClient.applyToJob(fromString(jobId), token);
            if (isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException();
            }
            return response.getBody();
        } catch (Exception exception) {
            log.error("applyToJob tool error: {}", exception.getMessage());
            throw exception;
        }
    }
}
