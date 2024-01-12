package com.gdsc.colot.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QnAResponseDto {
    private Integer question_id;
    private String question;
    private String answer1;
    private String answer2;

    public static QnAResponseDto of(Integer question_id, String question, String answer1, String answer2) {
        return new QnAResponseDto(question_id, question, answer1, answer2);
    }

}

