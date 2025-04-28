package com.example.relation.repository;

import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.sports.Locker;
import com.example.relation.entity.sports.SportsMember;
import com.example.relation.repository.sports.LockerRepository;
import com.example.relation.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    // 단방향 (SprotsMember => Locker)
    @Test
    public void testInsert() {
        // Locker 5개 생성
        IntStream.range(1, 6).forEach(i -> {
            Locker locker = Locker.builder().name("locker1" + i).build();
            lockerRepository.save(locker);
        });

        // 스포츠 회원 생성
        LongStream.range(1, 6).forEach(i -> {
            SportsMember sportsMember = SportsMember.builder()
                    .locker(Locker.builder().id(i).build())
                    .name("member" + i)
                    .build();
            sportsMemberRepository.save(sportsMember);

        });
    }

    // 개별조회
    @Test
    public void testRead1() {
        System.out.println(lockerRepository.findById(1L).get());
        System.out.println(sportsMemberRepository.findById(1L).get());
    }

    @Transactional
    @Test
    public void testRead2() {
        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();

        System.out.println(sportsMember);
        System.out.println(sportsMember.getLocker());
    }

    @Test
    public void testUpdate() {
        // 3번 회원의 이름을 홍길동으로 변경
        SportsMember sportsMember = sportsMemberRepository.findById(3L).get();
        sportsMember.setName("홍길동");
        sportsMemberRepository.save(sportsMember);
    }

    @Test
    public void testDelete() {
        // 5번 회원 삭제
        sportsMemberRepository.deleteById(5L);
    }

    @Test
    public void testDelete2() {
        // 4번 locker 삭제
        // lockerRepository.deleteById(4L);

        // 4번 회원에게 5번 locker 할당하려면

        // 4번회원, 5번라커 각각 정보 가져옴
        SportsMember sportsMember = sportsMemberRepository.findById(4L).get();
        Locker locker = lockerRepository.findById(5L).get();

        sportsMember.setLocker(locker);
        sportsMemberRepository.save(sportsMember);

        // 4번 locker 삭제
        lockerRepository.deleteById(4L);

    }

    // -----------------------------
    // locker -> sprotsMember 접근
    // -----------------------------
    @Test
    public void testRead3() {
        Locker locker = lockerRepository.findById(1L).get();

        System.out.println(locker);
        System.out.println(locker.getSportsMember());
    }
}
