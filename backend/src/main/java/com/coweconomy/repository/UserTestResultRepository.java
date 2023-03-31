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
     * @param userId 회원 ID(seq), startDate 시작 날짜, endDate 끝 날짜
     * @return boolean
     * **/
    @Query("select utr from UserTestResult utr where utr.user.userId= :userId " +
            "and utr.regtime >= :startDate and utr.regtime < :endDate")
    List<UserTestResult> findByUserUserIdAndRegtime(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * userId에 해당되는 연-월 Quiz에서 맞춘 경제용어 카테고리 조회
     * @param userId 회원 id(seq), year 연, month 월
     * @return List<Object[]>
     * **/
    @Query("select utr.article.articleCategory, count(*) " +
            "from UserTestResult utr " +
            "where utr.testResultFlag = true " +
            "and utr.user.userId = :userId " +
            "and year(utr.regtime) = :year and month(utr.regtime) = :month " +
            "group by utr.article.articleCategory")
    List<Object[]> findArticleCategoryByUserIdAndYearAndMonth(@Param("userId") Long userId, @Param("year") Integer year, @Param("month") Integer month);

}
