package com.jobsearch.ai.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChatRequestDto(@NotBlank String message) {

}
