# Employee Management System (EMS)

This Employee Management System (EMS) project is a Java-based application that provides functionalities for managing employee records in an organization. The system allows the admin to add, delete, update, and search for employee information, as well as approve leaves and update salaries. The application uses a MySQL database for data storage and retrieval.

## Features

- **Admin Login**: Secure login system for administrators.
- **Add Employee**: Add new employee records including details such as name, designation, salary, age, gender, email, and phone number.
- **Delete Employee**: Remove an employee record from the database.
- **View All Employees**: Display all employee records stored in the database.
- **Update Employee**: Modify existing employee details.
- **Search Employee**: Search for specific employee details using their ID.
- **Create Employee Detail File**: Generate a text file with detailed information about an employee.
- **Update Salary**: Change the salary of an employee and update it in the database.
- **Approve Leave**: Manage and approve or deny employee leave requests.

## Technologies Used

- **Java**: Core programming language used for application logic.
- **MySQL**: Database used for storing employee records.
- **JDBC**: Java Database Connectivity (JDBC) API for connecting and executing queries in MySQL.
- **File Handling**: Used for creating and managing employee detail files.

## Installation

1. **Install MySQL**:
   - Download and install MySQL on your machine.
   - Create a database named `ems` and the necessary tables using the provided SQL scripts.

2. **Set Up Java Environment**:
   - Install JDK (Java Development Kit) on your machine.
   - Set up your Java environment and ensure `javac` and `java` commands work in your terminal.

3. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/EMS.git
