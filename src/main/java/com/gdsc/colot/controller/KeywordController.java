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

    @PostMapping("/api/v2/keyword")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<KeywordResponseDto> keyword(@RequestBody @Valid List<KeywordRequestDto> keywordRequestDtoList) {
        final List<String> tmp = keywordService.getKeyword(keywordRequestDtoList);
        final String keyword = tmp.get(0);
        final String detail = tmp.get(1);
        final String image = keywordService.getImage(keyword);
        final KeywordResponseDto keywordResponseDto = new KeywordResponseDto(keyword, detail, image);
        return BaseResponse.success(SuccessCode.GET_SUCCESS, keywordResponseDto);
    }
}
