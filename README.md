## 1) Знание теории
### Система управления базами данных (СУБД) – это …
   Комплекс программ, позволяющих создать базу данных и манипулировать данными.
### База данных (БД) – это …
   Совокупность данных, хранимых в соответствии со схемой данных, манипулирование которыми выполняют в соответствии с правилами средств моделирования данных.
### Модель данных – это …
   Совокупность структур данных и операций их обработки.
### Таблица – это …
   Совокупность связанных данных, хранящихся в структурированном виде в базе данных. Она состоит из столбцов и строк.
### Индексы – это …
   Объект базы данных, создаваемый с целью повышения производительности поиска данных
### Отношения – это …
   Схема связи таблиц в БД
### Какие бывают типы отношений?
   Один к одному, один ко многим (многие к одному), многие ко многим
### Хранимая процедура – это …
   объект базы данных, представляющий собой набор SQL-инструкций, который компилируется один раз и хранится на сервере
### Первичный ключ – это …
   Поле или набор полей со значениями, которые являются уникальными для всей таблицы
### Внешний ключ – это …
   Поле или набор полей, используемый в реляционной базе данных для связи данных между таблицами.
## 2) Подготовка
### Установка СУБД (к примеру, postgresql)
### - Скачать СУБД PostgreSQL последней версии по <a href="https://www.enterprisedb.com/downloads/postgres-postgresql-downloads" target="_blank">ссылке</a>
### - Установить PostgreSQL
### Во время установки:
> - Задать пароль к БД (например, qwerty123)
> - Не устанавливать компонент pgAdmin, будем использовать DBeaver
### Установка инструмента администрирования базы данных (к примеру, dbeaver)
### - Скачать DBeaver последней версии по <a href="https://dbeaver.io/download/" target="_blank">ссылке</a>
### - Установить DBeaver
### Настройка подключения
### В DBeaver создать новое соединение:
> - Выбрать для соединения PostgreSQL
> - В настройках ничего не менять, ввести только пароль, который задавали при установки PostgreSQL
### Создание БД и настройка привилегий
> - Создать пользователя:
> ``` sql
> CREATE USER test_sql WITH password '123';
> ```
> - Создать БД для пользователя:
> ``` sql
> CREATE DATABASE sqltest OWNER test_sql;
> ```
> - Дать права пользователю:
> ``` sql
> GRANT ALL privileges ON DATABASE sqltest TO test_sql;
> ```
> - Подключиться к созданной БД, после чего в левом разделе DBeaver нажать ПКМ по созданной БД и выбрать редактор **SQL -> Open SQL console**
## 3) Знание операторов DDL
### Создайте 2 таблицы ниже. Столбец ‘code’ первичный ключ типа int (с автоувеличением), столбец ‘model’ типа int, столбец ‘price’ типа int, столбец ‘maker’ типа varchar(25)
### Таблица ProductOne:
<table>
    <thead>
        <tr>
            <th>code</th>
            <th>model</th>
            <th>price</th>
            <th>maker</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td align="center">1</td>
            <td align="center">100</td>
            <td align="center">60000</td>
            <td align="center">lenovo</td>
        </tr>
        <tr>
            <td align="center">2</td>
            <td align="center">103</td>
            <td align="center">55000</td>
            <td align="center">dell</td>
        </tr>
        <tr>
            <td align="center">3</td>
            <td align="center">100</td>
            <td align="center">60000</td>
            <td align="center">lenovo</td>
        </tr>
        <tr>
            <td align="center">4</td>
            <td align="center">102</td>
            <td align="center">85000</td>
            <td align="center">asus</td>
        </tr>
        <tr>
            <td align="center">5</td>
            <td align="center">103</td>
            <td align="center">55000</td>
            <td align="center">dell</td>
        </tr>
        <tr>
            <td align="center">6</td>
            <td align="center">100</td>
            <td align="center">60000</td>
            <td align="center">lenovo</td>
        </tr>
        <tr>
            <td align="center">7</td>
            <td align="center">102</td>
            <td align="center">85000</td>
            <td align="center">asus</td>
        </tr>
        <tr>
            <td align="center">8</td>
            <td align="center">103</td>
            <td align="center">55000</td>
            <td align="center">dell</td>
        </tr>
        <tr>
            <td align="center">9</td>
            <td align="center">102</td>
            <td align="center">85000</td>
            <td align="center">asus</td>
        </tr>
        <tr>
            <td align="center">10</td>
            <td align="center">100</td>
            <td align="center">60000</td>
            <td align="center">lenovo</td>
        </tr>
        <tr>
            <td align="center">11</td>
            <td align="center">101</td>
            <td align="center">60000</td>
            <td align="center">hp</td>
        </tr>
        <tr>
            <td align="center">12</td>
            <td align="center">104</td>
            <td align="center">65000</td>
            <td align="center">acer</td>
        </tr>
        <tr>
            <td align="center">13</td>
            <td align="center">101</td>
            <td align="center">60000</td>
            <td align="center">hp</td>
        </tr>
        <tr>
            <td align="center">14</td>
            <td align="center">108</td>
            <td align="center">100000</td>
            <td align="center">apple</td>
        </tr>
    </tbody>
