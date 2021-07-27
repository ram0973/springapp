# Пример приложения REST API на языке Java 16 c использованием Spring

<h1 align="center"><img src="https://github.com/ram0973/springapp/blob/main/src/main/resources/spring-framework.png?raw=true" alt="Spring Application"></h1>

Задумывался для обучения и как каркас веб-приложения на Spring, чтобы можно было на 
реальном примере попробовать написать бэкенд на Java и к нему фронтэнд на JS/TS или приложение Android,    
которое будет получать данные из REST API. Приложение не должно переусложняться, как JHipster.

Что реализовано:
1. Пользователи (модель User) c JWT-авторизацией - роли (Role, Role Enum) - админ, модератор, пользователь
2. Простейшая статья (Article)
3. Для статей работает постраничная разбивка с сортировкой. Примеры:
```
/api/articles/active?page=0&size=3&sort=published,desc&sort=title,asc
/api/articles/active?page=0&size=3&sort=published,desc&sort=title,asc&sort=published,desc
Тут page - номер страницы, size - размер страницы, sort - правила сортировки
```

Что надо сделать:

0. Logout
1. Теги в статьях (Tag)
2. Комментарии в статьях (Comment)
3. Тесты
4. Деплой на сервер (конфиг nginx c https, docker и прочее)
5. Попробовать генератор http://www.telosys.org/
6. Посмотреть что можно взять из JHipster
7. ~~Конфигурации запуска dev, staging, prod~~
8. Миграции
9. Разобраться с Jpa Buddy

Внимание: приложение не готово к production среде, и использованные решения могут быть ошибочными/небезопасными. 

## Требования (в чём тестировалось)
0. Ubuntu 20.04.2 или WSL
1. Java JDK 16
2. Maven 3.8.1 
3. Postgresql 12

## Установка необходимых зависимостей и настройка базы

Можно (не обязательно) поставить sdkman - утилиту для установки Maven, Gradle, JDK, spring console, etc. 
Список приложений можно получить командой sdk list


```shell
sudo apt install zip unzip
sudo curl -s "https://get.sdkman.io" | bash
# Follow the instructions on-screen to complete installation.
# Next, open a new terminal or enter:
$ . "$HOME/.sdkman/bin/sdkman-init.sh" # also add this to .bashrc
sdk version
```
Установка Java 16 JDK (Ubuntu 20.04/WSL) (либо можно поставить с помощью sdk: sdk list java)
```shell
sudo add-apt-repository ppa:linuxuprising/java
sudo apt-get update
sudo apt-get install oracle-java16-installer --install-recommends
java --version
```

Установка Maven 3.8.1 (либо можно поставить с помощью sdk: sdk list maven)
```shell
cd /opt
sudo wget https://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
sudo tar xzvf apache-maven-3.8.1-bin.tar.gz
sudo rm apache-maven-3.8.1-bin.tar.gz
sudo nano /etc/environments
# добавить в конце -> :/opt/apache-maven-3.8.1/bin
. /etc/environments
mvn --version
```

Установка Postgresql 12:
```shell
sudo apt install postgresql-12 -y
```
Пример настройки базы:
```shell
sudo -u postgres psql
postgres= create database springapp;
postgres= create user springapp with encrypted password 'springapp';
postgres= grant all privileges on database springapp to springapp;
postgres= \connect springapp
postgres= \q
```

## Загрузка исходного кода
```shell
git clone https://github.com/ram0973/springapp
cd springapp
```
## Настройки приложения
Настройки лежат в src/main/resources/application.properties
Если нужно сменить среду выполнения, нужно поменять в этом файле свойство spring.profiles.active на нужное
## 

## Запуск приложения из исходников
$ mvn spring-boot:run

## Создание jar и запуск приложения
```shell
mvn package && java -jar target/*.jar

Также, благодаря такой настройке в pom.xml

<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
Собирается executable (исполнимый) jar:

Поэтому запустить можно и так:
chmod +x target/*.jar
target/*.jar
````
### Управление jar с помощью systemd:
```
Создать скрипт myapp.service в /etc/systemd/system:
 
[Unit]
Description=myapp
After=syslog.target

[Service]
User=myapp
ExecStart=/var/myapp/myapp.jar
SuccessExitStatus=143
TimeoutStopSec=10

[Install]
WantedBy=multi-user.target
```

### Запуск приложения с помощью Docker - отдельные контейнеры: 
```shell
# Запуск приложения в dev среде, без отдельной docker сети:

# Способ 1 (приложение в контейнере)

# БД Postgresql, на хосте не должен быть запущен Postgresql
docker build . -f ./docker/postgresql/Dockerfile -t psqldb:latest
docker run --network host psqldb:latest
# Добавить создание базы и прочее 

# Приложение в контейнере
mvn package
docker build . -f ./docker/springapp/Dockerfile -t springapp:latest
docker run --network host springapp:latest

# Способ 2 (приложение без контейнера, на хосте не должен быть запущен Postgresql)
docker build . -f ./docker/postgresql/Dockerfile -t psqldb:latest
docker run --network host psqldb:latest # на хосте
mvn spring-boot:run



```

## Тестовые запросы к Rest API можно делать с Postman, примеры:
Регистрация:
```
POST http://localhost:8080/api/auth/signup
Body: RAW JSON
{
    "username": "user",
    "email": "user@acme.com",
    "password": "123456",
}
```
Вход:
```
POST http://localhost:8080/api/auth/login
Body: RAW JSON
{
"username": "user",
"password": "123456"
}

Пример ответа:
{
    "id": 1,
    "username": "bilbo",
    "email": "bilbo@baggins.com",
    "roles": [],
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaWxibyIsImlhdCI6MTYyNTcyODQ2MSwiZXhwIjoxNjI1ODE0ODYxfQ.ck5K-iwmleK9LmbMBev_orB-PbN7DNQpqWoWcT4VJZ3eUPYEv-e2TG6Trf4AEyAoiaq2kApgF5AYVq3etS_BoA",
    "tokenType": "Bearer"
}
```
Список пользователей (защищённое api):
```
GET http://localhost:8080/api/users
Authorization - Bearer token - взять из ответа который выдается при логине
```
Список активных статей (открытое api):
```
GET http://localhost:8080/api/articles/active
```
## Документация (Swagger)


Документация по Api в формате JSON: http://localhost:8080/v3/api-docs

Swagger главный интерфейс: http://localhost:8080/swagger-ui


## Spring Boot Actuator

Можно получать разнообразные данные о работе приложении в формате JSON 

[Документация](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

Использование — открыть один из URL, например http://localhost:8080/actuator/health

## Консоль spring boot

Можно установить консоль spring, с помощью которой можно создать новое приложение spring boot, получить захешированный 
пароль и другое:

```shell
sdk list springboot
sdk install springboot "VERSION"
spring version
```

## Devtools

При разработке приложения произойдёт рестарт приложения при следующем действии: 

В Eclipse - сохранение файла;

В IntelliJ IDEA - сборка проекта (Build -> Build Project | Ctrl-F9);

Запуск mvn compile.

## Лицензия: MIT
