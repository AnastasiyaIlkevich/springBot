package com.botEx.springBot.service;

import com.botEx.springBot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private final BotConfig botConfig;

    final static String HELP_TEXT = "Это бот для предоставления информации по 1С";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listCommands = new ArrayList<>();
        listCommands.add(new BotCommand("/start", "преветственное сообщение"));
        listCommands.add(new BotCommand("/myData", "предоставляет сохранённую информацию о пользователе"));
        listCommands.add(new BotCommand("/deleteData", "удаляет сохранённую информацию о пользователе"));
        listCommands.add(new BotCommand("/help", "подробная информация о боте"));
       // listCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list " + e.getMessage());
        }


    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String massegeTaxt = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (massegeTaxt) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                default:
                    sendMessage(chatId, "Sorry(((((");
            }
        }

    }

    private void startCommandReceived(long chatId, String firstName) {

        String answer = "hi, " + firstName + ", nice to meet you!";
        log.info("user " + firstName + "click /start");
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error " + e.getMessage());
        }

    }
}
