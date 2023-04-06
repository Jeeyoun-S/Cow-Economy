package com.coweconomy.api.controller;

import com.coweconomy.api.request.CategoryArticleDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.article.dto.ArticleDetailDto;
import com.coweconomy.domain.article.dto.ArticleDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.service.ArticleService;
import com.coweconomy.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    @Autowired
    ArticleService articleService;

    // 로그인한 사용자 정보를 가져오기
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    // 오류 처리
    private BaseResponse exceptionHandling(Exception exception, String type) {
        logger.error(type + exception.toString());
        return BaseResponse.fail();
    }

    @ApiOperation(value = "인기 기사 조회", notes = "오늘 전체 기사 중 인기 기사 10개를 조회한다.")
    @GetMapping("/hot-news")
    public BaseResponse getHotArticles(HttpServletRequest request){
        List<ArticleDto> hotArticles = articleService.getHotArticles();

        return BaseResponse.success(hotArticles);
    }
    @ApiOperation(value = "카테고리별 조회", notes = "오늘 전체 기사 중 인기 기사 10개를 조회한다.")
    @GetMapping("/category-news")
    public BaseResponse getCategoryArticles(HttpServletRequest request){
        List<List<ArticleDto>> articles = articleService.getCategoryArticles();
        return BaseResponse.success(articles);
    }

    @ApiOperation(value = "키워드 검색", notes = "키워드로 기사를 조회한다.")
    @PostMapping("/search")
    public BaseResponse searchByKeyword(HttpServletRequest request, @RequestParam String keyword, @RequestBody CategoryArticleDto categoryLast ){
        System.out.println("키워드: "+ keyword);
        System.out.println("금융: "+ categoryLast.getFinance());
        System.out.println("주식: "+ categoryLast.getStock());
        System.out.println("산업: "+ categoryLast.getIndustry());
        System.out.println("중기/벤처: "+ categoryLast.getVenture());
        System.out.println("부동산: "+ categoryLast.getEstate());
        System.out.println("글로벌경제: "+ categoryLast.getWorldwide());
        System.out.println("생활경제: "+ categoryLast.getLife());
        System.out.println("경제 일반: "+ categoryLast.getCommon());
        Long[] LastIdList = categoryLast.setCategoryLast(categoryLast);

        HashMap<String, List<?>> result = articleService.getByKeywordArticles(keyword,LastIdList);
        System.out.println("전체 기사 길이: "+result.get("articles").size());

        for (ArticleDto article:(List<ArticleDto>)result.get("articles")) {
            System.out.println("기사: "+ article.getArticleId()+"     " + article.getArticleCategory()+"      "
                    +article.getArticlePress()+"        "+article.getArticleTitle()+"     "+article.getArticleRegtime() );
        }
        return BaseResponse.success(result);
    }
    @ApiOperation(value = "기사 상세 정보", notes = "기사 상세페이지에 보여줄 정보를 모두 조회한다.")
    @GetMapping("/{articleId}")
    public BaseResponse getArticleDetail(HttpServletRequest request, @PathVariable("articleId") Long articleId) {
        
        // 로그인이 안 된 경우 0으로 설정
        Long userId = 0L;
        
        // 토큰 검사 후 토큰이 있다면 로그인한 사용자로
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();
        } catch (NullPointerException e) {
            // 로그인 하지 않은 상태
        }

        // 기사 정보 가져오기
        ArticleDetailDto articleDetailDto = articleService.getArticleDetail(articleId, userId);

        if (articleDetailDto != null) {

            // 기사 조회수 증가
            articleService.increaseHits(articleId);

            return BaseResponse.success(articleDetailDto);
        } else {
            return BaseResponse.fail();
        }
    }

    @ApiOperation(value = "기사 읽음 처리", notes = "사용자 기사를 읽은 것으로 처리한다.")
    @PostMapping("/{articleId}")
    public BaseResponse addUserArticle(HttpServletRequest request, @PathVariable("articleId") Long articleId) {

        try {
            // 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

            boolean result = articleService.addUserArticle(articleId, userId);

            return BaseResponse.success(result);
        } catch (Exception exception) {
            return exceptionHandling(exception, "# RM #");
        }
    }
}
