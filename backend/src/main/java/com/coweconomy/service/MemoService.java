package com.coweconomy.service;

import com.coweconomy.api.request.MemoRequestDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.repository.ArticleRepository;
import com.coweconomy.repository.UserArticleMemoRepository;
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

    @Autowired
    UserArticleMemoRepository userArticleMemoRepository;

    /**
     * 입력 받은 메모 Request가 유요한 값인지 확인
     * @param memoRequestDto 전달 받은 메모 Request
     * @return 유효한 값인 경우 true
     * **/
    public boolean isValidMemoRequest(MemoRequestDto memoRequestDto) {
        
        String memoContent = memoRequestDto.getMemoContent();
        if (memoContent.length() > 500) {
            return false;
        }
        // memoContent 내의 특수문자 확인 (고민 중)

        return true;
    }

    /**
     * 입력 받은 Memo Request 값을 Memo Entity로 변경
     * @param memoRequestDto 전달 받은 메모 Request
     * @return memo Entity
     * **/
    public UserArticleMemo saveMemoRequestDto(MemoRequestDto memoRequestDto, Long userId) {
        
        // User 가져오기
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {

            // Article 가져오기
            Optional<Article> optionalArticle = articleRepository.findById(memoRequestDto.getArticleId());
            if (optionalArticle.isPresent()) {

                // 저장할 UserArticleMemo Entity 생성
                UserArticleMemo userArticleMemo = UserArticleMemo.builder()
                        .user(optionalUser.get())
                        .article(optionalArticle.get())
                        .memoContent(memoRequestDto.getMemoContent())
                        .memoStartRange(memoRequestDto.getMemoStartRange())
                        .memoEndRange(memoRequestDto.getMemoEndRange())
                        .memoStartIndex(memoRequestDto.getMemoStartIndex())
                        .memoEndIndex(memoRequestDto.getMemoEndIndex())
                        .memoPublicScope(memoRequestDto.isMemoPublicScope())
                        .build();

                // DB에 저장
                userArticleMemoRepository.save(userArticleMemo);

                return userArticleMemo;
            }
        }

        return null;
    }

    /**
     * memoId의 작성자가 userId가 맞는지 확인
     * @param memoId 메모 ID
     * @param userId 사용자 ID
     * @return 맞다면 true, 아니면 false
     * **/
    public boolean checkMemoWriter(Long memoId, long userId) {
        Long correctUserId = userArticleMemoRepository.findUserIdByMemoId(memoId);

        if (correctUserId == userId) return true;
        else return false;
    }

    /**
     * memoId에 해당하는 메모를 삭제
     * @param memoId 메모 ID
     * **/
    public void deleteMemo(Long memoId) {
        userArticleMemoRepository.deleteById(memoId);
    }
}