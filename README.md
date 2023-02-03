Черновой вариант базы данных контактов с возможностью работать через CLI и REST API:

Работа через веб интерфейс:

http://localhost:8080/api/list - база с возможностью вносить контакты и редактировать (AJAX)

Добавление контакта через REST:

http://localhost:8080/api/addContact?type=person&name=Adam&surname=Sendler&birth=1988.01.21&gender=M&number=+375(13)23-32-43

Удаление по индексу:
http://localhost:8080/api/delContact?id=1

Список контактов:
http://localhost:8080/content/json

(+ надо доработать сериализацию)
<img width="335" alt="Снимок экрана 2023-02-03 в 11 24 52" src="https://user-images.githubusercontent.com/98476503/216549345-a3d252d5-0911-4f5f-b936-42abec71c2ea.png">
