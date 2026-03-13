# Pharmacy Supply Management System

A comprehensive Spring Boot REST API application demonstrating advanced database relationships, hierarchical data structures, and enterprise-level Java development practices.

## Project Information

**Student:** Shema Ken  
**Student ID:** 26503  
**Group:** E  
**University:** Adventist University of Central Africa (AUCA)  
**Course:** Web Technology and Internet  
**Project Type:** Midterm Practical Examination  
**Date:** March 2026

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [API Endpoints](#api-endpoints)
- [Testing Guide](#testing-guide)
- [Assessment Coverage](#assessment-coverage)
- [Key Implementations](#key-implementations)
- [Documentation](#documentation)

---

## Overview

This project is a fully functional Pharmacy Supply Management System built with Spring Boot and PostgreSQL. It demonstrates:

- Complex database relationships (One-to-One, One-to-Many, Many-to-Many)
- Hierarchical location structure using Adjacency List Model
- Cascade operations for automatic data persistence
- Pagination and sorting capabilities
- Derived query methods
- RESTful API design
- Rwanda administrative location hierarchy (Province → District → Sector → Cell → Village)

---

## Features

### Core Functionality
- **User Management** - Create and manage pharmacy users linked to specific locations
- **Drug Inventory** - Track drugs with categories and suppliers
- **Category Management** - Organize drugs into categories
- **Supplier Management** - Manage drug suppliers with many-to-many relationships
- **Location Hierarchy** - Rwanda's 5-level administrative structure

### Advanced Features
- **Cascade Save** - Automatically save entire location hierarchies
- **Pagination** - Efficient data retrieval with page-based loading
- **Sorting** - Dynamic sorting by any field
- **Province Query** - 4-level JOIN query to find users by province
- **Existence Check** - Verify user email uniqueness
- **Data Seeding** - Pre-populated Rwanda locations and sample data

---

## Technologies Used

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.2.3** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework
- **PostgreSQL** - Relational database
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management

### Tools
- **Postman** - API testing
- **Git** - Version control
- **IntelliJ IDEA / VS Code** - IDE

---

## Database Schema

### Entities

#### 1. User
- **Relationship:** One-to-One with Location
- **Fields:** id, name, email, location_id

#### 2. Location
- **Relationship:** Self-referencing (parent-child hierarchy)
- **Fields:** id, name, code, type, parent_id
- **Types:** PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE

#### 3. Drug
- **Relationships:** 
  - Many-to-One with Category
  - Many-to-Many with Supplier
- **Fields:** id, name, price, category_id

#### 4. Category
- **Relationship:** One-to-Many with Drug
- **Fields:** id, name

#### 5. Supplier
- **Relationship:** Many-to-Many with Drug (via supplier_drug join table)
- **Fields:** id, name, contactEmail

### ERD Diagram

```
┌─────────────┐         ┌──────────────┐
│    User     │────────▶│   Location   │
│             │ 1:1     │              │
│ - id        │         │ - id         │
│ - name      │         │ - name       │
│ - email     │         │ - code       │
│ - location  │         │ - type       │
└─────────────┘         │ - parent ◀───┼─┐
                        └──────────────┘ │
                                         │ Self-
                                         │ Reference
                                         └─┘

┌─────────────┐         ┌──────────────┐
│  Category   │────────▶│     Drug     │
│             │ 1:N     │              │
│ - id        │         │ - id         │
│ - name      │         │ - name       │
└─────────────┘         │ - price      │
                        │ - category   │
                        └──────┬───────┘
                               │ M:N
                        ┌──────▼───────┐
                        │supplier_drug │
                        │ (join table) │
                        └──────┬───────┘
                               │
                        ┌──────▼───────┐
                        │   Supplier   │
                        │              │
                        │ - id         │
                        │ - name       │
                        │ - email      │
                        └──────────────┘
```

---

## Project Structure

```
PharmacySupplySystem/
├── src/
│   ├── main/
│   │   ├── java/com/pharmacy/
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   ├── Location.java
│   │   │   │   ├── LocationType.java
│   │   │   │   ├── Drug.java
│   │   │   │   ├── Category.java
│   │   │   │   └── Supplier.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── LocationRepository.java
│   │   │   │   ├── DrugRepository.java
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   └── SupplierRepository.java
│   │   │   ├── service/
│   │   │   │   └── PharmacyService.java
│   │   │   ├── controller/
│   │   │   │   └── PharmacyController.java
│   │   │   ├── config/
│   │   │   │   └── RwandaDataSeeder.java
│   │   │   └── PharmacyApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
├── docs/
│   ├── API_TESTING_GUIDE.md
│   ├── SIMPLE_TESTING_GUIDE.md
│   ├── LOCATION_POSTMAN_API_GUIDE.md
│   ├── QUICK_API_REFERENCE.md
│   ├── EXAM_READY_SUMMARY.md
│   ├── LOCATION_HIERARCHY_THEORY.md
│   ├── RWANDA_LOCATION_TESTING_GUIDE.md
│   ├── WORKING_ENDPOINTS_GUIDE.md
│   └── FINAL_STATUS.md
├── pom.xml
└── README.md
```

---

## Installation & Setup

### Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher
- Git

### Step 1: Clone the Repository
```bash
git clone https://github.com/shemaken173/midterm_26503_groupE.git
cd midterm_26503_groupE
```

### Step 2: Configure Database
Create a PostgreSQL database:
```sql
CREATE DATABASE pharmacysupplymanagementsystem_db;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pharmacysupplymanagementsystem_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Step 3: Build the Project
```bash
./mvnw clean install
```

### Step 4: Run the Application
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### Step 5: Verify Installation
```bash
curl http://localhost:8080/api/pharmacy/categories
```

---

## API Endpoints

### User Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/pharmacy/users` | Create user with location hierarchy |
| GET | `/api/pharmacy/users/exists?email={email}` | Check if user exists |
| GET | `/api/pharmacy/users/province?code={code}` | Get users by province code |
| GET | `/api/pharmacy/users/province?name={name}` | Get users by province name |
| PUT | `/api/pharmacy/users/{id}` | Update user |
| DELETE | `/api/pharmacy/users/{id}` | Delete user |

### Drug Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/pharmacy/drugs` | Create drug |
| GET | `/api/pharmacy/drugs` | Get drugs (paginated & sorted) |
| GET | `/api/pharmacy/drugs/all` | Get all drugs |
| GET | `/api/pharmacy/drugs/{id}` | Get drug by ID |
| PUT | `/api/pharmacy/drugs/{id}` | Update drug |
| DELETE | `/api/pharmacy/drugs/{id}` | Delete drug |

### Category Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/pharmacy/categories` | Create category |
| GET | `/api/pharmacy/categories` | Get all categories |
| GET | `/api/pharmacy/categories/{id}` | Get category by ID |
| GET | `/api/pharmacy/categories/{id}/drugs` | Get drugs in category |
| PUT | `/api/pharmacy/categories/{id}` | Update category |
| DELETE | `/api/pharmacy/categories/{id}` | Delete category |

### Supplier Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/pharmacy/suppliers` | Create supplier |
| GET | `/api/pharmacy/suppliers` | Get all suppliers |
| GET | `/api/pharmacy/suppliers/{id}` | Get supplier by ID |
| GET | `/api/pharmacy/suppliers/{id}/drugs` | Get drugs from supplier |
| POST | `/api/pharmacy/suppliers/{supplierId}/drugs/{drugId}` | Add drug to supplier |
| PUT | `/api/pharmacy/suppliers/{id}` | Update supplier |
| DELETE | `/api/pharmacy/suppliers/{id}` | Delete supplier |

### Location Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/pharmacy/locations` | Get all locations |
| GET | `/api/pharmacy/locations/{id}` | Get location by ID |

---

## Testing Guide

### Example 1: Create User with Location Hierarchy (Cascade Save)

```bash
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Shema Ken",
  "email": "shema.ken@auca.ac.rw",
  "location": {
    "name": "Nyagatovu Village",
    "code": "NYG",
    "type": "VILLAGE",
    "parent": {
      "name": "Bibare Cell",
      "code": "BIB",
      "type": "CELL",
      "parent": {
        "name": "Kimironko Sector",
        "code": "KIM",
        "type": "SECTOR",
        "parent": {
          "name": "Gasabo District",
          "code": "GAS",
          "type": "DISTRICT",
          "parent": {
            "name": "Kigali City",
            "code": "KGL",
            "type": "PROVINCE"
          }
        }
      }
    }
  }
}
```

### Example 2: Get Drugs with Pagination & Sorting

```bash
GET http://localhost:8080/api/pharmacy/drugs?page=0&size=5&sortBy=name
```

### Example 3: Check User Exists

```bash
GET http://localhost:8080/api/pharmacy/users/exists?email=shema.ken@auca.ac.rw
```

### Example 4: Get Users by Province

```bash
GET http://localhost:8080/api/pharmacy/users/province?code=KGL
```

For more examples, see the [API Testing Guide](API_TESTING_GUIDE.md).

---

## Key Implementations

### 1. Adjacency List Model (Location Hierarchy)

The location hierarchy uses a self-referencing table where each location has a `parent_id` pointing to another location:

```java
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "parent_id")
private Location parent;
```

This creates a tree structure:
```
Kigali City (Province)
  └─ Gasabo (District)
      └─ Kimironko (Sector)
          └─ Bibare (Cell)
              └─ Nyagatovu (Village)
```

### 2. Cascade Save Operation

When saving a user with a location hierarchy, all 5 location records are automatically saved:

```java
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "location_id")
private Location location;
```

### 3. 4-Level JOIN Query

Finding users by province requires traversing 4 levels:

```java
@Query("SELECT u FROM User u " +
       "JOIN u.location village " +
       "JOIN village.parent cell " +
       "JOIN cell.parent sector " +
       "JOIN sector.parent district " +
       "JOIN district.parent province " +
       "WHERE province.code = :code OR province.name = :name")
List<User> findUsersByProvince(@Param("code") String code, @Param("name") String name);
```

### 4. Pagination & Sorting

```java
@GetMapping("/drugs")
public Page<Drug> getDrugs(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "name") String sortBy) {
    return pharmacyService.getDrugs(page, size, sortBy);
}
```

### 5. Derived Query Method

```java
boolean existsByEmail(String email);
```

Spring Data JPA automatically generates:
```sql
SELECT COUNT(*) > 0 FROM users WHERE email = ?
```

---

## Documentation

Comprehensive documentation is available in the following files:

- **API_TESTING_GUIDE.md** - Complete API testing guide
- **SIMPLE_TESTING_GUIDE.md** - Step-by-step testing
- **LOCATION_POSTMAN_API_GUIDE.md** - Postman examples
- **QUICK_API_REFERENCE.md** - Quick reference
- **EXAM_READY_SUMMARY.md** - Exam preparation
- **LOCATION_HIERARCHY_THEORY.md** - Theory explanation
- **RWANDA_LOCATION_TESTING_GUIDE.md** - Rwanda locations
- **WORKING_ENDPOINTS_GUIDE.md** - Working endpoints
- **FINAL_STATUS.md** - Final status report

---

## Seeded Data

The application comes pre-populated with:

### Locations
- 2 Provinces (Kigali City, Eastern Province)
- Multiple Districts, Sectors, Cells, and Villages

### Categories
1. Antibiotics
2. Pain Relievers
3. Vitamins

### Drugs
1. Amoxicillin 500mg (Antibiotic)
2. Paracetamol (Pain Reliever)
3. Ibuprofen (Pain Reliever)
4. Vitamin C (Vitamin)
5. Vitamin D (Vitamin)

### Suppliers
1. PharmaCorp Ltd
2. MediSupply Inc
3. HealthGoods Co

### Users
6 sample users linked to villages in different provinces

---

## Troubleshooting

### Issue: Application won't start
**Solution:** Check if PostgreSQL is running and database credentials are correct

### Issue: Port 8080 already in use
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection refused
**Solution:** Verify PostgreSQL is running:
```bash
sudo service postgresql status
```

---

## Contributing

This is an academic project for examination purposes. Contributions are not accepted.

---

## License

This project is created for educational purposes as part of the Web Technology and Internet course at AUCA.

---

## Author

**Shema Ken**  
Student ID: 26503  
Group: E  
Adventist University of Central Africa (AUCA)  
Email: shema.ken@auca.ac.rw

---

## Acknowledgments

- AUCA Faculty of Information Technology
- Web Technology and Internet Course Instructor
- Spring Boot Documentation
- PostgreSQL Community

---

## Contact

For questions or clarifications about this project:
- Email: shema.ken@auca.ac.rw
- GitHub: [@shemaken173](https://github.com/shemaken173)

---

**Last Updated:** March 13, 2026  
**Status:** Production Ready  
**Version:** 1.0.0

---

If you find this project helpful, please give it a star on GitHub!
