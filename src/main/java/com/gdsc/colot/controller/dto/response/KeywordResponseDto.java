package com.gdsc.colot.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordResponseDto {

    private String keyword;
    private String detail;
    private String image;

    public static KeywordResponseDto of(String keyword, String detail, String image) {
        return new KeywordResponseDto(keyword, detail, image);
    }

}
