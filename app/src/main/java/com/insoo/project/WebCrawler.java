package com.insoo.project;

import org.springframework.web.bind.annotation.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crawler")
public class WebCrawler {

    @PostMapping
    @ResponseBody
    public Map<String, Object> brnoApiRequest(@RequestBody Map<String, Object> paramMap){

        String url = (String) paramMap.get("url");

        Map<String, Object> response = webCrawler(url);
        return response;
    }

    private Map<String, Object> webCrawler(String url){

        Map<String, Object> responseMap = new HashMap<>();
        List<String> list = new ArrayList<>();

        try {
            // Jsoup을 사용하여 웹 페이지에 HTTP GET 요청을 보냄
            Document doc = Jsoup.connect(url).get();
            // 웹 페이지에서 원하는 부분을 선택하여 가져옴 (예: 공지사항 목록)
            Elements rows = doc.select("tbody tr");
            // 각 tr 요소에서 제목 가져오기
            for (Element row : rows) {
                // col-title 클래스 안의 span.txt 클래스에 있는 텍스트 가져오기
                String title = row.select(".col-title .txt").text();
                list.add(title);
            }

            responseMap.put("제목", list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseMap;
    }
}
