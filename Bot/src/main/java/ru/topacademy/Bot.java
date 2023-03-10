package ru.topacademy;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "Приветствие":
                    sendMsg(message,"Приветствую! " + Texts.hello());
                    break;
                case "Об авторе":
                    sendMsg(message, Texts.about_author());
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "Город не найден");
                    }
                    break;
            }
        }
    }

    public void setButtons(SendMessage message) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(keyboardMarkup);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Приветствие"));
        keyboardFirstRow.add(new KeyboardButton("Об авторе"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardMarkup.setKeyboard(keyboardRowList);
    }

    private void sendMsg(Message message, String s) {
        SendMessage smsg = new SendMessage();
        smsg.enableMarkdown(true);
        smsg.setChatId(message.getChatId().toString());

        smsg.setReplyToMessageId(message.getMessageId());
        smsg.setText(s);
        try {
            setButtons(smsg);
            execute(smsg);
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }

    @Override
    public  String getBotUsername() {
        return "PogodaSityBot";
    }

    @Override
    public String getBotToken() {
        return "6267834317:AAGb-GOsbpK6g3M_o3Yawi69bZ75QB_WU94";
    }
}
