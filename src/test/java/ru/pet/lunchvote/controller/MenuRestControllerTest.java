package ru.pet.lunchvote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.pet.lunchvote.model.Menu;
import ru.pet.lunchvote.repository.MenuRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MenuRestControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MenuRepository menuRepository;

    Menu MENU0 = new Menu(0, LocalDate.now(), "Rest0", "Dishes1", 99);
    Menu MENU1 = new Menu(1, LocalDate.now(), "Rest1", "Dishes1", 299);
    Menu MENU2 = new Menu(2, LocalDate.now(), "Rest2", "Dishes2", 399);
    Menu MENU3 = new Menu(3, LocalDate.now().minusDays(1), "Rest3", "Dishes3", 499);
    Menu MENU3mod = new Menu(3, LocalDate.now().minusDays(1), "Rest3mod", "Dishes3mod", 499);
    Menu MENU4noid = new Menu( null, LocalDate.now(), "Rest4", "Dishes4", 49);
    Menu MENU4 = new Menu(4, LocalDate.now(), "Rest4", "Dishes4", 49);

    List<Menu> menu = List.of(MENU0, MENU1, MENU2, MENU3);

    @BeforeAll
    public static void setup(){
    }


    class MenuMatcher implements ArgumentMatcher<Menu> {
        private Menu left;

        public MenuMatcher(Menu left) {
            this.left = left;
        }

        @Override
        public boolean matches(Menu right) {
            if (right.getId() == null && left.getId() != null) return false;
            if (((right.getId() == null && left.getId() == null) || (right.getId().equals(left.getId())))
                    && right.getMenudate().equals(left.getMenudate())
                    && right.getRestaurant().equals(left.getRestaurant())
                    && right.getDishes().equals(left.getDishes())
                    && right.getPrice().equals(left.getPrice()))
                return true;
            else return false;
        }
    }

    @Test
    void getAll() throws Exception {
        Mockito.when(menuRepository.findAll()).thenReturn(menu);
        mvc.perform(get("/menus").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].dishes", is("Dishes3")));
    }

    @Test
    void getAllToday() throws Exception {
        Mockito.when(menuRepository.getAllByMenudate(LocalDate.now())).thenReturn(
                menu.stream().filter(m -> m.getMenudate().equals(LocalDate.now())).collect(Collectors.toList()));
        mvc.perform(get("/menus/today").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].dishes", is(MENU2.getDishes())));
    }

    @Test
    void getByDate() throws Exception {
        Mockito.when(menuRepository.getAllByMenudate(LocalDate.now().minusDays(1))).thenReturn(List.of(MENU3));
        mvc.perform(get("/menus/date/" + LocalDate.now().minusDays(1).toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].dishes", is(MENU3.getDishes())));
    }

    @Test
    void getById() throws Exception {
        Mockito.when(menuRepository.findById(MENU1.getId())).thenReturn(Optional.of(MENU1));
        mvc.perform(get("/menus/" + MENU1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dishes", is(MENU1.getDishes())));
    }

    @Test
    void deleteNoLogin() throws Exception {
        mvc.perform(delete("/menus/" + MENU1.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void deleteNotAdmin() throws Exception {
        mvc.perform(delete("/menus/" + MENU1.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteAdmin() throws Exception {
        mvc.perform(delete("/menus/" + MENU1.getId()))
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void createWithAdmin() throws Exception {
        Mockito.when(menuRepository.save(ArgumentMatchers.argThat(new MenuMatcher(MENU4noid)))).thenReturn(MENU4);
        mvc.perform(post("/menus/").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(MENU4noid)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(MENU4.getId())))
                .andExpect(jsonPath("$.dishes", is(MENU4.getDishes())))
                .andExpect(redirectedUrlPattern("**/menus/" + MENU4.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        mvc.perform(put("/menus/" + MENU3mod.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(MENU3mod)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.dishes", is(MENU3mod.getDishes())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateWrongId() throws Exception {
       mvc.perform(put("/menus/" + MENU3mod.getId() + 1).contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(this.mapper.writeValueAsString(MENU3mod)))
               .andExpect(status().isBadRequest());
    }
}