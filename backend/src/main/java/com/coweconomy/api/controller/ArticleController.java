package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.article.dto.ArticleDetailDto;
import com.coweconomy.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @ApiOperation(value = "기사 상세 정보", notes = "기사 상세페이지에 보여줄 정보를 모두 조회한다.")
    @GetMapping("/{articleId}")
    public BaseResponse getArticleDetail(@PathVariable("articleId") Long articleId) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        Long userId = 2L;
        
        // 기사 정보 가져오기
        ArticleDetailDto articleDetailDto = articleService.getArticleDetail(articleId, userId);

        // 기사 조회수 증가
        articleService.increaseHits(articleId);

        return BaseResponse.success(articleDetailDto);
    }

    @ApiOperation(value = "기사 읽음 처리", notes = "사용자 기사를 읽은 것으로 처리한다.")
    @PostMapping("/{articleId}")
    public BaseResponse addUserArticle(@PathVariable("articleId") Long articleId) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        Long userId = 1L;
        boolean result = articleService.addUserArticle(articleId, userId);
        return BaseResponse.success(result);
    }
}
