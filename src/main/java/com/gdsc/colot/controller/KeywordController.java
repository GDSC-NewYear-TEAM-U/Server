package com.gdsc.colot.controller;

import com.gdsc.colot.common.dto.BaseResponse;
import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.KeywordResponseDto;
import com.gdsc.colot.controller.dto.response.QnAResponseDto;
import com.gdsc.colot.exception.SuccessCode;
import com.gdsc.colot.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/api/v2/qlist")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<QnAResponseDto>> questionList() {
        final List<QnAResponseDto> questionList = keywordService.getQuestion();
        return BaseResponse.success(SuccessCode.GET_SUCCESS, questionList);
    }

    @GetMapping("/api/v2/keyword")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<KeywordResponseDto> keyword(
            @RequestParam("q1") Integer q1,
            @RequestParam("a1") Integer a1,
            @RequestParam("q2") Integer q2,
            @RequestParam("a2") Integer a2,
            @RequestParam("q3") Integer q3,
            @RequestParam("a3") Integer a3,
            @RequestParam("q4") Integer q4,
            @RequestParam("a4") Integer a4,
            @RequestParam("q5") Integer q5,
            @RequestParam("a5") Integer a5,
            @RequestParam("q6") Integer q6,
            @RequestParam("a6") Integer a6,
            @RequestParam("q7") Integer q7,
            @RequestParam("a7") Integer a7,
            @RequestParam("q8") Integer q8,
            @RequestParam("a8") Integer a8
    ) {
        List<KeywordRequestDto> requestDtos = new ArrayList<>();
        requestDtos.add(new KeywordRequestDto(q1, a1));
        requestDtos.add(new KeywordRequestDto(q2, a2));
        requestDtos.add(new KeywordRequestDto(q3, a3));
        requestDtos.add(new KeywordRequestDto(q4, a4));
        requestDtos.add(new KeywordRequestDto(q5, a5));
        requestDtos.add(new KeywordRequestDto(q6, a6));
        requestDtos.add(new KeywordRequestDto(q7, a7));
        requestDtos.add(new KeywordRequestDto(q8, a8));
        final KeywordResponseDto data = keywordService.getKeyword(requestDtos);
//        final KeywordResponseDto data = KeywordResponseDto.of("되나요", "됩니까", "되나용");
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }

    @GetMapping("/api/v2/vertexai")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> test() throws IOException {
        final String data = keywordService.vertexAI();
        return BaseResponse.success(SuccessCode.LOGIN_SUCCESS, data);
    }
}
