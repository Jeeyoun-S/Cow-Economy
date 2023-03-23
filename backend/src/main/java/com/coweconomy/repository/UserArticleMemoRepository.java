package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserArticleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserArticleMemoRepository extends JpaRepository<UserArticleMemo, Long> {

    /**
     * userId에 해당하는 사용자가 작성한 모든 Memo 가져오기
     * @param userId 사용자 ID
     * @return List<UserArticleMemo> 사용자의 모든 메모 리스트
     * **/
    List<UserArticleMemo> findAllByUser_UserId(Long userId);

    /**
     * memoId의 작성자 userId 가져오기
     * @param memoId 메모 ID
     * @return Long userID 사용자 ID
     * **/
    Long findUserIdByMemoId(Long memoId);
}