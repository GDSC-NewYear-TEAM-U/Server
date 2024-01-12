package com.gdsc.colot.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QnAResponseDto {

    private String question;
    private String answer1;
    private String answer2;

    public static QnAResponseDto of(String question, String answer1, String answer2) {
        return new QnAResponseDto(question, answer1, answer2);
    }

}

