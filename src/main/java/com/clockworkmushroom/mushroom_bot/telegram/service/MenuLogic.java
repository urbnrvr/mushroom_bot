package com.clockworkmushroom.mushroom_bot.telegram.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.BUY;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.INFO;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.MENU;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.START;

@Component
public class MenuLogic {

    public SendMessage sendMenuMessage(String chatId, boolean isStartCommand) {
        String text = isStartCommand ? START.getMessage() : MENU.getMessage();

        return SendMessage.builder()
                .text(text)
                .chatId(chatId)
                .replyMarkup(getKeyboard())
                .build();
    }

    private InlineKeyboardMarkup getKeyboard() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        var infoButton = InlineKeyboardButton.builder()
                .text("Информация")
                .callbackData(INFO.getCommand())
                .build();

        var buyButton = InlineKeyboardButton.builder()
                .text("Приобрести")
                .callbackData(BUY.getCommand())
                .build();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(infoButton);
        keyboardRow1.add(buyButton);

        keyboard.add(keyboardRow1);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
