package com.example.movie.repository.total;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.QMovie;
import com.example.movie.entity.QMovieImage;
import com.example.movie.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MovieImageReviewRepositoryImpl extends QuerydslRepositorySupport implements MovieImageReviewRepository {

        public MovieImageReviewRepositoryImpl() {
                super(MovieImage.class);
        }

        @Override
        public Page<Object[]> getTotalList(String type, String keyword, Pageable pageable) {

                QMovie movie = QMovie.movie;
                QMovieImage movieImage = QMovieImage.movieImage;
                QReview review = QReview.review;

                JPQLQuery<MovieImage> query = from(movieImage);
                // LEFT JOIN MOVIE m ON mi.MOVIE_MNO = m.MNO
                query.leftJoin(movie).on(movieImage.movie.eq(movie));

                JPQLQuery<Long> count = JPAExpressions.select(review.countDistinct()).from(review)
                                .where(review.movie.eq(movieImage.movie));
                JPQLQuery<Double> avg = JPAExpressions.select(review.grade.avg().round()).from(review)
                                .where(review.movie.eq(movieImage.movie));

                JPQLQuery<Tuple> tuple = query.select(movie, movieImage, count, avg)
                                .where(movieImage.inum.in(JPAExpressions.select(movieImage.inum.min())
                                                .from(movieImage)
                                                .groupBy(movieImage.movie)));
                // .orderBy(movie.mno.desc());

                // 검색
                BooleanBuilder builder = new BooleanBuilder();
                BooleanExpression expression = movie.mno.gt(0);
                builder.and(expression);

                BooleanBuilder condition = new BooleanBuilder();

                if (!type.isEmpty() && type.contains("title")) {
                        condition.or(movie.title.contains(keyword));
                        builder.and(condition);
                }

                tuple.where(builder);

                // Sort 생성
                Sort sort = pageable.getSort();
                // sort 기준이 여러개 일 수 있어서
                sort.stream().forEach(order -> {
                        // import com.querydsl.core.types.Order;
                        Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                        String prop = order.getProperty();
                        PathBuilder<Movie> ordeBuilder = new PathBuilder<>(Movie.class, "movie");
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

        @Override
        public List<Object[]> getMovieRow(Long mno) {
                log.info("영화 상세정보 요청 {}", mno);

                QMovie movie = QMovie.movie;
                QMovieImage movieImage = QMovieImage.movieImage;
                QReview review = QReview.review;

                JPQLQuery<MovieImage> query = from(movieImage);
                // LEFT JOIN MOVIE m ON mi.MOVIE_MNO = m.MNO
                query.leftJoin(movie).on(movieImage.movie.eq(movie));

                JPQLQuery<Long> count = JPAExpressions.select(review.countDistinct()).from(review)
                                .where(review.movie.eq(movieImage.movie));
                JPQLQuery<Double> avg = JPAExpressions.select(review.grade.avg().round()).from(review)
                                .where(review.movie.eq(movieImage.movie));

                JPQLQuery<Tuple> tuple = query.select(movie, movieImage, count, avg)
                                .where(movieImage.movie.mno.eq(mno))
                                .orderBy(movieImage.inum.desc());

                List<Tuple> result = tuple.fetch();

                List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

                return list;
        }

}
