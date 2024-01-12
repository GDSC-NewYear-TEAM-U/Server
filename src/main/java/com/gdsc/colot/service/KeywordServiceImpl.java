package com.gdsc.colot.service;

import com.google.cloud.vertexai.VertexAI;
import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.QnA;
import com.gdsc.colot.controller.dto.response.QnAResponseDto;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.preview.ChatSession;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
            new QnA("q1", "a1", "a2"),
            new QnA("q2", "a1", "a2"),
            new QnA("q3", "a1", "a2"),
            new QnA("q4", "a1", "a2"),
            new QnA("q5", "a1", "a2"),
            new QnA("q6", "a1", "a2"),
            new QnA("q7", "a1", "a2"),
            new QnA("q8", "a1", "a2"),
            new QnA("q9", "a1", "a2"),
            new QnA("q10", "a1", "a2"),
            new QnA("q11", "a1", "a2"),
            new QnA("q12", "a1", "a2")
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

    @Override
    public String vertexAI() throws IOException {
        String projectId = "cookiehouse-405120";
        String location = "asia-northeast3";
        String modelName = "gemini-pro";

        return chatDiscussion(projectId, location, modelName);
    }

    public static String chatDiscussion(String projectId, String location, String modelName)
            throws IOException {
        // Initialize client that will be used to send requests. This client only needs
        // to be created once, and can be reused for multiple requests.
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            GenerateContentResponse response;

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            // Create a chat session to be used for interactive conversation.
            ChatSession chatSession = new ChatSession(model);

            response = chatSession.sendMessage("Hello.");
            String r1 = ResponseHandler.getText(response);
            System.out.println(r1);

            response = chatSession.sendMessage("What are all the colors in a rainbow?");
            String r2 = ResponseHandler.getText(response);
            System.out.println(r2);

            response = chatSession.sendMessage("Why does it appear when it rains?");
            String r3 = ResponseHandler.getText(response);
            System.out.println(r3);
            System.out.println("Chat Ended.");

            return r1 + r2 + r3;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
