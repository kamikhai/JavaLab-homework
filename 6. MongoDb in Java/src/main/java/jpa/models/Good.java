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
@Document(collection = "goods")
public class Good {
    @Id
    private String _id;
    private String name;
    private Shop seller;
    private Integer cost;

}
