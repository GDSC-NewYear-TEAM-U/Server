package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.KeywordResponseDto;
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

    public static final List<QnAResponseDto> Q_AA_LIST = List.of(
            new QnAResponseDto(0, "q1", "a11", "a21"),
            new QnAResponseDto(1, "q2", "a12", "a22"),
            new QnAResponseDto(2, "q3", "a13", "a23"),
            new QnAResponseDto(3, "q4", "a14", "a24"),
            new QnAResponseDto(4, "q5", "a15", "a25"),
            new QnAResponseDto(5, "q6", "a16", "a26"),
            new QnAResponseDto(6, "q7", "a17", "a27"),
            new QnAResponseDto(7, "q8", "a18", "a28"),
            new QnAResponseDto(8, "q9", "a19", "a29"),
            new QnAResponseDto(9, "q10", "a110", "a210"),
            new QnAResponseDto(10, "q11", "a111", "a211"),
            new QnAResponseDto(11, "q12", "a112", "a212")
    );

    @Override
    public List<QnAResponseDto> getQuestion() { // 8개 질문 리스트 보내기
        List<Integer> numbers = new ArrayList<>();
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (numbers.size() < Q_CNT) {
            int randomNumber = (int) (Math.random() * Q_AA_LIST.size());
            if (uniqueNumbers.add(randomNumber))
                numbers.add(randomNumber);
        }

        List<QnAResponseDto> questionList = new ArrayList<>();
        for (int n : numbers)
            questionList.add(
                    new QnAResponseDto(n, Q_AA_LIST.get(n).getQuestion(), Q_AA_LIST.get(n).getAnswer1(), Q_AA_LIST.get(n).getAnswer2())
            );

        return questionList;

    }


    @Override
    public List<String> getKeyword(List<KeywordRequestDto> keywordRequestDtoList) { // 추출된 키워드 보내기
//        String reqText = "너는 사용자가 제공한 연인의 정보들로 연인의 선물을 추천한다.\n" +
//                "정확한 근거 없는 내용은 피한다.\n" +
//                "소개와 설명을 제공한다.\n" +
//                "\n";
//
//        for (int i = 0; i < keywordRequestDtoList.size(); i++) {
//            Integer index = keywordRequestDtoList.get(i).getQuestion_id();
//            reqText += "input: " + Q_AA_LIST.get(index).getQuestion();
//            reqText += "\noutput: " + (keywordRequestDtoList.get(i).getAnswer() == 0 ? Q_AA_LIST.get(index).getAnswer1() : Q_AA_LIST.get(index).getAnswer2());
//            reqText += "\n\n";
//        }
//
//        reqText += "input: 연인에게 선물할 물건명 한가지와 추천한 선물에 대한 설명을 제공한다.\n" +
//                "output:\n";




        String requestURL = "";
        String API_KEY = "";

        List<String> response = new ArrayList<>();
        response.add("key");
        response.add("det");

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
//                response.add(keyword);
        //      response.add(detail);
//            } else {
//                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
//            }
//
//        } catch (Exception e){
//            System.err.println(e.toString());
//        }

        return response;
    }


    @Override
    public String getImage(String keyword) {
        // 이미지 api
        return null;
    }
}
