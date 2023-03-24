package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTestResultRepository extends JpaRepository<UserTestResult, Long> {




}
