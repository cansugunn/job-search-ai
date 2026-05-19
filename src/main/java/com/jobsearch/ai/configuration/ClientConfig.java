package com.jobsearch.ai.configuration;

import com.jobsearch.ai.client.JobSearchApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

  @Value("${job-search.api.base-url}")
  private String jobSearchApiBaseUrl;

  @Bean
  public JobSearchApiClient jobSearchApiClient() {
    RestClient restClient = RestClient.builder()
                                      .baseUrl(jobSearchApiBaseUrl)
                                      .build();
    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(JobSearchApiClient.class);
  }
}
