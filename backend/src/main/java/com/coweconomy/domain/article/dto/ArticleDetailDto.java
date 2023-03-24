package com.coweconomy.domain.article.dto;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.dto.UserArticleMemoDetailDto;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.domain.word.dto.ArticleWordDto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ArticleDetailDto extends ArticleDto {

    // 기사 카테고리
    private String articleCategory;

    // 기자명
    private String articleEditor;

    // 기사 URL
    private String articleUrl;
    
    // 기사 내용
    private String articleContent;

    // 기사에 해당하는 내가 쓴 메모 리스트
    private List<UserArticleMemoDto> userArticleMemoListMine;

    // 기사에 해당하는 다른 사람의 메모 리스트
    private List<UserArticleMemoDto> userArticleMemoListOther;

    // 기사에 해당하는 모든 단어 리스트
    private Map<String, ArticleWordDto> articleWordList;

    // 기사에 해당하는 모든 관련 기사 리스트
    private List<RelatedArticleDto> relatedArticleList;

    // 기사를 읽었는지 확인
    private boolean isReading;

    public ArticleDetailDto(Article article, Long userId) {
        super(article);
        this.articleEditor = article.getArticleEditor();
        this.articleUrl = article.getArticleUrl();
        this.articleContent = article.getArticleContent();

        List<UserArticleMemo> memoList = article.getUserArticleMemoList();
        if (userId >= 0) {
            this.userArticleMemoListMine = memoList.stream().filter(m -> m.getUser().getUserId() == userId).map(m -> new UserArticleMemoDto(m)).collect(Collectors.toList());
            this.userArticleMemoListOther = memoList.stream().filter(m -> m.getUser().getUserId() != userId && m.getMemoPublicScope()).map(m -> new UserArticleMemoDetailDto(m)).collect(Collectors.toList());
        } else {
            this.userArticleMemoListOther = memoList.stream().filter(m -> m.getMemoPublicScope()).map(m -> new UserArticleMemoDetailDto(m)).collect(Collectors.toList());
        }

        this.articleWordList = article.getArticleWordList().stream().collect(Collectors.toMap(m -> m.getEconomyWord().getWord(), m-> new ArticleWordDto(m)));
        
        // 관련 기사 구현 필요한 곳
        // this.relatedArticleList
    }
}
