package com.fastcampus.getinline.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc // MockMvc를 자동으로 Autowired 받을 수 있다.
@SpringBootTest
class BaseControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("[view][GET] 기본 페이지 요청")
    @Test
    void givenNothing_whenRequestingRootPage_thenReturnsIndexPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(content().string(CoreMatchers.containsString("Index Page 입니다.")))
                .andExpect(view().name("index"))
                .andDo(print());
    }
}