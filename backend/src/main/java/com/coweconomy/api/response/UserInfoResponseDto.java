package com.coweconomy.api.response;

import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 유저 마이페이지 API 요청에 대한 응답값(Response) 정의
 */
@Data
public class UserInfoResponseDto {

    // 회원 정보
    UserDto user;

    // 회원이 작성한 memo
    List<UserArticleMemoDto> memoDtoList;

    // 회원 그래프 정보


    public UserInfoResponseDto(UserDto user, List<UserArticleMemoDto> memoDtoList) {
        this.user = user;
        this.memoDtoList = memoDtoList;
    }
}
