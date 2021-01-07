package jpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "faq")
public class FAQ {
    @Id
    private Long _id;
    private Buyer buyer;
    private String question;
    private String answer;
}
