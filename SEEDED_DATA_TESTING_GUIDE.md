# SEEDED DATA TESTING GUIDE
## Pharmacy Supply System - Pre-populated Database

**The database is automatically populated when the application starts!**

---

## What's Already in the Database?

### Categories (5)
1. Antibiotics (id: 1)
2. Pain Relievers (id: 2)
3. Vitamins (id: 3)
4. Antihistamines (id: 4)
5. Antacids (id: 5)

### Drugs (18)
**Antibiotics:**
- Amoxicillin 500mg ($15.99)
- Penicillin 250mg ($18.50)
- Ciprofloxacin 500mg ($22.00)
- Azithromycin 250mg ($25.99)

**Pain Relievers:**
- Aspirin 500mg ($9.99)
- Ibuprofen 400mg ($11.99)
- Paracetamol 500mg ($7.50)
- Naproxen 250mg ($13.99)

**Vitamins:**
- Vitamin C 1000mg ($12.50)
- Vitamin D3 2000IU ($14.99)
- Multivitamin Complex ($19.99)
- Vitamin B12 1000mcg ($16.50)

**Antihistamines:**
- Cetirizine 10mg ($8.99)
- Loratadine 10mg ($9.50)
- Diphenhydramine 25mg ($7.99)

**Antacids:**
- Omeprazole 20mg ($17.99)
- Ranitidine 150mg ($12.99)
- Calcium Carbonate 500mg ($6.99)

### Users (8) with Locations
1. Shema Ken - ken@auca.ac.rw (Kigali City - KC)
2. Alice Mukamana - alice@example.com (Eastern Province - EP)
3. Bob Nkusi - bob@example.com (Kigali City - KC)
4. Grace Uwase - grace@example.com (Northern Province - NP)
5. David Habimana - david@example.com (Southern Province - SP)
6. Sarah Ingabire - sarah@example.com (Western Province - WP)
7. John Mugisha - john@example.com (Kigali City - KC)
8. Mary Umutoni - mary@example.com (Eastern Province - EP)

### Suppliers (5)
1. MedSupply Rwanda Ltd
2. PharmaCorp International
3. HealthPlus Distributors
4. Global Pharma Solutions
5. Rwanda Medical Supplies

### Supplier-Drug Relationships (Many-to-Many)
- Each supplier supplies multiple drugs
- Some drugs are supplied by multiple suppliers

---

## TESTING ALL ASSESSMENT POINTS

### ✅ Point 1: ERD with 5 Tables (3 Marks)

**Verify tables exist:**
```
GET http://localhost:8080/api/pharmacy/categories
GET http://localhost:8080/api/pharmacy/drugs/all
GET http://localhost:8080/api/pharmacy/users/province?code=KC
GET http://localhost:8080/api/pharmacy/locations
GET http://localhost:8080/api/pharmacy/suppliers
```

---

### ✅ Point 2: Saving Location (2 Marks)

**Test cascade save - Create new user with location:**
```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Test User",
  "email": "test@example.com",
  "location": {
    "addressLine": "Test Address",
    "provinceName": "Test Province",
    "provinceCode": "TP"
  }
}
```

**Explanation:** Location is saved automatically via CascadeType.ALL

---

### ✅ Point 3: Pagination & Sorting (5 Marks)

**Test 1: First page (2 drugs, sorted by name)**
```
GET http://localhost:8080/api/pharmacy/drugs?page=0&size=2&sortBy=name
```
**Expected:** Shows first 2 drugs alphabetically

**Test 2: Second page**
```
GET http://localhost:8080/api/pharmacy/drugs?page=1&size=2&sortBy=name
```
**Expected:** Shows next 2 drugs

**Test 3: Sort by price (cheapest first)**
```
GET http://localhost:8080/api/pharmacy/drugs?page=0&size=10&sortBy=price
```
**Expected:** Calcium Carbonate ($6.99) should be first

**Test 4: Large page size**
```
GET http://localhost:8080/api/pharmacy/drugs?page=0&size=5&sortBy=price
```

---

### ✅ Point 4: Many-to-Many Relationship (3 Marks)

**Test 1: Get all drugs from Supplier 1 (MedSupply)**
```
GET http://localhost:8080/api/pharmacy/suppliers/1/drugs
```
**Expected:** Returns 6 drugs

**Test 2: Get all drugs from Supplier 2 (PharmaCorp)**
```
GET http://localhost:8080/api/pharmacy/suppliers/2/drugs
```
**Expected:** Returns 7 drugs

**Test 3: Add new drug to supplier**
```
POST http://localhost:8080/api/pharmacy/suppliers/1/drugs/10
```
**Explanation:** Links drug ID 10 to supplier ID 1

**Test 4: Verify the relationship**
```
GET http://localhost:8080/api/pharmacy/suppliers/1/drugs
```
**Expected:** Now shows 7 drugs (one more than before)

---

### ✅ Point 5: One-to-Many Relationship (2 Marks)

**Test 1: Get all drugs in Antibiotics category**
```
GET http://localhost:8080/api/pharmacy/categories/1/drugs
```
**Expected:** Returns 4 drugs (Amoxicillin, Penicillin, Ciprofloxacin, Azithromycin)

**Test 2: Get all drugs in Pain Relievers category**
```
GET http://localhost:8080/api/pharmacy/categories/2/drugs
```
**Expected:** Returns 4 drugs (Aspirin, Ibuprofen, Paracetamol, Naproxen)

