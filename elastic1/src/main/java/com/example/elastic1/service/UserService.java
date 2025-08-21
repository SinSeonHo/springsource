package com.example.elastic1.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.elastic1.document.UserDocument;
import com.example.elastic1.dto.UserDocumentDTO;
import com.example.elastic1.repository.UserDocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDocumentRepository userDocumentRepository;

    public UserDocumentDTO create(UserDocumentDTO userDocumentDTO) {

        UserDocument userDocument = dtoToDoc(userDocumentDTO);
        UserDocument savDocument = userDocumentRepository.save(userDocument);
        return docToDto(savDocument);
    }

    public UserDocumentDTO getUser(String id) {
        return docToDto(userDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다")));
    }

    public String removeUser(String id) {
        userDocumentRepository.deleteById(id);
        return "삭제된 유저아이디" + id;
    }

    public UserDocumentDTO updateUser(UserDocumentDTO userDocumentDTO) {
        UserDocument userDocument = userDocumentRepository.findById(userDocumentDTO.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        userDocument.setAge(userDocumentDTO.getAge());
        userDocument.setName(userDocumentDTO.getName());
        userDocument.setIsActive(userDocumentDTO.getIsActive());

        return docToDto(userDocumentRepository.save(userDocument));
    }

    public List<UserDocumentDTO> list() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserDocument> result = userDocumentRepository.findAll(pageable);
        return result.get().map(doc -> docToDto(doc)).collect(Collectors.toList());
    }

    private UserDocumentDTO docToDto(UserDocument userDodument) {
        return UserDocumentDTO.builder()
                .id(userDodument.getId())
                .name(userDodument.getName())
                .age(userDodument.getAge())
                .isActive(userDodument.getIsActive())
                .build();
    }

    private UserDocument dtoToDoc(UserDocumentDTO userDocumentDTO) {
        return UserDocument.builder()
                .id(userDocumentDTO.getId())
                .name(userDocumentDTO.getName())
                .age(userDocumentDTO.getAge())
                .isActive(userDocumentDTO.getIsActive())
                .build();
    }
}
