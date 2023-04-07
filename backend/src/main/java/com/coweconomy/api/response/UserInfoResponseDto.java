package com.coweconomy.api.response;

import com.coweconomy.domain.article.dto.ArticleMemoDto;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 유저 마이페이지 API 요청에 대한 응답값(Response) 정의
 */
@Data
public class UserInfoResponseDto {

    // 회원 정보
    UserDto user;

    // 회원이 작성한 memo
    List<ArticleMemoDto> memoDtoList;

    // 6개월 간 읽은 기사 수 정보
    List<Object[]> articleCntList;

    // 회원이 읽은 기사의 카테고리 별 기사 수
    List<Object[]> articleCategoryCnt;

    // 회원이 Quiz에서 맞춘 경제용어 카테고리 별 개수 조회
    List<Object[]> quizPassWordCategoryCnt;

    public UserInfoResponseDto(
            UserDto user,
            List<ArticleMemoDto> memoDtoList,
            List<Object[]> articleCntList,
            List<Object[]> articleCategoryCnt,
            List<Object[]> quizPassWordCategoryCnt) {
        this.user = user;
        this.memoDtoList = memoDtoList;
        this.articleCntList = articleCntList;
        this.articleCategoryCnt = articleCategoryCnt;
        this.quizPassWordCategoryCnt = quizPassWordCategoryCnt;
    }
}
