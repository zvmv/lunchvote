package ru.pet.lunchvote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoteRestControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @BeforeAll
    static void Setup(){

    }

    @Test
    void contextLoad(){

    }

    @Test
    void getAllNoUser() throws Exception {
        mvc.perform(get("/votes")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAllNotAdmin() throws Exception {
        mvc.perform(get("/votes")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAll() throws Exception {
        mvc.perform(get("/votes")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[1].userId", is(2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllByDate() throws Exception {
        mvc.perform(get("/votes/date/" + LocalDate.now())).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[2].userId", is(3)));

        mvc.perform(get("/votes/date/" + LocalDate.now().minusDays(2))).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getWinner() throws Exception {
        mvc.perform(get("/votes/winner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getWinnerByDate() throws Exception {
        mvc.perform(get("/votes/winner/" + LocalDate.now().minusDays(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    @WithMockUser
    void deleteByUser() throws Exception {
        mvc.perform(delete("/votes/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteByAdmin() throws Exception {
        mvc.perform(delete("/votes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void makeVoteWithUnknownUser() throws Exception {
        mvc.perform(post("/votes/?id=2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "mike@yandex.ru", password = "pass")
    void makeVote() throws Exception {
        mvc.perform(post("/votes/?id=2"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.menuId", is(2)));
        mvc.perform(get("/votes/winner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));
    }
}