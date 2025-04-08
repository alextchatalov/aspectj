
# Spring Boot AspectJ Logging Example

This project demonstrates how to use **AspectJ** in a **Spring Boot** application to perform **automatic logging** for key operations like creating, retrieving, and deleting accounts.

## 📜 What is an Aspect?

An **Aspect** is a modularization of a concern that cuts across multiple classes, like logging, security, or transaction management. In **Aspect-Oriented Programming (AOP)**, aspects help you separate these concerns cleanly from your business logic.

In this project, we use **AspectJ** (a popular AOP framework) to create a reusable **logging aspect** that automatically logs method execution without cluttering your business code.

---

## 🚀 How it Works

- The `@Loggable` annotation is used to **mark methods** that should be automatically logged.
- The `LoggingAspect` class intercepts method calls annotated with `@Loggable`.
- It logs **method entry**, **arguments**, **execution time**, and **method exit**.

For example:

```java
@Loggable
public Account execute(UUID id) {
    // Business logic
}
```

Any method annotated with `@Loggable` will be automatically logged.

---

## 🛠️ Project Structure

| File | Description |
|:-----|:------------|
| `@Loggable` | Custom annotation to mark methods for logging. |
| `LoggingAspect` | Aspect that captures and logs method calls at runtime. |
| `CreateAccountUseCaseImpl` | Example service for creating an account. |
| `GetAccountUseCaseImpl` | Example service for retrieving an account. |
| `DeletetAccountUseCaseImpl` | Example service for deleting an account. |
| `AccountDTO`, `AccountRequest` | DTOs used in API communication. |

---

## 🔥 Running the Application

1. Clone the repository.
2. Run the application with your favorite IDE or `mvn spring-boot:run`.
3. Use the following `curl` commands to test the endpoints:

### ➕ Create an Account
```bash
curl --location 'http://localhost:8080/accounts' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "username": "johndoe",
    "password": "securePassword123",
    "id": "a8bdb935-6bac-4621-94f3-bacbf69e4409"
}'
```

### 🔍 Get an Account
```bash
curl --location 'localhost:8080/accounts/bb2cc380-2f3d-4b0b-aa65-b216753b83a5'
```

### ❌ Delete an Account
```bash
curl --location --request DELETE 'http://localhost:8080/accounts/123e4567-e89b-12d3-a456-426614174000' --header 'Accept: application/json'
```

All these operations will automatically trigger logs thanks to the AspectJ configuration!

---

## 📋 Example Log Output

When you call one of the endpoints, you might see logs like:

```
[LOG] Entering method: GetAccountUseCaseImpl.execute(UUID)
Arguments: [bb2cc380-2f3d-4b0b-aa65-b216753b83a5]
[LOG] Exiting method: GetAccountUseCaseImpl.execute(UUID) after 12ms
Returned: Account(id=bb2cc380-2f3d-4b0b-aa65-b216753b83a5, username=Test, ...)
```

---

## 📚 References

- [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [AspectJ Documentation](https://www.eclipse.org/aspectj/)