**Test 3: Get all drugs in Vitamins category**
```
GET http://localhost:8080/api/pharmacy/categories/3/drugs
```
**Expected:** Returns 4 drugs

**Explanation:** One category has many drugs (foreign key: category_id in drugs table)

---

### ✅ Point 6: One-to-One Relationship (2 Marks)

**Test 1: Get user by province (shows user with location)**
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```
**Expected:** Returns 3 users, each with their location object

**Test 2: Create new user with location**
```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "New User",
  "email": "newuser@test.com",
  "location": {
    "addressLine": "New Address",
    "provinceName": "Kigali City",
    "provinceCode": "KC"
  }
}
```

**Explanation:** Each user has exactly one location (foreign key: location_id in users table)

---

### ✅ Point 7: existBy() Method (2 Marks)

**Test 1: Check existing email (should return true)**
```
GET http://localhost:8080/api/pharmacy/users/exists?email=shema.ken@auca.ac.rw
```
**Expected:** `true`

**Test 2: Check non-existent email (should return false)**
```
GET http://localhost:8080/api/pharmacy/users/exists?email=notfound@test.com
```
**Expected:** `false`

**Test 3: Check another existing email**
```
GET http://localhost:8080/api/pharmacy/users/exists?email=alice@example.com
```
**Expected:** `true`

**Explanation:** Spring Data JPA derived query method checks existence without loading full entity

---

### ✅ Point 8: Province Query with OR Condition (4 Marks)

**Test 1: Get users by province code (KC)**
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```
**Expected:** Returns 3 users (Shema Ken, Bob, John)

**Test 2: Get users by province name**
```
GET http://localhost:8080/api/pharmacy/users/province?name=Eastern Province
```
**Expected:** Returns 2 users (Alice, Mary)

**Test 3: Get users by code OR name (OR condition)**
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC&name=Northern Province
```
**Expected:** Returns 4 users (Shema Ken, Bob, John from KC + Grace from NP)

**Test 4: Get users from Southern Province**
```
GET http://localhost:8080/api/pharmacy/users/province?name=Southern Province
```
**Expected:** Returns 1 user (David)

**Explanation:** Uses OR condition to search by province code OR province name

---

## ADDITIONAL USEFUL TESTS

### Get All Data

```
# Get all categories
GET http://localhost:8080/api/pharmacy/categories

# Get all drugs (unpaginated)
GET http://localhost:8080/api/pharmacy/drugs/all

# Get all suppliers
GET http://localhost:8080/api/pharmacy/suppliers

# Get all locations
GET http://localhost:8080/api/pharmacy/locations
```

### Get by ID

```
# Get category by ID
GET http://localhost:8080/api/pharmacy/categories/1

# Get drug by ID
GET http://localhost:8080/api/pharmacy/drugs/1

# Get supplier by ID
GET http://localhost:8080/api/pharmacy/suppliers/1

# Get location by ID
GET http://localhost:8080/api/pharmacy/locations/1
```

### Update Operations

```
# Update category
PUT http://localhost:8080/api/pharmacy/categories/1
Content-Type: application/json

{
  "name": "Antibiotics - Updated"
}

# Update drug
PUT http://localhost:8080/api/pharmacy/drugs/1
Content-Type: application/json

{
  "name": "Amoxicillin 500mg - Updated",
  "price": 17.99
}

# Update user
PUT http://localhost:8080/api/pharmacy/users/1
Content-Type: application/json

{
  "name": "Shema KEN - Updated",
  "email": "ken.updated@auca.ac.rw"
}
```

### Delete Operations

```
# Delete category (be careful - cascades to drugs)
DELETE http://localhost:8080/api/pharmacy/categories/5

# Delete drug
DELETE http://localhost:8080/api/pharmacy/drugs/18

# Delete user
DELETE http://localhost:8080/api/pharmacy/users/8

# Delete supplier
DELETE http://localhost:8080/api/pharmacy/suppliers/5
```

---

## ASSESSMENT POINTS CHECKLIST

| Point | Topic | Status | Test Endpoint |
|-------|-------|--------|---------------|
| 1 | ERD (5 tables) | ✅ Ready | GET /categories, /drugs, /users, /locations, /suppliers |
| 2 | Saving Location | ✅ Ready | POST /users (with location) |
| 3 | Pagination & Sorting | ✅ Ready | GET /drugs?page=0&size=2&sortBy=name |
| 4 | Many-to-Many | ✅ Ready | GET /suppliers/1/drugs |
| 5 | One-to-Many | ✅ Ready | GET /categories/1/drugs |
| 6 | One-to-One | ✅ Ready | GET /users/province?code=KC |
| 7 | existBy() | ✅ Ready | GET /users/exists?email=shema.ken@auca.ac.rw |
| 8 | Province Query | ✅ Ready | GET /users/province?code=KC&name=EP |

---

## HOW TO RESTART WITH FRESH DATA

If you want to reset the database:

1. Stop the application
2. Drop the database:
   ```sql
   DROP DATABASE pharmacysupplymanagementsystem_db;
   CREATE DATABASE pharmacysupplymanagementsystem_db;
   ```
3. Restart the application - data will be seeded automatically

---

## NOTES

- Data is seeded only once (checks if categories exist)
- All relationships are properly established
- Ready for immediate testing
- No manual data entry needed!

**Base URL:** `http://localhost:8080/api/pharmacy`

**Happy Testing! 🎓**
