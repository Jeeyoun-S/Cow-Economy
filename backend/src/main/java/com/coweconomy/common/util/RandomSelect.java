package com.coweconomy.common.util;

import java.util.*;
import java.util.stream.Collectors;

public class RandomSelect {

    /**
     * 범위 내의 숫자 중 랜덤으로 7개 선택 (중복 X)
     * - 오늘의 Quiz 문제 출제 시 사용
     **/
    public List<Integer> getRandomSelect(int range) {
        Set<Integer> setIdx = new HashSet<>();
//        List<Integer> selectIdx = new ArrayList<>();

        while (setIdx.size() < 7) {
            int random = (int) (Math.random() * range); // 0 ~ range 숫자 중 랜덤으로 선택
            setIdx.add(random);
        }
        List<Integer> selectIdx = new ArrayList<>(setIdx);
        Collections.sort(selectIdx);

        return selectIdx;
    }

}
