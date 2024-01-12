package com.gdsc.colot.controller;

import com.gdsc.colot.common.dto.BaseResponse;
import com.gdsc.colot.controller.dto.response.ItemsResponseDto;
import com.gdsc.colot.exception.SuccessCode;
import com.gdsc.colot.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods")
    public BaseResponse<List<ItemsResponseDto>> searchItems(@RequestParam("title") String title) throws IOException, ParseException {
        final List<ItemsResponseDto> data = goodsService.searchItems(title);
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }

}
