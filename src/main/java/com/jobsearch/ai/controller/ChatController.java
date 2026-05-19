package com.jobsearch.ai.controller;

import com.jobsearch.ai.data.dto.request.ChatRequestDto;
import com.jobsearch.ai.data.dto.response.ChatResponseDto;
import com.jobsearch.ai.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "AI Chat", description = "AI agent for job search and apply (real-time messaging not required)")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

  private final ChatService chatService;

  @Operation(summary = "Send a message to the AI job search agent")
  @PostMapping
  public ResponseEntity<ChatResponseDto> chat(@Valid @RequestBody ChatRequestDto request,
                                              @AuthenticationPrincipal Jwt jwt) {
    String bearerToken = "Bearer " + jwt.getTokenValue();
    return ResponseEntity.ok(chatService.chat(request, bearerToken));
  }
}
