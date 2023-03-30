package com.coweconomy.domain.word.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class EconomyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("경제 용어 ID")
    private Long wordId;

    @Column(length = 30)
    @NotNull
    @Comment("경제 단어")
    private String word;

    @Column(length = 100)
    @Comment("경제 단어 소제목")
    private String subword;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    @Comment("경제 단어 설명")
    private String wordExpl;
//
//    @OneToMany(mappedBy = "economyWord", cascade = CascadeType.ALL)
//    private List<ArticleWord> articleWordList = new ArrayList<>();
}
