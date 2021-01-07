package ru.itis.dsl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dsl.models.Buyer;
import ru.itis.dsl.models.Good;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private String id;
    private String buyerName;
    private List<String> goodsNames;
}
