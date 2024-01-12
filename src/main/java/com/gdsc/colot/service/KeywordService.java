package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.KeywordResponseDto;
import com.gdsc.colot.controller.dto.response.QnAResponseDto;

import java.io.IOException;
import java.util.List;

public interface KeywordService {
    List<QnAResponseDto> getQuestion();

    String vertexAI() throws IOException;
    List<String> getKeyword(List<KeywordRequestDto> keywordRequestDtoList);

    String getImage(String keyword);
}
