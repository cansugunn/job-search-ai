package com.jobsearch.ai.service.impl;

import com.jobsearch.ai.data.dto.request.ChatRequestDto;
import com.jobsearch.ai.data.dto.response.ChatResponseDto;
import com.jobsearch.ai.service.ChatService;
import com.jobsearch.ai.tools.JobSearchTools;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import static com.jobsearch.ai.data.constant.ChatClientConstants.TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatClient chatClient;

  private final JobSearchTools jobSearchTools;

  @Override
  public ChatResponseDto chat(ChatRequestDto request, String bearerToken) {
    log.debug("Processing AI chat request: {}", request.message());
    ChatResponse response = chatClient.prompt()
                                      .system(SYSTEM_PROMPT)
                                      .user(request.message())
                                      .tools(jobSearchTools)
                                      .toolContext(Map.of(TOKEN, bearerToken))
                                      .call()
                                      .chatResponse();
    String reply = response.getResult().getOutput().getText();
    log.debug("AI reply: {}", reply);
    return new ChatResponseDto(reply);
  }
}
