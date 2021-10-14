package ru.pet.lunchvote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserRepository userRepository;

    static User ADMIN = new User(0,"admin@mail.ru", "pass", "Admin", true, true);
    static User USER1 = new User(1, "user1@mail.ru", "pass", "User1", true, false);
    static User USER2 = new User(2,"user2@mail.ru", "pass", "User2", true, false);
    static User USER2mod = new User(2,"user2mod@mail.ru", "pass", "User2mod", true, false);
    static User USER3 = new User(3,"user3@mailru", "pass", "User3", true, false);
    static User USER3noid = new User("user3@mail.ru", "pass", "User3", true, false);
    static User USER3invalid = new User("user3mailru", "pas", "", null, null);


    static List<User> users = Arrays.asList(ADMIN, USER1, USER2);

    //Тест на повторное создание

    @Test
    void ContextLoad() {

    }

    @Test
    void getAllNoUser() throws Exception {
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "user", roles = {"USER"})
    void getAllUser() throws Exception {
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void getAllAdmin() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(users);
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].email", is("user2@mail.ru")));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void getById() throws Exception {
        Mockito.<Optional<User>>when(userRepository.findById(USER1.getId())).thenReturn(Optional.of(users.get(1)));
        mvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("user1@mail.ru")));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void createNotNullId() throws Exception {
        mvc.perform(post("/users/").contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(USER3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void create() throws Exception {
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(USER3);
        mvc.perform(post("/users/").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(USER3noid)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("User3")))
                .andExpect(redirectedUrlPattern("**/users/" + USER3.getId()));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void createInvalid() throws Exception {
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(USER3invalid);
        mvc.perform(post("/users/").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(USER3invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.violations", hasSize(5)));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void del() throws Exception{
        Mockito.when(userRepository.getById(2)).thenReturn(null);
        mvc.perform(delete("/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void updateNotFound() throws Exception {
        mvc.perform(put("/users/" + USER3.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(USER3)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void updateWithoutIdInBody() throws Exception {
        mvc.perform(put("/users/" + USER3.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(USER3noid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
    void update() throws Exception {
        Mockito.when(userRepository.update(USER2mod)).thenReturn(1);
        Mockito.<Optional<User>>when(userRepository.findById(USER2mod.getId())).thenReturn(Optional.of(USER2mod));
        mvc.perform(put("/users/" + USER2mod.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(USER2mod)))
                .andExpect(status().isNoContent());

        mvc.perform(get("/users/" + USER2mod.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("user2mod@mail.ru")));
    }
}