package com.clockworkmushroom.mushroom_bot.telegram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.BUY;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.INFO;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.MENU;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.Commands.START;
import static com.clockworkmushroom.mushroom_bot.telegram.enumerations.ProductDescription.FLY_AGARIC;

@Slf4j
@Component
public class UpdateHandler {

    private final MenuLogic menuLogic;
    private final InfoLogic infoLogic;

    @Autowired
    public UpdateHandler(MenuLogic menuLogic, InfoLogic infoLogic) {
        this.menuLogic = menuLogic;
        this.infoLogic = infoLogic;
    }

    public SendMessage handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallBackQuery(update.getCallbackQuery());
        } else {
            return handleMessage(update.getMessage());
        }
    }

    private SendMessage handleCallBackQuery(CallbackQuery callbackQuery) {
        return handleMenu(callbackQuery.getData(), callbackQuery.getFrom().getId().toString());
    }

    private SendMessage handleMessage(Message message) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                return handleMenu(
                        message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength()),
                        message.getChatId().toString());
            } else {
                return createMessage(message.getChatId().toString(), "Не понял");
            }
    }

    private SendMessage handleMenu(String command, String chatId) {
        log.info(command);
        if (START.getCommand().equals(command)) {
            return  menuLogic.sendMenuMessage(chatId, true);
        } else if (MENU.getCommand().equals(command)) {
            return menuLogic.sendMenuMessage(chatId, false);
        } else if (INFO.getCommand().equals(command)) {
            return infoLogic.sendInfoMessage(chatId);
        } else if (BUY.getCommand().equals(command)) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Покупка")
                    .build();
        } else if (FLY_AGARIC.getCommand().equals(command)){
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(FLY_AGARIC.getDescription())
                    .build();
        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Не понял")
                    .build();
        }
    }

    private SendMessage createMessage(String chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
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
