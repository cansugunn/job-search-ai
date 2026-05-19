package com.jobsearch.ai.client.dto.response;

import java.util.List;

public record Page<T>(List<T> content) {

}
