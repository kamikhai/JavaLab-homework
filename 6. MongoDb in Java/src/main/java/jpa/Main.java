package jpa;

import jpa.models.Buyer;
import jpa.repositories.BuyerRepository;
import jpa.repositories.GoodRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 23.11.2020
 * MongoDb
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RepositoriesConfig.class);
        BuyerRepository buyerRepository = context.getBean(BuyerRepository.class);
        GoodRepository goodRepository = context.getBean(GoodRepository.class);

        //сохранение
        Buyer buyer = Buyer.builder()
                .email("321@gmail.com")
                .name("Katya")
                .surname("Ivanova")
                .build();
        buyerRepository.save(buyer);

        //обновление
        buyer = buyerRepository.findByEmail("321@gmail.com");
        buyer.setEmail("563@gmail.com");
        buyerRepository.save(buyer);

        //удаление
        buyer = buyerRepository.findByEmail("563@gmail.com");
        buyerRepository.delete(buyer);

//
//        System.out.println(goodRepository.findByNameAndCostBefore("Jacket", 1300));
    }
}
