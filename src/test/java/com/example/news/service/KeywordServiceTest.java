package com.example.news.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringJUnit4ClassRunner.class)
public class KeywordServiceTest {

    @Autowired
    public KeywordService keywordService;

    @DisplayName("키워드리스트 가져오기")
    @Test
    public void findKeywords() {
        Assert.assertNotNull(keywordService.findKeywords());
    }


    @DisplayName("키워드 찾기")
    @Test
    public void findKeyword(){
        Assert.assertNotNull(keywordService.findKeywordByKeyword("코로나"));

    }


    @DisplayName("키워드 등록")
    @Test
    public void insertKeyword() {
        Assert.assertEquals(keywordService.insertKeyword("자가 키트").getKeyword(),"자가 키트");

    }


    @Test
    public void checkKeyword() {

        Assert.assertTrue(keywordService.checkKeyword("선거"));
        Assert.assertFalse(keywordService.checkKeyword("자가 키트"));
    }
//
//    @DisplayName("키워드 삭제")
//    @Test
//    public void deleteKeyword(){
//        keywordService.deleteKeyword(
//                keywordService.findKeyword("코로나").getKeywordIdx());
////        Assert.assertNull(
////                keywordService.findKeyword("코로나"));
//
//    }

}
