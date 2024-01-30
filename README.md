# RecruitmentApplication
REST API для рекрутингового агентства по работе с заявками на подбор персонала. Задача хакатона SkillFactory (ноябрь 2023), заказчик - GlazGo. На текущий момент приложение находится в процессе разработки и расширения функционала.


## Содержание
- [Технологии](#технологии)
- [Возможности](#возможности)
- [Структура БД](#структура-бд)
- [Авторы проекта](#авторы-проекта)


## Технологии
- [Java 17](https://www.java.com/ru/)
- [Spring Boot 3.2.0](https://spring.io/projects/spring-boot)
- [PostgreSQL 16.1](https://www.postgresql.org)
- [JJWT-API 0.12.3](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api)


## Возможности
В текущей версии проекта доступен следующий функционал:
- [Авторизация](#авторизация)
- [JWT-токены](#jwt-токены)
- [Роли пользователей](#роли-пользователей)

### Авторизация
Авторизация осуществляется путем HTTP запроса на URL
```
http://localhost:8080/auth/login
```
и передачи в теле запроса имени пользователя (его корпоративного email) и пароля в формате JSON. Пример:
```
{
    "username": "customer@glazgo.ru",
    "password": "secret"
}
```
В случае успешной аутентификации пользователю присваивается роль в соответствии с его ролью в БД пользователей и JWT-токен.

Если указаны неверные имя пользователя или пароль, либо, пользователь не найден в БД пользователей, он получает HTTP ответ со статусом "403".

### JWT-токены
В случае успешной авторизации пользователь получает access_token и refresh_token в формате JSON. Пример:
```
{
    "access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJjdXN0b21lckBnbGF6Z28ucnUiLCJpYXQiOjE3MDY2MTE4NTgsImV4cCI6MTcwNjYxNTQ1OH0.XNJqIIrRPkZwlvMYC9UHdzguqGWg3V7ruN6b7EPN-E7bZT6Qu-zau9xF_21ncjpn",
    "refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJjdXN0b21lckBnbGF6Z28ucnUiLCJpYXQiOjE3MDY2MTE4NTgsImV4cCI6MTcwNzIxNjY1OH0.jIzDetGyjdP6zBl1PO6cWK-hG5j7KqgWFlQvkvF4mORH0Rhnrfb-FJmJv1Mda1yG"
}
```
Срок жизни access-токена - 1 час. Срок жизни refresh-токена - 1 неделя. 

Продолжительность жизни токенов можно менять в файле application.properties, который находится в структуре проекта.

В случае, если закончился срок access-токена, происходит перенаправление на URL
```
http://localhost:8080/auth/refresh
```
где происходит проверка, не истек ли срок refresh-токена. В случае, если refresh-токен не истек, выдается новый access-токен с новым сроком жизни. Если просрочен и refresh-токен, потребуется повторно авторизоваться путем ввода логина и пароля пользователя, после чего будут выданы оба токена с новым сроком действия.

### Роли пользователей
В текущей версии приложения предусмотрено 4 роли пользователей:
- RECRUITER
- ADMIN_RECRUITER
- CUSTOMER
- CUSTOMER_MANAGER

Пользователи с ролями RECRUITER и ADMIN-RECRUITER имеют доступ ко всем URL формата
```
http://localhost:8080/recruiter/**
```
Пользователи с ролями CUSTOMER имеют доступ ко всем URL формата
```
http://localhost:8080/customer/**
```
кроме
```
http://localhost:8080/recruiter/questionnaire
http://localhost:8080/recruiter/reports
```
Доступ к последним двум URL есть только у роли CUSTOMER_MANAGER. Кроме этого, у роли CUSTOMER_MANAGER есть доступ ко всем URL, доступ к которым есть у роли CUSTOMER.


## Структура БД
В структуре проекта по пути `src/main/resources/init.sql` находится скрипт для инициализации БД.
В текущей версии приложения БД состояит из одной таблицы с данными пользователей для авторизации и имеет следующую структуру:

![img](https://i.ibb.co/dWysVDy/2024-01-30-14-49-56.png)


## Авторы проекта
  - [Мылов Сергей](https://github.com/Mylov91) — Java-developer (Mylov91@yandex.ru)

  - [Ободеев Иван](https://github.com/Krabec) — FrontEnd-developer (obodeev2015@yandex.ru)
