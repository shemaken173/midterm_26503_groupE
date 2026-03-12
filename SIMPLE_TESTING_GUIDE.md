# SIMPLE API TESTING GUIDE
## Pharmacy Supply System - Practical Examination

**Base URL:** `http://localhost:8080/api/pharmacy`

---

## TESTING SEQUENCE (Copy & Paste into Postman)

### STEP 1: Create Categories (One-to-Many Parent)

#### 1.1 Create Category - Antibiotics
```
POST http://localhost:8080/api/pharmacy/categories
Content-Type: application/json

{
  "name": "Antibiotics"
}
```

#### 1.2 Create Category - Pain Relievers
```
POST http://localhost:8080/api/pharmacy/categories
Content-Type: application/json

{
  "name": "Pain Relievers"
}
```

#### 1.3 Create Category - Vitamins
```
POST http://localhost:8080/api/pharmacy/categories
Content-Type: application/json

{
  "name": "Vitamins"
}
```

#### 1.4 Get All Categories (Verify)
```
GET http://localhost:8080/api/pharmacy/categories
```

---

### STEP 2: Create Drugs (One-to-Many Child)

#### 2.1 Create Drug - Amoxicillin
```
POST http://localhost:8080/api/pharmacy/drugs
Content-Type: application/json

{
  "name": "Amoxicillin 500mg",
  "price": 15.99,
  "category": {
    "id": 1
  }
}
```

#### 2.2 Create Drug - Aspirin
```
POST http://localhost:8080/api/pharmacy/drugs
Content-Type: application/json

{
  "name": "Aspirin 500mg",
  "price": 9.99,
  "category": {
    "id": 2
  }
}
```

#### 2.3 Create Drug - Ibuprofen
```
POST http://localhost:8080/api/pharmacy/drugs
Content-Type: application/json

{
  "name": "Ibuprofen 400mg",
  "price": 11.99,
  "category": {
    "id": 2
  }
}
```

#### 2.4 Create Drug - Vitamin C
```
POST http://localhost:8080/api/pharmacy/drugs
Content-Type: application/json

{
  "name": "Vitamin C 1000mg",
  "price": 12.50,
  "category": {
    "id": 3
  }
}
```

#### 2.5 Create Drug - Penicillin
```
POST http://localhost:8080/api/pharmacy/drugs
Content-Type: application/json

{
  "name": "Penicillin 250mg",
  "price": 18.50,
  "category": {
    "id": 1
  }
}
```

---

### STEP 3: Test Pagination & Sorting (Assessment Point 3)

#### 3.1 Get Drugs - Page 0 (First 2 drugs, sorted by name)
```
GET http://localhost:8080/api/pharmacy/drugs?page=0&size=2&sortBy=name
```
**Expected:** Shows 2 drugs, totalElements=5, totalPages=3

#### 3.2 Get Drugs - Page 1 (Next 2 drugs)
```
GET http://localhost:8080/api/pharmacy/drugs?page=1&size=2&sortBy=name
```
**Expected:** Shows next 2 drugs

#### 3.3 Get Drugs - Sorted by Price
```
GET http://localhost:8080/api/pharmacy/drugs?page=0&size=10&sortBy=price
```
**Expected:** Drugs sorted from cheapest to most expensive

---

### STEP 4: Test One-to-Many Relationship (Assessment Point 5)

#### 4.1 Get All Drugs in Category 1 (Antibiotics)
```
GET http://localhost:8080/api/pharmacy/categories/1/drugs
```
**Expected:** Returns 2 drugs (Amoxicillin, Penicillin)

#### 4.2 Get All Drugs in Category 2 (Pain Relievers)
```
GET http://localhost:8080/api/pharmacy/categories/2/drugs
```
**Expected:** Returns 2 drugs (Aspirin, Ibuprofen)

---

### STEP 5: Create Users with Locations (One-to-One + Cascade)

#### 5.1 Create User - Shema Ken (Kigali City)
```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Shema Ken",
  "email": "shema.ken@auca.ac.rw",
  "location": {
    "addressLine": "AUCA Campus, Kigali",
    "provinceName": "Kigali City",
    "provinceCode": "KC"
  }
}
```
**Assessment Point 2:** Location saved automatically (Cascade)
**Assessment Point 6:** One-to-One relationship

#### 5.2 Create User - Alice (Eastern Province)
```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Alice Mukamana",
  "email": "alice@example.com",
  "location": {
    "addressLine": "Rwamagana District",
    "provinceName": "Eastern Province",
    "provinceCode": "EP"
  }
}
```

#### 5.3 Create User - Bob (Kigali City)
```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Bob Nkusi",
  "email": "bob@example.com",
  "location": {
    "addressLine": "Nyarugenge District",
    "provinceName": "Kigali City",
    "provinceCode": "KC"
  }
}
```

#### 5.4 Create User - Grace (Northern Province)
```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Grace Uwase",
  "email": "grace@example.com",
  "location": {
    "addressLine": "Musanze District",
    "provinceName": "Northern Province",
    "provinceCode": "NP"
  }
}
```

---

### STEP 6: Test existBy() Method (Assessment Point 7)

#### 6.1 Check Existing Email (Should return true)
```
GET http://localhost:8080/api/pharmacy/users/exists?email=shema.ken@auca.ac.rw
```
**Expected:** `true`

#### 6.2 Check Non-Existent Email (Should return false)
```
GET http://localhost:8080/api/pharmacy/users/exists?email=notfound@test.com
```
**Expected:** `false`

---

### STEP 7: Test Province Query - OR Condition (Assessment Point 8)

