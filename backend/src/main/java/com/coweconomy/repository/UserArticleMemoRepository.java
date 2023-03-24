package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserArticleMemoRepository extends JpaRepository<UserArticleMemo, Long> {

    /**
     * userId에 해당하는 사용자가 작성한 모든 Memo 가져오기
     * @param userId 사용자 ID
     * @return List<UserArticleMemo> 사용자의 모든 메모 리스트
     * **/
    List<UserArticleMemo> findAllByUser_UserId(Long userId);
}