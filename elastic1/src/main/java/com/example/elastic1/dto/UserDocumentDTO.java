package com.example.elastic1.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDocumentDTO {

    private String id;

    private String name;

    private Long age;

    private Boolean isActive;
}
