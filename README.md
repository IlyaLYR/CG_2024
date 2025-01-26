# Task_4
Computer graphics - software rendering in Java <p>
Данные проект - это итог всего, что проходилось в курсе CG.
## cg-software-rendering
### Графический конвейер
В финальном проекте курса подходим к задаче визуализации модели. Вообще, получение изображения объекта по его описанию называется рендерингом. В случае трёхмерных моделей речь идёт о 3d-рендеринге.
В нашем случае рендер будет программным (software), то есть код должен выполняться без использования видеокарты. Такая задача не теряет свою актуальность и среди разработчиков крупного ПО, а в рамках данного курса она позволит
по-настоящему прочувствовать все алгоритмы, что стоят за отрисовкой.
За отображением трёхмерной модели на плоскости экрана стоит несколько переходов из одной системы координат в другую. Все они вместе называются графическим конвейером, а точнее его первым и необходимым этапом. Как и в главе
выше, за каждый переход будет отвечать своя матрица. Умножая их по очереди на трёхмерные вершины моделей, будем получать в итоге соответствующие координаты экрана.
### Возможности cg-software-rendering
1) Загрузка obj-модели
2) Отрисовка сетки модели
3) Возможность наложить текстурку на модель
4) Освещение и работа с тенями
5) Перемещение модели в пространстве (аффинные преобразования)
6) Работа с несколькими камера
---
*Авторы*
@IlyaLYR @9anchik


