package com.example.shop.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.shop.document.ProductDocument;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, String> {

}
