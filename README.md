# Пример приложения REST API на Java 18 c использованием Spring Boot

<h1 align="center"><img src="https://github.com/ram0973/springapp/blob/main/src/main/resources/spring-framework.png?raw=true" alt="Spring Application"></h1>

Задумывался для обучения и как каркас веб-приложения на Spring, чтобы можно было на
реальном примере попробовать написать бэкенд на Java и к нему фронтэнд на JS/TS или приложение Android,
которое будет получать данные из REST API. Приложение не должно переусложняться, как JHipster.

Что реализовано:

1. Пользователи (модель User) c JWT-авторизацией - роли - админ, модератор, пользователь
2. Простейшая статья (Article)
3. Для статей работает постраничная разбивка с сортировкой. Примеры:
4. Soft delete
```
/api/articles/active?page=0&size=3&sort=published,desc&sort=title,asc
/api/articles/active?page=0&size=3&sort=published,desc&sort=title,asc&sort=published,desc
Тут page - номер страницы, size - размер страницы, sort - правила сортировки
```

Что надо сделать TODO:

1. Теги в статьях (Tag)
2. Комментарии в статьях (Comment)
3. Тесты
4. Деплой на сервер (конфиг nginx c https, docker и прочее)
5. Попробовать генератор http://www.telosys.org/
6. Посмотреть что можно взять из JHipster
7. Конфигурации запуска dev, staging, prod
8. Миграции
9. Разобраться с Jpa Buddy
10. https://www.testcontainers.org/


Внимание: приложение не готово к production среде, и использованные решения могут быть ошибочными/небезопасными.

## Требования (в чём тестировалось)
### Установка зависимостей описана в README-SECONDARY

0. OS: Debian testing (bookworm)
1. Java JDK 18
2. Maven 3.6.3
3. Postgresql 14.4
4. Docker 20.10
5. Docker-compose 2.9.0
6. NodeJs 16.16.0
7. NPM 8.11.0

# Установка приложения

## Загрузка исходного кода

```shell
git clone https://github.com/ram0973/springapp
cd springapp
```

## Настройки приложения

Настройки лежат в src/main/resources/application.properties
Если нужно сменить среду выполнения, нужно поменять в этом файле свойство spring.profiles.active на нужное

### Запуск приложения с помощью Docker compose:
(Также можно пользовать командами из Makefile, например make up-dev)

```shell
# Development
docker-compose up
# Production
docker-compose -f docker-compose-prod.yml up
```

## Документация (Swagger)

Документация по Api в формате JSON: /v3/api-docs
Swagger главный интерфейс: / или /swagger-ui.html

## Devtools

При разработке приложения произойдёт рестарт приложения при следующих действиях:
1. В Eclipse - сохранение файла;
2. В IntelliJ IDEA - сборка проекта (Build -> Build Project | Ctrl-F9);
3. Запуск mvn compile.


# Frontend

Написан как приложение React + SSR + SSG

## Запуск
```shell
cd springapp/frontend/react
yarn && yarn-dev
```

## Лицензия: MIT
