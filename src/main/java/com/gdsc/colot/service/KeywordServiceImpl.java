package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.QnA;
import com.gdsc.colot.controller.dto.response.QnAResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final int Q_CNT = 8;

    public static final List<QnA> qnAList = List.of(
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2"),
            new QnA("q", "a1", "a2")
    );

    @Override
    public List<QnAResponseDto> getQuestion() { // 8개 질문 리스트 보내기
        List<Integer> numbers = new ArrayList<>();
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (numbers.size() < Q_CNT) {
            int randomNumber = (int) (Math.random() * qnAList.size());
            if (uniqueNumbers.add(randomNumber))
                numbers.add(randomNumber);
        }

        List<QnAResponseDto> questionList = new ArrayList<>();
        for (int n : numbers)
            questionList.add(
                    new QnAResponseDto(qnAList.get(n).getQuestion(), qnAList.get(n).getAnswer1(), qnAList.get(n).getAnswer2())
            );

        return questionList;

    }


    @Override
    public String getKeyword(KeywordRequestDto keywordRequestDto) { // 추출된 키워드 보내기
        String requestURL = "";
        String API_KEY = "";

        String keyword = null;

//        try {
//            HttpClient client = HttpClientBuilder.create().build();
//            HttpGet getRequest = new HttpGet(requestURL);
//            getRequest.addHeader("x-api-key", API_KEY);
//
//            HttpResponse response = client.execute(getRequest);
//
//            //Response 출력
//            if (response.getStatusLine().getStatusCode() == 200) {
//                ResponseHandler<String> handler = new BasicResponseHandler();
//                String body = handler.handleResponse(response);
//                keyword = body;
//            } else {
//                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
//            }
//
//        } catch (Exception e){
//            System.err.println(e.toString());
//        }

        return keyword;
    }
}
