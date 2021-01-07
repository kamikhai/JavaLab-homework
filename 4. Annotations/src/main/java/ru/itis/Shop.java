package ru.itis;

@HtmlForm(method = "post", action = "/shop")
public class Shop {
    @HtmlInput(type = "text", name = "name", placeholder = "Название")
    private String name;
}

