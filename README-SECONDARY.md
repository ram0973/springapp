## Установка зависимостей (без Docker) и настройка базы

Можно (необязательно) поставить sdkman - утилиту для установки Maven, Gradle, JDK, spring console, etc.
Список приложений можно получить командой sdk list

Установка Java 18 JDK (на Debian bookworm) + Maven 3.8.1

```shell
curl -fsSL https://download.bell-sw.com/pki/GPG-KEY-bellsoft | sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/bellsoft.gpg
echo "deb [arch=amd64] https://apt.bell-sw.com/ stable main" | sudo tee /etc/apt/sources.list.d/bellsoft.list
sudo apt-get update
sudo apt install bellsoft-java18-full

cd /opt
sudo wget https://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.zip
sudo unzip apache-maven-3.8.1-bin.zip
rm apache-maven-3.8.1-bin.zip
# в /etc/profile:
export JAVA_HOME=/usr/lib/jvm/bellsoft-java18-full-amd64
export PATH=$JAVA_HOME/bin:$PATH
export MAVEN_HOME=/opt/apache-maven-3.8.1
export PATH=$MAVEN_HOME/bin:$PATH

java --version
mvn --version
```

Установка Postgresql 14:

```shell
sudo apt install postgresql-14 -y
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

Установка Docker:

```shell
sudo apt install docker.io docker-compose -y
```

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
docker run -e POSTGRES_PASSWORD=postgres --network host psqldb:latest
# Добавить создание базы и прочее

# Приложение в контейнере
mvn package
docker build . -f ./docker/springapp/Dockerfile -t springapp:latest
docker run -e POSTGRES_PASSWORD=postgres --network host springapp:latest

# Способ 2 (приложение без контейнера, на хосте не должен быть запущен Postgresql)
docker build . -f ./docker/postgresql/Dockerfile -t psqldb:latest
docker run --network host -e POSTGRES_PASSWORD=postgres psqldb:latest #
mvn spring-boot:run
```

## Hal Explorer

HAL Explorer /api/explorer

## Тестовые запросы к Rest API можно делать с Postman, примеры:

Регистрация:

```
POST http://localhost:8080/api/auth/signup
Body: RAW JSON
{
    "email": "user@acme.com",
    "password": "123456",
}
```

Вход:

```
POST http://localhost:8080/api/auth/login
Body: RAW JSON
{
    "email": "bilbo@baggins.com",
    "password": "123123"
}

Пример ответа:
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaWxib0BiYWdnaW5zLmNvbSIsImV4cCI6MTY1ODgxOTIyMCwicm9sZXMiOlsiUk9MRV9VU0VSIl19.S_oTrDvTL6hjYpMJAdgsz3uuYc8WqcINIfyEQj2e_UmHm3-Ua61XO1WdLC0kDVtsa6dh136ADbykMi23RuNrpA",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaWxib0BiYWdnaW5zLmNvbSIsImV4cCI6MTY2MTQxMDkyMX0.rBoqfdPfWH5qBBNG6NQdBuieJzGk-WEFA1efXujtHEkMkihAzWauJudk_cwq3ufyar3_XrJncHZcTvzRV4EDPg"
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

## Spring Boot Actuator

Можно получать разнообразные данные о работе приложении в формате JSON

[Документация](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

Использование — открыть один из URL, например http://localhost:8080/actuator/health

## Консоль spring boot

Можно установить консоль spring, с помощью которой можно создать новое приложение spring boot, получить захешированный
пароль и другое:

```shell
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk list springboot
sdk install springboot "2.7.2"
spring version
sdk install springboot "VERSION"
# Spring CLI v2.7.2
```

# Frontend

## Установка NodeJS + npm + yarn
```bash
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
nvm install --lts
npm -g install yarn
```