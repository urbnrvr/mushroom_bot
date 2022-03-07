package com.clockworkmushroom.mushroom_bot.telegram.service;

import com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.MENU;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.ProductDescription.FLY_AGARIC;

@Component
public class InfoLogic {

    public SendMessage sendInfoMessage(String chatId) {
        return SendMessage.builder()
                .text("Выберите продукт")
                .chatId(chatId)
                .replyMarkup(getKeyboard())
                .build();
    }

    public InlineKeyboardMarkup getKeyboard() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        var flyAgaricButton = InlineKeyboardButton.builder()
                .text("Мухомор \uD83C\uDF44")
                .callbackData(FLY_AGARIC.getCommand())
                .build();

        var backButton = InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData(MENU.getCommand())
                .build();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(flyAgaricButton);
        keyboardRow1.add(backButton);

        keyboard.add(keyboardRow1);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
