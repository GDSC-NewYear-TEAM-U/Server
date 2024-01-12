package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.KeywordResponseDto;
import com.gdsc.colot.controller.dto.response.QnAResponseDto;

import java.util.List;

public interface KeywordService {
    List<QnAResponseDto> getQuestion();

    List<String> getKeyword(List<KeywordRequestDto> keywordRequestDtoList);

    String getImage(String keyword);
}
