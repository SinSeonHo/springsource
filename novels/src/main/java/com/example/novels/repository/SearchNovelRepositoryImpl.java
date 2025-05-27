package com.example.novels.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.novels.entity.Novel;
import com.example.novels.entity.QGenre;
import com.example.novels.entity.QGrade;
import com.example.novels.entity.QNovel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class SearchNovelRepositoryImpl extends QuerydslRepositorySupport implements SearchNovelRepository {

    public SearchNovelRepositoryImpl() {
        super(Novel.class);

    }

    @Override
    public Object[] getNovelById(Long id) {

        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel);
        query.leftJoin(genre).on(novel.genre.eq(genre));
        query.where(novel.id.eq(id));

        JPQLQuery<Double> ratingAvg = JPAExpressions.select(grade.rating.avg())
                .from(grade)
                .where(grade.novel.eq(novel))
                .groupBy(grade.novel);

        JPQLQuery<Tuple> tuple = query.select(novel, genre, ratingAvg);
        Tuple result = tuple.fetchFirst();

        return result.toArray();
    }

    @Override
    public Page<Object[]> list(Pageable pageable) {
        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel);
        query.leftJoin(genre).on(novel.genre.eq(genre));

        JPQLQuery<Double> ratingAvg = JPAExpressions.select(grade.rating.avg())
                .from(grade)
                .where(grade.novel.eq(novel))
                .groupBy(grade.novel);

        JPQLQuery<Tuple> tuple = query.select(novel, genre, ratingAvg);

        // 무조건 필요 속도빨라짐
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = novel.id.gt(0);
        builder.and(expression);

        // 검색

        tuple.where(builder);

        // Sort 생성
        Sort sort = pageable.getSort();
        // sort 기준이 여러개 일 수 있어서
        sort.stream().forEach(order -> {
            // import com.querydsl.core.types.Order;
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            String prop = order.getProperty();
            PathBuilder<Novel> ordeBuilder = new PathBuilder<>(Novel.class, "novel");
            tuple.orderBy(new OrderSpecifier(direction, ordeBuilder.get(prop)));
        });

        // ------------------- 전체 리스트 + Sort 적용

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        long totalCnt = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable,
                totalCnt);
    }

}
