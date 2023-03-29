package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserTestResultRepository extends JpaRepository<UserTestResult, Long> {

    /**
     * 오늘의 Quiz 수행 여부 확인
     * @param Long 회원 ID(seq)
     * @return boolean
     * **/
    @Query("select utr from UserTestResult utr where utr.user.userId= :userId " +
            "and utr.regtime >= :startDate and utr.regtime < :endDate")
    List<UserTestResult> findByUserUserIdAndRegtime(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
