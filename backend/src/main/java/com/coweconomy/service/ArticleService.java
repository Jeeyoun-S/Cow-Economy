package com.coweconomy.service;

import com.coweconomy.domain.article.dto.ArticleDetailDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.word.dto.EconomyWordDto;
import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import com.coweconomy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserArticleRepository userArticleRepository;

    @Autowired
    UserArticleMemoRepository userArticleMemoRepository;

    @Autowired
    EconomyWordRepository economyWordRepository;

    /**
     * @param articleId 기사 ID
     * **/
    public ArticleDetailDto getArticleDetail(Long articleId, Long userId) {

        // articleId로 article 정보 가져오기
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            ArticleDetailDto articleDetailDto = new ArticleDetailDto(article, userId);

            // 기사에 있는 단어 뜻 가져오기
            Map<String, EconomyWordDto> economyWordMap = new HashMap<>();
            List<ArticleWord> articleWordList = article.getArticleWordList();
            for (ArticleWord articleWord : articleWordList) {
                String subWordIds = articleWord.getSubWordId();
                if (!subWordIds.isEmpty()) {
                    String[] subwordList = articleWord.getSubWordId().split(",", -1);
                    for (String id : subwordList) {
                        Optional<EconomyWord> optionalEconomyWord = economyWordRepository.findById(Long.parseLong(id));
                        if (optionalEconomyWord.isPresent()) {
                            EconomyWord economyWord = optionalEconomyWord.get();
                            economyWordMap.put(economyWord.getWord(), new EconomyWordDto(economyWord));
                        }
                    }
                }
            }
            articleDetailDto.updateArticleWord(economyWordMap);

            // 사용자가 로그인한 상태라면
            if (userId >= 0) {
                // 사용자 ID로 사용자 Entity Select
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {

                    // 읽은 기사 Table에서 사용자, 기사로 컬럼 가져오기
                    Optional<UserArticle> optionalUserArticle = userArticleRepository.findByUserAndArticle(optionalUser.get(), article);
                    articleDetailDto.setReading(optionalUserArticle.isPresent());
                }
            }

            return articleDetailDto;
        }

        return null;
    }

    /**
     * articleId에 해당하는 기사를 userId의 사용자가 읽은 것으로 변경
     * @param articleId 기사 ID
     * @param userId 사용자 ID
     * @return 변경 완료 true, 변경 실패 false
     * **/
    public boolean addUserArticle(Long articleId, Long userId) {

        // 기사 ID로 기사 정보 가져오기
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {

            // 사용자 ID로 사용자 정보 가져오기
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                // 이미 DB에 존재하는지 확인
                Optional<UserArticle> userArticleOptional = userArticleRepository.findByUserAndArticle(optionalUser.get(), optionalArticle.get());
                if (!userArticleOptional.isPresent()) {

                    // userArticle Entity 생성
                    UserArticle userArticle = UserArticle.builder()
                            .user(optionalUser.get())
                            .article(optionalArticle.get())
                            .build();

                    // DB에 insert
                    userArticleRepository.save(userArticle);

                    // 경험치 증가
                    User user = optionalUser.get();
                    user.increaseExperience();
                    userRepository.save(user);

                    return true;
                }
            }
        }
        return false;
    }

    public void increaseHits(Long articleId) {  
        // DB에서 articleId로 article 가져오기
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            // 조회수 +1 증가시키기
            article.increaseHits();
            // 증가시킨 조회수 DB에 반영하기
            articleRepository.save(article);
        }
    }
}
