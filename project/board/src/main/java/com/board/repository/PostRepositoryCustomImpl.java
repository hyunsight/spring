package com.board.repository;

import com.board.dto.MainPostDto;
import com.board.dto.PostSearchDto;
import com.board.dto.QMainPostDto;
import com.board.entity.Post;
import com.board.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.board.entity.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private JPAQueryFactory queryFactory;


    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //현재 날짜로부터 이전 날짜를 구해주는 메서드
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); //현재 날짜, 시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null)
            return null;
        else if (StringUtils.equals("1d", searchDateType))
            dateTime = dateTime.minusDays(1); //1일전
        else if (StringUtils.equals("1w", searchDateType))
            dateTime = dateTime.minusWeeks(1); //1주일 전
        else if (StringUtils.equals("1m", searchDateType))
            dateTime = dateTime.minusMonths(1); //1개월 전
        else if (StringUtils.equals("6m", searchDateType))
            dateTime = dateTime.minusMonths(6); //6개월 전

        return post.regTime.after(dateTime);
    }

//    //상태를 전제로 했을 때 null이 들어있으므로 처리를 한번 해준다
//    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
//        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
//    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("title", searchBy)) {
            return post.title.like("%" + searchQuery + "%");
        }
//        else if(StringUtils.equals("createdBy", searchBy)){ //등록자 검색시
//            return post.createdBy.like("%" + searchQuery + "%");
//
//        }

        return null;
    }


    @Override
    public Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable) {

        List<Post> content = queryFactory
                .selectFrom(post)
                .where(regDtsAfter(postSearchDto.getSearchDateType()),
                        searchByLike(postSearchDto.getSearchBy(), postSearchDto.getSearchQuery()))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        long total = queryFactory.select(Wildcard.count).from(post)
                .where(regDtsAfter(postSearchDto.getSearchDateType()),
                        searchByLike(postSearchDto.getSearchBy(), postSearchDto.getSearchQuery()))
                .fetchOne();

        //pageable 객체: 한 페이지의 몇개의 게시물을 보여줄지, 시작 페이지 번호에 대한 정보를 가지고 있다.
        return new PageImpl<>(content, pageable, total);
    }

    //검색어가 빈문자열일 때를 대비
    private BooleanExpression postTitleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ?
                null : post.title.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainPostDto> getMainPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        // QueryDSL을 사용하여 쿼리 조건을 생성합니다.
        BooleanBuilder builder = new BooleanBuilder();

        // postSearchDto에서 필요한 조건을 추출하여 Predicate를 생성합니다.
        Predicate predicate = postTitleLike(postSearchDto.getSearchQuery());

        // 생성된 Predicate를 쿼리에 적용하고, Pageable을 이용하여 페이징된 결과를 가져옵니다.
        List<MainPostDto> content = queryFactory
                .select(new QMainPostDto(
                        post.id,
                        post.title,
                        post.content
                ))
                .from(post)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 가져온 결과를 Page 객체로 변환하여 반환합니다.
        long totalCount = queryFactory
                .selectFrom(post)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }
}
