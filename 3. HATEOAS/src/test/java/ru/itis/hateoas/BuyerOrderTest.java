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
import ru.itis.hateoas.models.*;
import ru.itis.hateoas.repositories.BuyerOrderRepository;
import ru.itis.hateoas.repositories.BuyerRepository;
import ru.itis.hateoas.repositories.GoodRepository;
import ru.itis.hateoas.services.BuyerOrderService;

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
public class BuyerOrderTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerOrderService buyerOrderService;

    @BeforeEach
    public void setUp() {
        when(buyerOrderService.makeOrderStateDelivery(1L)).thenReturn(deliveryOrder());
        when(buyerOrderService.makeOrderStateAtThePostOffice(1L)).thenReturn(atThePostOfficeOrder());
        when(buyerOrderService.makeOrderStateReceived(1L)).thenReturn(receivedOrder());
    }

    @Test
    public void deliveryOrderTest() throws Exception {
        mockMvc.perform(put("/buyerOrders/1/delivery")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(deliveryOrder().getState().name()))
                .andDo(document("delivery_order", responseFields(
                        fieldWithPath("state").description("Состояние заказа"),
                        fieldWithPath("_links.atThePostOffice.href").description("Ссылки на доступные действия" )
                )));
    }

    @Test
    public void atThePostOfficeTest() throws Exception {
        mockMvc.perform(put("/buyerOrders/1/atThePostOffice")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(atThePostOfficeOrder().getState().name()))
                .andDo(document("at_the_post_office_order", responseFields(
                        fieldWithPath("state").description("Состояние заказа"),
                        fieldWithPath("_links.received.href").description("Ссылки на доступные действия" )
                )));
    }

    @Test
    public void receivedTest() throws Exception {
        mockMvc.perform(put("/buyerOrders/1/received")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(receivedOrder().getState().name()))
                .andDo(document("received_order", responseFields(
                        fieldWithPath("state").description("Состояние заказа")
                )));
    }



    private BuyerOrder deliveryOrder() {
        BuyerOrder buyerOrder = getBuyerOrder();
        buyerOrder.setState(OrderState.DELIVERY);
        return buyerOrder;
    }

    private BuyerOrder atThePostOfficeOrder() {
        BuyerOrder buyerOrder = getBuyerOrder();
        buyerOrder.setState(OrderState.AT_THE_POST_OFFICE);
        return buyerOrder;
    }
    private BuyerOrder receivedOrder() {
        BuyerOrder buyerOrder = getBuyerOrder();
        buyerOrder.setState(OrderState.RECEIVED);
        return buyerOrder;
    }

    private BuyerOrder getBuyerOrder(){
        Shop shop = Shop.builder()
                .name("Мой магазин")
                .city(City.КАЗАНЬ)
                .build();
        Good good1 = Good.builder()
                .name("Носки")
                .seller(shop)
                .cost(50)
                .build();
        Good good2 = Good.builder()
                .name("Шапка")
                .seller(shop)
                .cost(300)
                .build();
        List<Good> goods = new ArrayList<>();
        goods.add(good2);
        goods.add(good1);
        Buyer buyer = Buyer.builder()
                .name("Иван")
                .surname("Иванов")
                .email("ivan@gmail.com")
                .goodsInBasket(goods)
                .build();

        return BuyerOrder.builder()
                .id(1L)
                .goods(goods)
                .buyer(buyer)
                .build();
    }
}
