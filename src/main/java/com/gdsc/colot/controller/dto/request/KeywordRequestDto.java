package com.gdsc.colot.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordRequestDto {
    private List<QAndA> qAndAList;
}

class QAndA {
    private String question;
    private String answer;
}