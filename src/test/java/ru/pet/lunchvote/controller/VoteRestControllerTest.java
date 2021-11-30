package ru.pet.lunchvote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.pet.lunchvote.repository.VoteRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoteRestControllerTest {

    @Autowired
    VoteRepository rep;

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
    void getAll() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void delete() {
    }

    @Test
    void makeVote() {
    }
}