#### 7.1 Get Users by Province Code (KC)
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```
**Expected:** Returns 2 users (Shema Ken, Bob)

#### 7.2 Get Users by Province Name
```
GET http://localhost:8080/api/pharmacy/users/province?name=Eastern Province
```
**Expected:** Returns 1 user (Alice)

#### 7.3 Get Users by Code OR Name
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC&name=Northern Province
```
**Expected:** Returns 3 users (Shema Ken, Bob, Grace) - OR condition

---

### STEP 8: Create Suppliers (Many-to-Many Setup)

#### 8.1 Create Supplier - MedSupply
```
POST http://localhost:8080/api/pharmacy/suppliers
Content-Type: application/json

{
  "name": "MedSupply Rwanda Ltd",
  "contactEmail": "contact@medsupply.rw"
}
```

#### 8.2 Create Supplier - PharmaCorp
```
POST http://localhost:8080/api/pharmacy/suppliers
Content-Type: application/json

{
  "name": "PharmaCorp International",
  "contactEmail": "info@pharmacorp.com"
}
```

#### 8.3 Create Supplier - HealthPlus
```
POST http://localhost:8080/api/pharmacy/suppliers
Content-Type: application/json

{
  "name": "HealthPlus Distributors",
  "contactEmail": "sales@healthplus.rw"
}
```

---

### STEP 9: Link Suppliers to Drugs (Many-to-Many - Assessment Point 4)

#### 9.1 Link Supplier 1 to Drug 1
```
POST http://localhost:8080/api/pharmacy/suppliers/1/drugs/1
```

#### 9.2 Link Supplier 1 to Drug 2
```
POST http://localhost:8080/api/pharmacy/suppliers/1/drugs/2
```

#### 9.3 Link Supplier 1 to Drug 3
```
POST http://localhost:8080/api/pharmacy/suppliers/1/drugs/3
```

#### 9.4 Link Supplier 2 to Drug 1 (Same drug, different supplier)
```
POST http://localhost:8080/api/pharmacy/suppliers/2/drugs/1
```

#### 9.5 Link Supplier 2 to Drug 4
```
POST http://localhost:8080/api/pharmacy/suppliers/2/drugs/4
```

#### 9.6 Link Supplier 3 to Drug 2
```
POST http://localhost:8080/api/pharmacy/suppliers/3/drugs/2
```

#### 9.7 Link Supplier 3 to Drug 5
```
POST http://localhost:8080/api/pharmacy/suppliers/3/drugs/5
```

---

### STEP 10: Verify Many-to-Many Relationship (Assessment Point 4)

#### 10.1 Get All Drugs from Supplier 1
```
GET http://localhost:8080/api/pharmacy/suppliers/1/drugs
```
**Expected:** Returns 3 drugs (Amoxicillin, Aspirin, Ibuprofen)

#### 10.2 Get All Drugs from Supplier 2
```
GET http://localhost:8080/api/pharmacy/suppliers/2/drugs
```
**Expected:** Returns 2 drugs (Amoxicillin, Vitamin C)

#### 10.3 Get All Drugs from Supplier 3
```
GET http://localhost:8080/api/pharmacy/suppliers/3/drugs
```
**Expected:** Returns 2 drugs (Aspirin, Penicillin)

---

## ASSESSMENT POINTS SUMMARY

| Point | Topic | Test Steps | Expected Result |
|-------|-------|------------|-----------------|
| 1 | ERD (5 tables) | Check database | 5 entities + join table |
| 2 | Saving Location | Step 5.1-5.4 | Location saved with user |
| 3 | Pagination & Sorting | Step 3.1-3.3 | Paginated results with sorting |
| 4 | Many-to-Many | Step 9 & 10 | Suppliers linked to drugs |
| 5 | One-to-Many | Step 4.1-4.2 | Categories contain drugs |
| 6 | One-to-One | Step 5.1-5.4 | User has one location |
| 7 | existBy() | Step 6.1-6.2 | Returns true/false |
| 8 | Province Query | Step 7.1-7.3 | OR condition works |

---

## QUICK VERIFICATION QUERIES

```
# Get all categories
GET http://localhost:8080/api/pharmacy/categories

# Get all drugs
GET http://localhost:8080/api/pharmacy/drugs/all

# Get all suppliers
GET http://localhost:8080/api/pharmacy/suppliers

# Get all locations
GET http://localhost:8080/api/pharmacy/locations
```

---

## POSTMAN SETUP

1. Open Postman
2. Create new request
3. Copy endpoint URL
4. Select method (GET/POST/PUT/DELETE)
5. For POST/PUT: Go to "Body" → "raw" → "JSON"
6. Paste JSON data
7. Click "Send"

---

## EXPECTED DATABASE STATE AFTER ALL TESTS

- **Categories:** 3 (Antibiotics, Pain Relievers, Vitamins)
- **Drugs:** 5 (Amoxicillin, Aspirin, Ibuprofen, Vitamin C, Penicillin)
- **Users:** 4 (Shema Ken, Alice, Bob, Grace)
- **Locations:** 4 (automatically created with users)
- **Suppliers:** 3 (MedSupply, PharmaCorp, HealthPlus)
- **Supplier-Drug Links:** 7 relationships in join table

---

## TROUBLESHOOTING

**Error: 404 Not Found**
- Check if Spring Boot is running
- Verify URL is correct

**Error: 500 Internal Server Error**
- Check category_id exists before creating drug
- Check drug_id and supplier_id exist before linking

**Error: Cannot connect**
- Ensure application is running on port 8080
- Check database connection in application.properties

---

**Total Testing Time:** 10-15 minutes  
**Total Requests:** 40+  
**All Assessment Points Covered:** ✅
