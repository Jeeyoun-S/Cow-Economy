package com.coweconomy.service;

import com.coweconomy.api.controller.UserController;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.repository.UserArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserArticleRepository userArticleRepository;

    /**
     * 회원이 일주일 이내에 읽은 기사 조회
     * @param userId 조회할 회원 ID
     * @return List<UserArticle> 회원이 읽은 기사 List
     * **/
    public List<UserArticleDto> getUserReadArticle(Long userId) {
        List<UserArticle> userArticles = new ArrayList<>();
        Date oneWeekAgo = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
//        userArticles = userArticleRepository.findByUserArticleId(userId);
        logger.info("#21# 일주일 전: {}", oneWeekAgo);
        userArticles = userArticleRepository.findByArticle(userId, oneWeekAgo);
//        userArticles = userArticleRepository.findByArticle(userId);

        List<UserArticleDto> result = userArticles.stream().map(u->new UserArticleDto(u)).collect(Collectors.toList());
        return result;
    }

}
