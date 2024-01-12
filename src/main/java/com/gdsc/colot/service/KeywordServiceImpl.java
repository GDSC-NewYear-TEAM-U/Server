package com.gdsc.colot.service;

import com.gdsc.colot.controller.dto.response.KeywordResponseDto;
import com.google.cloud.vertexai.VertexAI;
import com.gdsc.colot.controller.dto.request.KeywordRequestDto;
import com.gdsc.colot.controller.dto.response.QnAResponseDto;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.generativeai.preview.ChatSession;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
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
            new QnAResponseDto(0, "연인의 성별은 어떻게 되나요?", "여성", "남성"),
            new QnAResponseDto(1, "연인의 선호하는 패션 스타일은 어떤 것인가요?", "캐주얼", "단정한"),
            new QnAResponseDto(2, "연인의 취미나 관심사는 무엇인가요?", "운동", "게임"),
            new QnAResponseDto(3, "연인이 좋아하는 색깔을 무엇인가요?", "검은색", "흰색"),
            new QnAResponseDto(4, "연인이 좋아하는 음식 또는 음료는 무엇인가요?", "떡볶이", "돈까스"),
            new QnAResponseDto(5, "연인의 성격은 어떠한가요?", "창의적", "규칙적"),
            new QnAResponseDto(6, "연인이 행복해 보일 때, 그 웃음의 원인은 무엇인 것 같아요?", "성공적인 경험이나 성취", "주변 사람들과의 유쾌한 소통"),
            new QnAResponseDto(7, "연인이 새로운 경험을 할 때 보이는 반응은 어떤가요?", "흥분하고 호기심을 갖음", "조금 불안해하거나 주의 깊게 생각함"),
            new QnAResponseDto(8, "연인이 좋아하는 음악 스타일은 무엇인가요? ", "록, 힙합, 일렉트로닉", "팝, R&B, 재즈"),
            new QnAResponseDto(9, "연인이 좋아하는 계절이나 날씨는 어떤가요?", "따뜻한 날씨와 봄 또는 여름", "쌀쌀한 날씨와 가을 또는 겨울"),
            new QnAResponseDto(10, "연인의 인터넷 사용 스타일은 어떤가요?", "새로운 트렌드를 따라가는 것을 즐김", "SNS를 하지 않거나 정량적으로 사용함"),
            new QnAResponseDto(11, "연인은 어떤 여행을 선호하나요?", "새로운 도시나 국가를 탐험하는 것을 즐김", "여유롭게 휴양지에서 휴식을 취하는 것을 선호"),
            new QnAResponseDto(12, "영화나 TV 프로그램을 볼 때, 연인이 선호하는 장르는 무엇인가요?", "액션, 스릴러, 공상 과학", "로맨스, 코미디, 드라마"),
            new QnAResponseDto(13, "연인의 선호하는 애완동물은 무엇인가요?", "강아지", "고양이"),
            new QnAResponseDto(14, "연인의 중요한 가치관은 무엇인가요?", "돈, 명예", "행복")
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
    public KeywordResponseDto getKeyword(List<KeywordRequestDto> keywordRequestDtoList) { // 추출된 키워드 보내기

        String reqText = "너는 사용자가 제공한 연인의 정보들로 가장 확률이 높은 상위 1개 연인의 선물을 추천한다.\n" +
                "정확한 근거 없는 내용은 피한다.\n" +
                "소개와 설명을 다음과 같은 형식으로 제공한다.\n" +
                "item: 자동차, description: 자동차는 참 좋은 선물입니다." +
                "\n";

        for (int i = 0; i < keywordRequestDtoList.size(); i++) {
            Integer index = keywordRequestDtoList.get(i).getQuestion_id();
            reqText += "input: " + Q_AA_LIST.get(index).getQuestion();
            reqText += "\noutput: " + (keywordRequestDtoList.get(i).getAnswer() == 0 ? Q_AA_LIST.get(index).getAnswer1() : Q_AA_LIST.get(index).getAnswer2());
            reqText += "\n\n";
        }

        reqText += "input: 연인에게 선물할 물건명 한가지와 추천한 선물에 대한 설명을 제공한다.\n" +
                "output:\n";

        String projectId = "cookiehouse-405120";
        String location = "asia-northeast3";
        String modelName = "gemini-pro";

        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            GenerateContentResponse response;

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            // Create a chat session to be used for interactive conversation.
            ChatSession chatSession = new ChatSession(model);

            response = chatSession.sendMessage(reqText);
            String inputString = ResponseHandler.getText(response);

            String item = null;
            String description = null;

            // "item" 부분 추출
            int itemIndex = inputString.indexOf("item:");
            if (itemIndex != -1) {
                int commaIndex = inputString.indexOf(",", itemIndex);
                if (commaIndex != -1) {
                    item = inputString.substring(itemIndex + 6, commaIndex).trim();
                }
            }

            // "description" 부분 추출
            int descriptionIndex = inputString.indexOf("description:");
            if (descriptionIndex != -1) {
                description = inputString.substring(descriptionIndex + 12).trim();
            }

            return KeywordResponseDto.of(item, description, "");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getImage(String keyword) {
        final String requestUrl = "https://www.flaticon.com/kr/search?word=" + keyword + "&color=color&shape=outline";
        Connection conn = Jsoup.connect(requestUrl);

        Document document = null;

        try {
            document = conn.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return document.getElementsByClass("icon--item  ").get(0).attr("abs:data-png");
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
