package com.gdsc.colot.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QlistResponseDto {
    private List<QnA> questionList;
}

