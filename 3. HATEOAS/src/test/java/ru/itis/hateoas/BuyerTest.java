package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.dto.BuyerDto;
import ru.itis.hateoas.models.*;
import ru.itis.hateoas.services.BuyerOrderService;
import ru.itis.hateoas.services.BuyerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class BuyerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerService buyerService;

    @BeforeEach
    public void setUp() {
        when(buyerService.makeConfirmed(1L)).thenReturn(confirm());
    }

    @Test
    public void deliveryOrderTest() throws Exception {
        mockMvc.perform(put("/buyers/1/confirm")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(confirm().getState().name()))
                .andExpect(jsonPath("$.name").value(confirm().getName()))
                .andExpect(jsonPath("$.surname").value(confirm().getSurname()))
                .andExpect(jsonPath("$.email").value(confirm().getEmail()))
                .andDo(document("confirm_buyer", responseFields(
                        fieldWithPath("id").description("Id пользователя"),
                        fieldWithPath("name").description("Имя пользователя"),
                        fieldWithPath("surname").description("Фамилия пользователя"),
                        fieldWithPath("email").description("Почта пользователя"),
                        fieldWithPath("state").description("Подтвержден/не подтвержден аккаунт")
                )));
    }



    private BuyerDto confirm() {
        return BuyerDto.fromBuyer(Buyer.builder()
                .id(1L)
                .name("Kamilla")
                .surname("Khairullina")
                .email("kamilla@gmail.com")
                .password("qwerty007")
                .state(AccountState.CONFIRMED)
                .build());
    }


}
