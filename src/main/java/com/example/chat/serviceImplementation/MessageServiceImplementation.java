package com.example.chat.serviceImplementation;

import com.example.chat.dto.MessageDto;
import com.example.chat.entities.Message;
import com.example.chat.repository.MessageRepository;
import com.example.chat.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MessageServiceImplementation implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        // Set timestamp server-side to ensure consistency
        messageDto.setTimestamp(LocalDateTime.now());
        Message message = modelMapper.map(messageDto, Message.class);
        Message savedMessage = messageRepository.save(message);
        return modelMapper.map(savedMessage, MessageDto.class);
    }

    @Override
    public List<MessageDto> getMessages(String senderId, String receiverId) {
        // Retrieve messages sorted by timestamp
//        List<Message> messages = messageRepository.findBySenderIdAndReceiverIdOrderByTimestampAsc(senderId, receiverId);
        List<Message> messages = messageRepository.findMessagesBetweenUsers(senderId, receiverId);

        // Convert entities to DTOs
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageDto> getMessageById(Long id) {
        // Find message by ID and map to DTO if found
        return messageRepository.findById(id)
                .map(message -> modelMapper.map(message, MessageDto.class));
    }
}
