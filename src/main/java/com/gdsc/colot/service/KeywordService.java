package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.QlistResponseDto;

public interface KeywordService {
    QlistResponseDto getQuestion();

    String getKeyword(KeywordRequestDto keywordRequestDto);
}
