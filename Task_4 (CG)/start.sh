#!/bin/bash

# Путь к вашему JAR-файлу
JAR_FILE="target/artifacts/CG/cgvsu.jar"

# Проверка, существует ли файл
if [ -f "$JAR_FILE" ]; then
  # Запуск JAR-файла с выделением памяти для JVM (можно настроить)
  java -jar "$JAR_FILE"
else
  echo "Ошибка: JAR-файл '$JAR_FILE' не найден."
  exit 1
fi