</table>

### Таблица ProductTwo:
<table>
    <thead>
        <tr>
            <th>code</th>
            <th>model</th>
            <th>price</th>
            <th>maker</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td align="center">1</td>
            <td align="center">103</td>
            <td align="center">55000</td>
            <td align="center">dell</td>
        </tr>
        <tr>
            <td align="center">2</td>
            <td align="center">106</td>
            <td align="center">70000</td>
            <td align="center">lg</td>
        </tr>
        <tr>
            <td align="center">3</td>
            <td align="center">102</td>
            <td align="center">85000</td>
            <td align="center">asus</td>
        </tr>
        <tr>
            <td align="center">1</td>
            <td align="center">105</td>
            <td align="center">80000</td>
            <td align="center">samsung</td>
        </tr>
    </tbody>
</table>

``` sql
CREATE TABLE ProductOne (
code int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
model int,
price int,
maker varchar(25)
);
```
``` sql
CREATE TABLE ProductTwo (
code int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
model int,
price int,
maker varchar(25)
);
```
### Добавьте к таблице ProductOne столбец ‘city’ с типом varchar(25)
``` sql
ALTER TABLE ProductOne
ADD city varchar(25);
Удалите столбец ‘city’ из таблицы ProductOne
ALTER TABLE ProductOne
DROP COLUMN city;
```
### Удалите таблицу ProductTwo
``` sql
DROP TABLE ProductTwo;
Создайте повторно таблицу ProductTwo
CREATE TABLE ProductTwo (
code int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
model int,
price int,
maker varchar(25)
);
```
### В соответствии с таблицами ProductOne и ProductTwo добавить записи в БД
### ProductOne:
``` sql
INSERT INTO ProductOne (model, price, maker)
VALUES
(100, 60000, 'lenovo'),
(103, 55000, 'dell'),
(100, 60000, 'lenovo'),
(102, 85000, 'asus'),
(103, 55000, 'dell'),
(100, 60000, 'lenovo'),
(102, 85000, 'asus'),
(103, 55000, 'dell'),
(102, 85000, 'asus'),
(100, 60000, 'lenovo'),
(101, 60000, 'hp'),
(104, 55000, 'acer'),
(101, 60000, 'hp'),
(108, 100000, 'apple');
```
### ProductTwo:
``` sql
INSERT INTO ProductTwo (model, price, maker)
VALUES
(103, 55000, 'dell'),
(106, 70000, 'lg'),
(102, 85000, 'asus'),
(105, 80000, 'samsung');
```
## 4) Умение составлять простые запросы на выборку данных (SELECT)
### Найти все модели, кроме 102, 103, 104 из таблицы ProductOne. Ответ отсортируйте по убыванию номера модели
``` sql
SELECT model, price, maker FROM ProductOne WHERE model NOT IN ('102', '103', '104')
ORDER BY model DESC;
```
### Отобразить строки из таблицы ProductOne у которых цена (price) больше 55000, а производитель (maker) с названием, начинающимся на 'a' и на 'l'.
``` sql
SELECT model, price, maker FROM ProductOne WHERE price > 55000 AND (maker LIKE 'a%' OR maker LIKE 'l%')
```
### Отобразить строки из таблицы ProductOne, которые находятся в диапазоне цены (price) от  55000 до 60000.
``` sql
SELECT model, price,maker FROM ProductOne WHERE price BETWEEN 55000 AND 60000
```
## Знание агрегатных функций
### Найти количество продуктов из таблицы ProductOne, выпущенных производителем Lenovo и отобразить их в столбце qty
``` sql
SELECT COUNT(*) AS qty FROM ProductOne WHERE maker='lenovo'
```
### Найти минимальную, максимальную и среднюю (округлить до 2 знаков после запятой) цену на продукты из таблицы ProductOne
``` sql
SELECT MIN(price) AS min_price, MAX(price) AS max_price, ROUND(AVG(price), 2) AS avg_price FROM ProductOne
```
### Найти количество различных моделей из таблицы ProductOne
``` sql
SELECT COUNT(DISTINCT model) AS count_model FROM ProductOne
```
### Найти сумму цен продуктов из таблицы ProductOne, число моделей которых не превышает двух
``` sql
SELECT SUM (price) AS sum_price FROM ProductOne
WHERE model IN (SELECT model FROM ProductOne GROUP BY model HAVING COUNT (model) <=2);
```
## 6) Знание операторов соединения и объединения таблиц JOIN (INNER, LEFT, RIGHT, FULL), подзапросы
### Найти продукты в таблице ProductTwo, которых нет в таблице ProductOne (использовать подзапрос)
``` sql
SELECT * FROM ProductTwo
WHERE model NOT IN (SELECT model FROM ProductOne)
```
### Найти продукты из таблиц ProductOne и ProductTwo, номера моделей которых встречаются в обеих таблицах
``` sql
SELECT * FROM ProductOne p1 INNER JOIN ProductTwo p2 ON p1.model=p2.model
```
### Дополнительно:
### Учесть продукты из ProductOne:
``` sql
SELECT * FROM ProductOne p1 LEFT JOIN ProductTwo p2 ON p1.model=p2.model
```
### Учесть продукты из ProductTwo:
``` sql
SELECT * FROM ProductOne p1 RIGHT JOIN ProductTwo p2 ON p1.model=p2.model
```
### Учесть строки из обеих таблиц:
``` sql
SELECT * FROM ProductOne p1 FULL JOIN ProductTwo p2 ON p1.model=p2.model
```
## 7) Знание реализации операций реляционной алгебры средствами языка SQL (UNION, INTERSECT...)
### Для следующих задач не используем явные операции соединения
### Отобразить продукты производителей 'asus' из таблицы ProductOne  и 'samsung' из таблицы ProductTwo
``` sql
SELECT model, price, maker FROM ProductOne
WHERE maker='asus'
UNION
SELECT model, price, maker FROM ProductTwo
WHERE maker='samsung'
```
### Отобразить продукты из таблиц ProductOne и ProductTwo, номера моделей которых сопадают
``` sql
SELECT model, price, maker FROM ProductOne
INTERSECT
SELECT model, price, maker FROM ProductTwo
```
### Исключить продукты из таблицы ProductOne, которые есть в таблице ProductTwo и отобразить их
``` sql
SELECT model, price, maker FROM ProductOne
EXCEPT
SELECT model, price, maker FROM ProductTwo
```
## 8) Умение составлять запросы на запись и редактирование данных, знание операторов DML
### Добавить записи в таблицу ProductOne из таблицы ProductTwo с номерами моделей, которых не было в ProductOne
``` sql
INSERT INTO ProductOne (model, price, maker)
SELECT model, price, maker FROM ProductTwo
WHERE model NOT IN (SELECT model FROM ProductOne);
```
### Необходимо удалить все строки из таблицы ProductOne, кроме строки с наименьшим кодом (столбец code) для каждой группы ProductOne с одинаковым номером модели.
``` sql
DELETE FROM ProductOne
WHERE code NOT IN (
SELECT MIN(code)
FROM ProductOne
GROUP BY model
);
```
### Измените, данные в таблице ProductOne так, чтобы номера моделей были увеличены на 10, для производителя apple
``` sql
UPDATE ProductOne SET model=model+10 WHERE maker='apple'
```
## 9) Дополнительные задачи
### Отобразить первые 3 записи таблицы ProductOne
``` sql
SELECT model, price, maker FROM productone
LIMIT 3;
```
### Найти продукты из таблицы ProductOne, стоимость которых превышает стоимость любого продукта из таблицы ProductTwo
``` sql
SELECT model, price, maker FROM productone WHERE price > ALL (SELECT price FROM producttwo);
```
### Найти продукты из таблицы ProductOne, стоимость которых превышает стоимость хотя бы одного продукта из таблицы ProductTwo
``` sql
SELECT model, price, maker FROM productone WHERE price > ANY (SELECT price FROM producttwo);
```
### Вывести продукты из таблицы ProductOne и добавить текст (в новый столбец 'pricetext') в зависимости от цены:
> - Больше 70000 – дорого;
> - Равно 70000 – приемлемо;
> - Больше 70000 – дешево
``` sql
SELECT model, price, maker,
CASE
WHEN price > 70000 THEN 'expensive'
WHEN price = 70000 THEN 'reasonably'
ELSE 'cheap'
END AS pricetext
FROM productone;
```
### Напишите хранимую процедуру на добавление записи в таблицу ProductOne. После вызовите ее и проверьте по таблице, что запись добавилась
### Создание процедуры:
``` sql
CREATE PROCEDURE insert_procedure (model integer, price integer, maker varchar)
LANGUAGE SQL
AS $$
INSERT INTO ProductOne (model, price, maker) VALUES (model, price, maker);
$$;
```
### Вызов:
``` sql
CALL insert_procedure (111, 120000, 'razer');
```

