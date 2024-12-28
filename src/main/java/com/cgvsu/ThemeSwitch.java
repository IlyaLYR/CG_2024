package com.cgvsu;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class ThemeSwitch extends HBox {
    protected final ToggleButton darkButton;
    protected final ToggleButton lightButton;

    private boolean isDarkTheme = true; // По умолчанию — тёмная тема

    public ThemeSwitch() {
        // Создание кнопок
        darkButton = new ToggleButton("Dark");
        lightButton = new ToggleButton("Light");

        // Группа переключателей
        ToggleGroup toggleGroup = new ToggleGroup();
        darkButton.setToggleGroup(toggleGroup);
        lightButton.setToggleGroup(toggleGroup);

        // Установка по умолчанию
        darkButton.setSelected(true);

        // Стили для кнопок
        darkButton.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #ffffff;");
        lightButton.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #000000;");

        // Логика переключения
        darkButton.setOnAction(event -> isDarkTheme = true);
        lightButton.setOnAction(event -> isDarkTheme = false);

        // Добавляем кнопки в компоновку
        this.getChildren().addAll(darkButton, lightButton);
        this.setSpacing(10); // Отступ между кнопками
    }

    // Метод для получения текущей темы
    public boolean isDarkTheme() {
        return isDarkTheme;
    }
}

