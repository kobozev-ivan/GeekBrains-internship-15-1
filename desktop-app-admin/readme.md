desktop-app-admin should be here

Десктопный интерфейс администратора - клиент-приложение Справочник v1.0
реализует редактирование (добавление, изменение, удаление) элементов в таблицах базы данных, по протоколу HTTP через вебсервис REST. Формат обмена данными с сервером выбран JSON.

Кн. Обновить - позволяет получить все данные из соотвествующей открытой вкладке таблицы в БД.

Кн. Сохранить - отправляет в соответсвующую открытой вкладке таблицу БД, все реально сделанные изменения данных (добавление новых , удаление старых, изменение существующих элементов)

справочник java\reference
- Data.java - хранение, работа с данными
- Request.java - формирование запросов в базу
  
графический интефейс справочника: java\reference\gia  
* SheetReference.java - вид простой вкладки
* SheetReferenceKeywords.java - вид связанной вкладки через выпадающий список
* Reference.java - сборка вкладок
* Main class: AdminGI.java

Имеется локальный фейк-сервер с фейк-БД. Фейк-сервер реализован на встроенным в JDK JAVA SE8 HTTP -сервере с использованием [Jersey 2.26](https://jersey.github.io/documentation/latest/index.html) 
[API Jersey](https://jersey.github.io/apidocs/2.26/jersey/index.html) Фейк БД реализована на конструкциях MAP.
может использоваться для локальной проверки справочника.
  
Используются 3 HTTP - метода:
- GET - обновить/получить все элементы таблицы соответсвующей открытой вкладке
- POST - добавить/удалить выбранные элемент/элементы в соответствующей таблице открытой вкладке.
- PUT - измениь/заменить  выбранные элемент/элементы в соответствующей таблице открытой вкладке.

фейк-сервер с фейк БД desktop-app-admin\src\main\java\fakeDatabase
* FakeData.java - база данных
* WindowServer.java - реализация контрольного поля сервера
* WebService.java - веб-сервис REST с методами
* Main.class: FakeServerHTTP - графический интерфейс сервера.  