## Фрейм автотестирования SQL
### После прохождения подготовительного шага необходимо создать подключение с базой данных
### В данном проекте подключение реализовано в классе ConnectionDB
``` java
public class ConnectionDB {
    private static final String DB_URL = Config.getDbConfigurations().dbUrl();
    private static final String DB_USER = Config.getDbConfigurations().dbUser();
    private static final String DB_PASS = Config.getDbConfigurations().dbPass();

    private static final Logger logger = Logger.getLogger(ConnectionDB.class.getName());

    public static java.sql.Connection connection() {

        logger.info("Testing connection to PostgreSQL JDBC");
        java.sql.Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.info("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        logger.info("PostgreSQL JDBC Driver successfully connected");


        try {
            connection = DriverManager
                    .getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException e) {
            logger.info("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            logger.info("You successfully connected to database now");
        } else {
            logger.info("Failed to make connection to database");
        }

        return connection;
    }

}
```
### Адрес подключения, логин и пароль хранятся в папке с ресурсами
``` java
db.url=jdbc:postgresql://localhost:5432/dataBase
db.user=username
db.pass=password
```
### Для проверки ожидаемых результатов использовались json файлы
### В папке pojo находится класс Product для работы с json файлами
### В проекте реализованы классы для работы с DDL и DML, которые расположены в папке sql
### Запуск тестов рекомендуется начинать с task_3 и дальше. В task_3 происходит предварительное удаление таблиц для повторного тестирования из-за вносимых изменений в таблицы