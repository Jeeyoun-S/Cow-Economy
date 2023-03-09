package com.coweconomy.domain.word.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long wordId;

    @Column(length = 30)
    @NotNull
    private String word;

    @Column(length = 100)
    private String subword;
    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    private String wordExpl;

    @OneToMany(mappedBy = "economyWord", cascade = CascadeType.ALL)
    private List<ArticleWord> articleWordList = new ArrayList<>();
}
