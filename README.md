## SpringDataRepoWithSpringBoot
Example. Create project with Spring Boot

## Заметки

### Пошагово

Шаг 1: Создайте новый проект Spring Boot.

Используйте Spring Initializr (https://start.spring.io) или вашу среду разработки для создания нового проекта Spring Boot.
Укажите необходимые зависимости, такие как Spring Data JPA, Spring Web, Mysql, Lombok.

Шаг 2: Определите сущность Employee.

Создайте новый класс Employee с аннотацией @Entity.
Определите поля id, name, position и phone и аннотируйте их соответствующими аннотациями JPA, такими как @Id, @GeneratedValue и @Column.

```
@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String position;

    private String phone;
}
```

Шаг 3: Создайте интерфейс репозитория EmployeeRepository.

Создайте новый интерфейс EmployeeRepository, который расширяет CrudRepository<Employee, Integer>.
Благодаря наследованию от CrudRepository, вам не нужно объявлять стандартные методы, так как они уже доступны.

```
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Дополнительные методы репозитория (если необходимо)
}
```

Шаг 4: Настройте подключение к базе данных.

В файле application.properties (или application.yml) определите настройки подключения к базе данных.
Включите поддержку JPA и Hibernate, указав соответствующие свойства.

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/itvdn
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql= true
```

Шаг 5: Используйте репозиторий в вашем коде.

В классе или сервисе, где вы хотите использовать репозиторий, добавьте поле типа EmployeeRepository и аннотацию @Autowired.
Используйте методы репозитория, такие как save, findById, findAll, delete и другие, в своем коде.

```
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return employeeRepo.findAll(pageable);
    }
}
```

### В чем разница между JpaRepository и crudRepository

JpaRepository и CrudRepository являются интерфейсами из Spring Data JPA, предназначенными для упрощения доступа к базе данных при разработке приложений на основе JPA (Java Persistence API). Оба интерфейса предоставляют набор методов для выполнения операций CRUD (Create, Read, Update, Delete) над сущностями.

Разница между JpaRepository и CrudRepository заключается в том, что JpaRepository является расширением CrudRepository и предоставляет дополнительные функции, связанные с работой с JPA.

JpaRepository наследует CrudRepository и добавляет следующие возможности:

Поддержка пагинации и сортировки результатов запросов.

1. Возможность выполнения именованных запросов (named queries) с использованием аннотации @Query.
2. Методы для выполнения массовых операций сущностей, таких как удаление в пакетном режиме (batch delete) или сохранение коллекции сущностей.
3. Методы для работы с EntityManager'ом, такие как flush(), refresh() и др.
   
Таким образом, JpaRepository предоставляет более мощные возможности для работы с JPA и расширяет функциональность, доступную в CrudRepository. Если вам необходимы продвинутые функции JPA, рекомендуется использовать JpaRepository. Если же вам достаточно базовых операций CRUD, вы можете использовать CrudRepository.

### PageRequest.of

```
Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
```

- pageNumber - это номер страницы, которую вы хотите получить. Нумерация страниц начинается с нуля, что означает, что первая страница имеет номер 0, вторая страница - номер 1 и так далее.

- pageSize - это количество сущностей (в данном случае, объектов Person), которые должны быть возвращены на одной странице. Он определяет размер порций, в которых данные извлекаются из базы данных.

- Sort.by(sortBy) - определяет критерии сортировки для результатов запроса. Sort.by() является фабричным методом, который создает объект Sort на основе указанных параметров.

- sortBy - это имя поля, по которому вы хотите выполнить сортировку результатов. Имя поля должно соответствовать имени поля в классе сущности Person.

По умолчанию, сортировка осуществляется в порядке возрастания. Если вы хотите явно указать направление сортировки, вы можете использовать Sort.Direction.DESC для сортировки по убыванию или Sort.Direction.ASC для сортировки по возрастанию.

Собирая все вместе, PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)) создает объект PageRequest, который реализует интерфейс Pageable. Этот объект Pageable инкапсулирует информацию о номере страницы, размере страницы и критериях сортировки, позволяя получить определенную страницу отсортированных результатов из репозитория.
