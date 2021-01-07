package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import spring.models.Buyer;
import spring.models.Good;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SimpleMongoConfig.class);
        MongoTemplate template = context.getBean(MongoTemplate.class);

        //сохранение
        Buyer buyer = Buyer.builder()
                .email("mail@gmail.com")
                .name("Masha")
                .surname("Ivanova")
                .build();
        template.save(buyer, "buyer");

        //обновление
        Query query = new Query(Criteria.where("email").is("mail@gmail.com"));
        Update update = new Update().set("email", "masha@gmail.com");
        template.upsert(query, update, "buyer");

        //удаление
        query = new Query(Criteria.where("email").is("masha@gmail.com"));
        template.remove(query, "buyer");


//        // db.courses.find({active: true, $or: [{keywords: 'java core'}, {studentsCount: {$lt: 35}}]})
//        List<Good> goods = template.find(new Query(
//                new Criteria().orOperator(where("name").is("Jacket"), where("cost").lt(1300))), Good.class, "goods");
//        System.out.println(goods);
    }
}
