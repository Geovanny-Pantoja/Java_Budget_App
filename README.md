# Project Name: Budget Tracker

## Project Description
Budget Tracker is a Java console application that allows users to manage their personal finances by recording income, expenses, categories, and category allocations.  
The system supports multiple users, persistent data storage using SQLite, and a structured menu‑driven interface.  
The project demonstrates strong Object‑Oriented Programming (OOP) principles throughout its design and implementation.

## Video Demo
- https://youtu.be/BOioEvPeOW0

## How OOP Features Were Integrated
The Budget Tracker was intentionally built using core OOP concepts to create a modular, maintainable, and scalable application:

- **Inheritance**  
  The `Transaction` abstract class is extended by `Income` and `Expense`, allowing shared behavior while supporting different financial effects.

- **Abstraction**  
  Abstract classes and interfaces (such as `Transaction` and `Persistable`) define required behaviors without exposing implementation details.

- **Polymorphism**  
  The system stores all transactions in a single list and processes them through the parent type.  
  At runtime, the correct subclass behavior (Income or Expense) is automatically applied.

- **Encapsulation**  
  All data fields are private and accessed through getters/setters.  
  Input validation is centralized in `InputHelper`, keeping other classes clean and protected.

- **Composition**  
  Larger components are built from smaller ones:  
  - `User` contains a `Budget`  
  - `Budget` contains transactions and category allocations  
  - Managers (UserManager, CategoryManager, CategoryAllocationManager, TransactionManager) coordinate logic  
  - The `App` class composes all managers and helpers to run the program  

These OOP features work together to create a clean architecture where responsibilities are clearly separated and easy to maintain.

## Project Tasks

- **Task 1: Set up the development environment**
  - Install Java and required tools  
  - Configure Git and GitHub repository  

- **Task 2: Design the application**
  - Plan class structure and OOP relationships  
  - Design menu flow and user interactions  

- **Task 3: Implement the model layer**
  - Create User, Budget, Category, CategoryAllocation, and Transaction hierarchy  
  - Integrate OOP features into the class design  

- **Task 4: Implement managers**
  - Build UserManager, CategoryManager, CategoryAllocationManager, and TransactionManager  
  - Handle CRUD operations for all major components  

- **Task 5: Integrate SQLite database**
  - Create tables for users, categories, allocations, and transactions  
  - Implement DAO classes for database persistence  

- **Task 6: Build input/output system**
  - Implement InputHelper for validation  
  - Implement OutputHelper for menus and formatting  

- **Task 7: Test the application**
  - Test menu navigation and user flows  
  - Validate database operations and error handling  

- **Task 8: Deploy and finalize**
  - Package the application for execution  
  - Clean up UI and error messages  

- **Task 9: Document the project**
  - Create README file  
  - Provide design documentation and class descriptions  

## Project Skills Learned

- Object‑Oriented Programming (inheritance, abstraction, polymorphism, encapsulation, composition)  
- Modular application design and separation of concerns  
- Database integration using SQLite and JDBC  
- Input validation and error handling  
- Menu‑driven console UI development  
- Version control with Git and GitHub  
- Writing technical documentation  

## Language Used
- **Java** — core application logic  
- **SQLite** — persistent data storage  
- **Markdown** — documentation  

## Development Process Used
- **Iterative development**
  - Build features step‑by‑step  
  - Test and refine continuously  
- **Modular design**
  - Each class has a single responsibility  
  - Managers coordinate logic, models store data  

## Notes
- Ensure SQLite JDBC driver is included in the project  
- Database file is created automatically on first run  
- All input validation is handled through `InputHelper`  
- All menu formatting is handled through `OutputHelper`  

## Link to Project
Budget Tracker Repository (https://github.com/username/budget-tracker)



