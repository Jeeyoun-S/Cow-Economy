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
     * 입력 받은 Memo Request 값을 DB에 저장
     * @param memoRequestDto 전달 받은 메모 Request
     * @return memo Entity
     * **/
    public UserArticleMemo addMemo(MemoRequestDto memoRequestDto, Long userId, Long articleId) {

        // User 가져오기
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {

            // Article 가져오기
            Optional<Article> optionalArticle = articleRepository.findById(articleId);
            if (optionalArticle.isPresent()) {

                // 저장할 UserArticleMemo Entity 생성
                UserArticleMemo userArticleMemo = UserArticleMemo.builder()
                    .user(optionalUser.get())
                    .article(optionalArticle.get())
                    .memoPublicScope(memoRequestDto.isMemoPublicScope())
                    .build();

                // memoRequestDto에 받아온 값 넣기
                userArticleMemo.updateMemo(memoRequestDto);

                // DB에 insert
                UserArticleMemo returnMemo =  userArticleMemoRepository.saveAndFlush(userArticleMemo);
                return returnMemo;
            }
        }

        return null;
    }

    /**
     * 전달 받은 Memo Request 값을 DB에 저장
     * @param userArticleMemo 기존에 저장돼 있던 Entity
     * @param memoRequestDto 전달 받은 메모 Request
     * @param memoId 메모 ID
     * @return memo Entity
     * **/
    public UserArticleMemo modifyMemo(UserArticleMemo userArticleMemo, MemoRequestDto memoRequestDto, Long memoId) {

        // DB에서 가져온 기존 memo의 값 update하기
        userArticleMemo.updateMemo(memoRequestDto);

        // DB에 update
        UserArticleMemo returnMemo = userArticleMemoRepository.save(userArticleMemo);
        return returnMemo;
    }

    /**
     * 전달 받은 Memo Entity의 공개 여부 변경
     * @param userArticleMemo 기존에 저장돼 있던 Entity
     * @return boolean 공개 여부
     * **/
    public boolean modifyMemoScope(UserArticleMemo userArticleMemo) {

        // memoPublicScope 변경
        userArticleMemo.updateMemoScope();

        // DB에 update
        userArticleMemoRepository.save(userArticleMemo);
        return userArticleMemo.getMemoPublicScope();
    }

    /**
     * memoId의 작성자가 userId가 맞는지 확인
     * @param memoId 메모 ID
     * @param userId 사용자 ID
     * @return 맞다면 true, 아니면 false
     * **/
    public UserArticleMemo checkMemoWriter(Long memoId, Long userId) {
        
        // memoId로 memo 값 가져오기
        Optional<UserArticleMemo> memo = userArticleMemoRepository.findById(memoId);
        if (memo.isPresent()) {
            Long correctId = memo.get().getUser().getUserId();
            if (correctId == userId) return memo.get();
        }

        return null;
    }

    /**
     * memoId에 해당하는 메모를 삭제
     * @param userArticleMemo 삭제할 메모 Entity
     * **/
    public void deleteMemo(UserArticleMemo userArticleMemo) {
        userArticleMemoRepository.delete(userArticleMemo);
    }
}