package com.jobsearch.ai.data.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record ChatRequestDto(@NotBlank String message,
                             @Nullable String conversationId) {

}
