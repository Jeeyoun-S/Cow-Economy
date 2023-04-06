package com.coweconomy.service;

import com.coweconomy.domain.article.dto.ArticleDetailDto;
import com.coweconomy.domain.article.dto.ArticleDto;
import com.coweconomy.domain.article.dto.RelatedArticleDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.word.dto.EconomyWordDto;
import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import com.coweconomy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final UserArticleRepository userArticleRepository;
    private final UserArticleMemoRepository userArticleMemoRepository;
    private final EconomyWordRepository economyWordRepository;
    private final RelatedArticleRepository relatedArticleRepository;


    public List<ArticleDto> getHotArticles(){
        List<Article> todayArticles = articleRepository.findByTodayHotArticles();
        List<ArticleDto> result = todayArticles.stream().map(a->new ArticleDto(a)).collect(Collectors.toList());
        return result;
    }

    public List<List<ArticleDto>> getCategoryArticles(){
        List<Article> hot = articleRepository.findByCategoryHotArticle();
        List<Article> recent = articleRepository.findByCategoryRecentArticle();
        List<List<ArticleDto>> result = new ArrayList<>();
        List<ArticleDto> hotArticles = hot.stream().map(a->new ArticleDto(a)).collect(Collectors.toList());
        List<ArticleDto> recentArticles = recent.stream().map(a->new ArticleDto(a)).collect(Collectors.toList());
        result.add(hotArticles);
        result.add(recentArticles);
        System.out.println("카테고리별 뉴스 전체길이: " + result.size());
        return result;
    }

    public HashMap<String,List<?>> getByKeywordArticles(String keyword, Long[] lastArticleId){
        HashMap<String, List<?>> map = new HashMap<>();
        List<Article> articles = articleRepository.findByKeywordSearch(keyword, lastArticleId[0],lastArticleId[1], lastArticleId[2],
                lastArticleId[3],lastArticleId[4],lastArticleId[5],lastArticleId[6],lastArticleId[7]);
        List<ArticleDto> result = articles.stream().map(a->new ArticleDto(a)).collect(Collectors.toList());
        //각 카테고리별 마지막 기사아이디 조회
        String [] categorys = {"금융", "증권", "산업/재계", "중기/벤처", "부동산", "글로벌 경제", "생활경제", "경제 일반"};
        List<Long> categoryLast = new ArrayList<>();

        for (String category: categorys) {
            List<ArticleDto> articleList = result.stream().filter(r -> r.getArticleCategory().equals(category)).sorted((o1, o2) -> -o1.getArticleId().compareTo(o2.getArticleId())).collect(Collectors.toList());
            System.out.println(category+" 카테고리 정렬");
            if(articleList.size()>0){
                System.out.println("마지막 기사 아이디: "+articleList.get(articleList.size()-1).getArticleId());
                categoryLast.add(articleList.get(articleList.size()-1).getArticleId());
            }
            else    categoryLast.add(0L);

        }
//        System.out.println("전체 마지막 기사 아이디: " + result.get(result.size()-1).getArticleId());
        map.put("articles", result);
        map.put("categoryLast", categoryLast);
        return map;
    }
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
                        Optional<EconomyWord> optionalEconomyWord = economyWordRepository.findById(Long.parseLong(id.strip()));
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

            //관련 기사 설정
            List<RelatedArticleDto> relatedArticleDtoList = new ArrayList<>();
            //관련 기사의 아이디 문자열 분리
            if (article.getRelatedArticleList().size() > 0) {
                StringTokenizer tokens = new StringTokenizer(article.getRelatedArticleList().get(0).getSubArticleId(), ",");
                while(tokens.hasMoreTokens()){
                    //기사 정보 조회
                    Optional<Article> searchArticle = articleRepository.findById(Long.parseLong(tokens.nextToken()));
                    if(searchArticle.isPresent()){
                        //entity -> DTO (article_id, article_title, article_thumbnail)
                        Long relatedArticleId = searchArticle.get().getArticleId();
                        String relatedArticleTitle = searchArticle.get().getArticleTitle();
                        String relatedArticleThumbnail = searchArticle.get().getArticleThumbnail();
                        relatedArticleDtoList.add(new RelatedArticleDto(relatedArticleId, relatedArticleTitle, relatedArticleThumbnail));
                    }
                }
            }

            //전체 List DTO로 보내주기
            articleDetailDto.updateRelatedArticle(relatedArticleDtoList);

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
                    user.increaseExperience(5);
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
