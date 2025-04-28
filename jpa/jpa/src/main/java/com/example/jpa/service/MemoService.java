package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoService {
    // Repository 매소드 호출 후 결과 받기

    private final MemoRepository memoRepository;
    private final ModelMapper modelMapper;

    public List<MemoDTO> getList() {
        List<Memo> list = memoRepository.findAll();

        // Memo => MemoDTO로 옮기는 작업이 필요
        // List<MemoDTO> memos = new ArrayList<>();
        // for (Memo memo : list) {
        // MemoDTO dto = MemoDTO.builder()
        // .mno(memo.getMno())
        // .memoText(memo.getMemoText())
        // .build();
        // memos.add(dto);
        // }

        // list.stream().forEach(memo -> System.out.println(memo)); forEach는 하나씩 가져와서 소비
        List<MemoDTO> memos = list.stream()
                .map(memo -> modelMapper.map(memo, MemoDTO.class))
                .collect(Collectors.toList());

        return memos;
    }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        // entity를 dto로 바꾸는작업필요
        // MemoDTO dto = entityToDto(memo);
        // modelMapper.map(원본, 변경할타입)
        MemoDTO dto = modelMapper.map(memo, MemoDTO.class);
        return dto;
    }

    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());
        // update 실행 => 수정 된 Memo return해줌
        memo = memoRepository.save(memo);
        memoRepository.save(memo);
        return memo.getMno();
    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할 memo 는 MemoDTO 에 저장된 상태
        // MemoDTO => Memo(Entity) 로 변환
        // Memo memo = dtoToEntity(dto);
        Memo memo = modelMapper.map(dto, Memo.class);
        // 새로 저장된 memo가 리턴됨
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private Memo dtoToEntity(MemoDTO memoDTO) {

        Memo memo = Memo.builder()
                .mno(memoDTO.getMno())
                .memoText(memoDTO.getMemoText())
                .build();

        return memo;
    }

    private MemoDTO entityToDto(Memo memo) {

        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreatedDate())
                .updatedDate(memo.getUpdatedDate())
                .build();

        // 위의 builder와 같음
        // MemoDTO dto = new MemoDTO();
        // dto.setMno(memo.getMno());
        // dto.setMemoText(memo.getMemoText());

        return dto;
    }
}
