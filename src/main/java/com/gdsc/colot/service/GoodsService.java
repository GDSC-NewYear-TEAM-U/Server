package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.response.ItemsResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Service
@Slf4j
public class GoodsService {

    public List<ItemsResponseDto> searchItems(String title) throws IOException, ParseException {

        String apiUrl = "https://openapi.naver.com/v1/search/shop.json"
                + "?query=" + URLEncoder.encode(title, StandardCharsets.UTF_8)
                + "&display=5"
                + "&sort=sim";

        // URL 객체 생성
        URL url = new URL(apiUrl);

        // HttpURLConnection 객체 생성
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 헤더 추가
        connection.setRequestProperty("X-Naver-Client-Id", "yGSKp4LNpkAbTm1vzH7A");// 예시 헤더, 필요한 헤더에 따라 추가/변경
        connection.setRequestProperty("X-Naver-Client-Secret", "lcyVdhTDoR");

        // API 응답 읽기
        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder responseStringBuilder = new StringBuilder();

        String line;
        while ((line = bf.readLine()) != null) {
            responseStringBuilder.append(line);
        }

        // 응답 출력
        String result = responseStringBuilder.toString();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONArray items = (JSONArray) jsonObject.get("items");

        List<ItemsResponseDto> response = new ArrayList<>();

        for (Object o : items) {
            JSONObject item = (JSONObject) o;
            String title2 = ((String) item.get("title")).length() == 0 ? "준비중" : (String) item.get("title");
            String link = ((String) item.get("link")).length() == 0 ? "준비중" : (String) item.get("link");
            String image = ((String) item.get("image")).length() == 0 ? "준비중" : (String) item.get("image");
            String lprice = ((String) item.get("lprice")).length() == 0 ? "준비중" : (String) item.get("lprice");
            String mallName = ((String) item.get("mallName")).length() == 0 ? "준비중" : (String) item.get("mallName");
            String brand = ((String) item.get("brand")).length() == 0 ? "준비중" : (String) item.get("brand");
            String maker = ((String) item.get("maker")).length() == 0 ? "준비중" : (String) item.get("maker");
            String category1 = ((String) item.get("category1")).length() == 0 ? "준비중" : (String) item.get("category1");
            String category2 = ((String) item.get("category2")).length() == 0 ? "준비중" : (String) item.get("category2");
            String category3 = ((String) item.get("category3")).length() == 0 ? "준비중" : (String) item.get("category3");
            String category4 = ((String) item.get("category4")).length() == 0 ? "준비중" : (String) item.get("category4");

            response.add(
                    ItemsResponseDto.of(
                            title2, link, image, lprice, mallName, brand, maker, category1, category2, category3, category4
                    )
            );
        }

        return response;

    }

}
