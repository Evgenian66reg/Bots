package ru.topacademy;


import org.telegram.telegrambots.meta.api.objects.User;

public class Texts {
    public static String hello(){
        return " Я бот PogodaSityBot показываю погоду в городе. Просто введите название города в поле ввода.";
    }

    public static String about_author(){
        return "Свои отзывы вы можете отправлять на почту evrinom45@gmail.com.\n" +
                "Разрабатывал: Горбачев Евгений.  ";
    }
}
