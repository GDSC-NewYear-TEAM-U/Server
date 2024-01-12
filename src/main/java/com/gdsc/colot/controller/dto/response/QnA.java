package com.gdsc.colot.controller.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonSerialize
@Getter
public class QnA {
    private String question;
    private String answer1;
    private String answer2;
}
