package com.example.chat.service;

import com.example.chat.dto.MessageDto;

import java.util.List;
import java.util.Optional;

public interface MessageService  {
    MessageDto sendMessage(MessageDto messageDto);
    List<MessageDto> getMessages(String senderId, String receiverId);
    Optional<MessageDto> getMessageById(Long id);
}
