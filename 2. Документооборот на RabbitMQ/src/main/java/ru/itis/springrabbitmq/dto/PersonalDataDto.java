package ru.itis.springrabbitmq.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class PersonalDataDto implements Serializable {
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private String passportId;
    private String birthDate;
    private String itn;//itn - ИНН
    private String pasportScan;
    private boolean isBudget;
    private String phoneNumber;
    private String group;
    private Integer course;
    private boolean hasAdditionalDocuments;
}
