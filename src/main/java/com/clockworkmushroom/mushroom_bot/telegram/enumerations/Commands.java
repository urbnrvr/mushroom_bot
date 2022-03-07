package com.clockworkmushroom.mushroom_bot.telegram.enumerations;

import lombok.Getter;

@Getter
public enum Commands {
    START("/start", "Приветствую"),
    MENU("/menu", "Основное меню"),
    INFO("/info", "Информация о продукции"),
    BUY("/buy", "Приобрести");

    private final String command;
    private final String message;

    Commands(String command, String message) {
        this.command = command;
        this.message = message;
    }


}
