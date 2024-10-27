```
08-database
├─ .gitattributes
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
├─ README.md
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ kr
   │  │     └─ jinju
   │  │        └─ database
   │  │           ├─ controllers
   │  │           │  ├─ DepartmentController.java
   │  │           │  ├─ MemberController.java
   │  │           │  ├─ ProfessorController.java
   │  │           │  └─ StudentController.java
   │  │           ├─ DatabaseApplication.java
   │  │           ├─ exceptions
   │  │           │  ├─ ServiceNoResultException.java
   │  │           │  └─ StringFormatException.java
   │  │           ├─ helpers
   │  │           │  ├─ FileHelper.java
   │  │           │  ├─ MailHelper.java
   │  │           │  └─ WebHelper.java
   │  │           ├─ interceptors
   │  │           │  └─ MyInterceptor.java
   │  │           ├─ mappers
   │  │           │  ├─ DepartmentMapper.java
   │  │           │  ├─ MemberMapper.java
   │  │           │  ├─ ProfessorMapper.java
   │  │           │  └─ StudentMapper.java
   │  │           ├─ models
   │  │           │  ├─ Department.java
   │  │           │  ├─ Member.java
   │  │           │  ├─ Professor.java
   │  │           │  └─ Student.java
   │  │           ├─ MyWebConfig.java
   │  │           └─ services
   │  │              ├─ impl
   │  │              │  ├─ DepartmentServiceImpl.java
   │  │              │  ├─ MemberServiceImpl.java
   │  │              │  ├─ ProfessorServiceImpl.java
   │  │              │  └─ StudentServiceImpl.java
   │  │              ├─ DepartmentService.java
   │  │              ├─ MemberService.java
   │  │              ├─ ProfessorService.java
   │  │              └─ StudentService.java
   │  └─ resources
   │     ├─ application.properties
   │     ├─ log4jdbc.log4j2.properties
   │     ├─ logback-spring.xml
   │     ├─ META-INF
   │     │  └─ additional-spring-configuration-metadata.json
   │     ├─ static
   │     │  ├─ assets
   │     │  │  └─ css
   │     │  │     ├─ common.css
   │     │  │     └─ header.css
   │     │  ├─ favicon.ico
   │     │  └─ robots.txt
   │     └─ templates
   │        ├─ department
   │        │  ├─ add.html
   │        │  ├─ detail.html
   │        │  ├─ edit.html
   │        │  └─ index.html
   │        ├─ fragments
   │        │  └─ header.html
   │        ├─ member
   │        │  └─ index.html
   │        ├─ professor
   │        │  ├─ add.html
   │        │  ├─ detail.html
   │        │  ├─ edit.html
   │        │  └─ index.html
   │        └─ student
   │           ├─ add.html
   │           ├─ detail.html
   │           ├─ edit.html
   │           └─ index.html
   └─ test
      └─ java
         └─ kr
            └─ jinju
               └─ database
                  ├─ mappers
                  │  ├─ DepartmentMapperTest.java
                  │  ├─ MemberMapperTest.java
                  │  ├─ ProfessorMapperTest.java
                  │  └─ StudentMapperTest.java
                  └─ services
                     ├─ DepartmentServiceTest.java
                     ├─ MemberServiceTest.java
                     ├─ ProfessorServiceTest.java
                     └─ StudentServiceTest.java

```
