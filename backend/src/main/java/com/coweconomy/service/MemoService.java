package com.coweconomy.service;

import com.coweconomy.api.request.MemoRequestDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.repository.ArticleRepository;
import com.coweconomy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    /**
     * 입력 받은 메모 Request가 유요한 값인지 확인
     * @param memoRequestDto 전달 받은 메모 Request
     * @return 유효한 값인 경우 true
     * **/
    public boolean isValidMemoRequest(MemoRequestDto memoRequestDto) {

//        long articleId = memoRequestDto.getArticleId();
        // 존재하는 기사 ID인지 확인?

        String memoContent = memoRequestDto.getMemoContent();
        if (memoContent.length() > 500) {
            return false;
        }
        // memoContent 내의 특수문자...? 그냥?

        return true;
    }

    /**
     * 입력 받은 Memo Request 값을 Memo Entity로 변경
     * @param memoRequestDto 전달 받은 메모 Request
     * @return memo entity
     * **/
    public UserArticleMemo changeMemoRequestToEntity(MemoRequestDto memoRequestDto) {

//        User user = userRepository.findByUserId();

        Optional<Article> optionalArticle = articleRepository.findByArticleId(memoRequestDto.getArticleId());
        if (optionalArticle.isPresent()) {

            UserArticleMemo userArticleMemo = UserArticleMemo.builder()
//                    .user()
                    .article(optionalArticle.get())
                    .memoContent(memoRequestDto.getMemoContent())
                    .memoStartPoint(memoRequestDto.getMemoStartPoint())
                    .memoEndPoint(memoRequestDto.getMemoEndPoint())
                    .memoScrollPoint(memoRequestDto.getMemoScrollPoint())
                    .memoPublicScope(memoRequestDto.isMemoPublicScope())
                    .build();

            return userArticleMemo;
        }

        return null;
    }
}