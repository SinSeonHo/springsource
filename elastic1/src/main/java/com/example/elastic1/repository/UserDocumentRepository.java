package com.example.elastic1.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.elastic1.document.UserDocument;

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {

}
