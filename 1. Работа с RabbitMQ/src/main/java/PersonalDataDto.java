import java.io.Serializable;

public class PersonalDataDto implements Serializable {
    private String surname;
    private String name;
    private String passportId;
    private String date;
    private int age;

    public PersonalDataDto() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonalDataDto{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", passportId='" + passportId + '\'' +
                ", date='" + date + '\'' +
                ", age=" + age +
                '}';
    }
}
