
```
05_cookie_session
├─ .gitignore
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ logs
│  ├─ err_log.log
│  └─ log.log
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ kr
   │  │     └─ jinju
   │  │        └─ cookie_session
   │  │           ├─ controllers
   │  │           │  ├─ CookieController.java
   │  │           │  ├─ HomeController.java
   │  │           │  └─ SessionController.java
   │  │           ├─ CookieSessionApplication.java
   │  │           ├─ exceptions
   │  │           │  └─ StringFormatException.java
   │  │           ├─ helpers
   │  │           │  ├─ RegexHelper.java
   │  │           │  └─ UtilHelper.java
   │  │           ├─ interceptors
   │  │           │  └─ MyInterceptor.java
   │  │           └─ MyWebConfig.java
   │  └─ resources
   │     ├─ application.properties
   │     ├─ logback-spring.xml
   │     ├─ static
   │     │  ├─ assets
   │     │  │  └─ css
   │     │  │     └─ header.css
   │     │  ├─ favicon.ico
   │     │  └─ robots.txt
   │     └─ templates
   │        ├─ cookie
   │        │  ├─ home.html
   │        │  └─ popup.html
   │        ├─ fragments
   │        │  └─ header.html
   │        ├─ index.html
   │        └─ session
   │           ├─ home.html
   │           └─ login.html
   └─ test
      └─ java
         └─ kr
            └─ jinju
               └─ cookie_session
                  └─ CookieSessionApplicationTests.java

```