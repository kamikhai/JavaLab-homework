package ru.itis.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.itis.hateoas.models.*;
import ru.itis.hateoas.repositories.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class HateoasApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HateoasApplication.class, args);

        ShopRepository shopRepository = context.getBean(ShopRepository.class);
        Shop shop1 = shopRepository.save(Shop.builder()
                .name("Мой магазин")
                .city(City.КАЗАНЬ)
                .build());
        Shop shop2 = shopRepository.save(Shop.builder()
                .name("Не мой магазин")
                .city(City.МОСКВА)
                .build());

        GoodRepository goodRepository = context.getBean(GoodRepository.class);
        Good good1 = goodRepository.save(Good.builder()
                .name("Носки")
                .seller(shop1)
                .cost(50)
                .build());
        Good good2 =goodRepository.save(Good.builder()
                .name("Шапка")
                .seller(shop1)
                .cost(300)
                .build());
        Good good3 = goodRepository.save(Good.builder()
                .name("Тетрадь")
                .seller(shop2)
                .cost(34)
                .build());
        Good good4 = goodRepository.save(Good.builder()
                .name("Ручка")
                .seller(shop2)
                .cost(10)
                .build());

        BuyerRepository buyerRepository = context.getBean(BuyerRepository.class);
        List<Good> goods = new ArrayList<>();
        goods.add(good4);
        goods.add(good1);
        Buyer buyer1 = buyerRepository.save(Buyer.builder()
                .name("Камилла")
                .surname("Хайруллина")
                .email("kamilla@gmail.com")
                .password("qwerty007")
                .state(AccountState.NOT_CONFIRMED)
                .build());
        Buyer buyer2 = buyerRepository.save(Buyer.builder()
                .name("Иван")
                .surname("Иванов")
                .email("ivan@gmail.com")
                .password("qwerty007")
                .goodsInBasket(goods)
                .state(AccountState.NOT_CONFIRMED)
                .build());

        BuyerOrderRepository buyerOrderRepository = context.getBean(BuyerOrderRepository.class);
        buyerOrderRepository.save(BuyerOrder.builder()
                .goods(goods)
                .buyer(buyer1)
                .state(OrderState.PROCESSING)
                .build());

        CouponRepository couponRepository = context.getBean(CouponRepository.class);
        List<Buyer> buyers = new ArrayList<>();
        buyers.add(buyer2);
        couponRepository.save(Coupon.builder().buyer(buyers).shop(shop2).discountPercentage(20).build());

        FAQRepository faqRepository = context.getBean(FAQRepository.class);
        faqRepository.save(FAQ.builder().buyer(buyer1).question("Как долго доставляется товар?").build());


    }

}
