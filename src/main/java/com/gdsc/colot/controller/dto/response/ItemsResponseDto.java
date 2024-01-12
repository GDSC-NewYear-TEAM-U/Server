package com.gdsc.colot.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemsResponseDto {

    private String title;
    private String link;
    private String image;
    private String lprice;
    private String mallName;
    private String brand;
    private String maker;
    private String category1;
    private String category2;
    private String category3;
    private String category4;

    public static ItemsResponseDto of(
            String title,
            String link,
            String image,
            String lprice,
            String mallName,
            String brand,
            String maker,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        return new ItemsResponseDto(title, link, image, lprice, mallName, brand, maker, category1, category2, category3, category4);
    }

}
