# LOCATION API GUIDE - Postman Testing
## Adding Rwanda Locations and Users

**Base URL:** `http://localhost:8080/api/pharmacy`

---

## 🎯 Quick Start: Add Complete Location Hierarchy

### Option 1: Create User with Full Location Hierarchy (RECOMMENDED)

This creates the entire hierarchy in ONE request using Cascade.ALL:

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Jean Uwimana",
  "email": "jean@example.com",
  "location": {
    "name": "Village A",
    "code": "KIG-VA",
    "type": "VILLAGE",
    "parent": {
      "name": "Kimihurura Cell",
      "code": "KIM-CELL",
      "type": "CELL",
      "parent": {
        "name": "Kimihurura Sector",
        "code": "KIM-SEC",
        "type": "SECTOR",
        "parent": {
          "name": "Gasabo District",
          "code": "GAS",
          "type": "DISTRICT",
          "parent": {
            "name": "Kigali City",
            "code": "KC",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

**Result:** Creates Province → District → Sector → Cell → Village → User in one go!

---

## 📍 Option 2: Create Locations Step by Step

### Step 1: Create Province

```
POST http://localhost:8080/api/pharmacy/locations
Content-Type: application/json

{
  "name": "Northern Province",
  "code": "NP",
  "type": "PROVINCE",
  "parent": null
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Northern Province",
  "code": "NP",
  "type": "PROVINCE",
  "parent": null
}
```

---

### Step 2: Create District (under Province)

```
POST http://localhost:8080/api/pharmacy/locations
Content-Type: application/json

{
  "name": "Musanze District",
  "code": "MUS",
  "type": "DISTRICT",
  "parent": {
    "id": 1
  }
}
```

**Response:**
```json
{
  "id": 2,
  "name": "Musanze District",
  "code": "MUS",
  "type": "DISTRICT",
  "parent": {
    "id": 1,
    "name": "Northern Province",
    "code": "NP",
    "type": "PROVINCE"
  }
}
```

---

### Step 3: Create Sector (under District)

```
POST http://localhost:8080/api/pharmacy/locations
Content-Type: application/json

{
  "name": "Muhoza Sector",
  "code": "MUH",
  "type": "SECTOR",
  "parent": {
    "id": 2
  }
}
```

---

### Step 4: Create Cell (under Sector)

```
POST http://localhost:8080/api/pharmacy/locations
Content-Type: application/json

{
  "name": "Nyarutovu Cell",
  "code": "NYA",
  "type": "CELL",
  "parent": {
    "id": 3
  }
}
```

---

### Step 5: Create Village (under Cell)

```
POST http://localhost:8080/api/pharmacy/locations
Content-Type: application/json

{
  "name": "Village A",
  "code": "NYA-VA",
  "type": "VILLAGE",
  "parent": {
    "id": 4
  }
}
```

---

### Step 6: Create User (linked to Village)

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Marie Mukamana",
  "email": "marie@example.com",
  "location": {
    "id": 5
  }
}
```

---

## 🏘️ Pre-Made Location Examples

### Example 1: Kigali City - Gasabo - Remera

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Shema Ken",
  "email": "ken@auca.ac.rw",
  "location": {
    "name": "Village A",
    "code": "REM-VA",
    "type": "VILLAGE",
    "parent": {
      "name": "Rukiri I",
      "code": "RUK1",
      "type": "CELL",
      "parent": {
        "name": "Remera",
        "code": "REM",
        "type": "SECTOR",
        "parent": {
          "name": "Gasabo",
          "code": "GAS",
          "type": "DISTRICT",
          "parent": {
            "name": "Kigali City",
            "code": "KC",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

---

### Example 2: Kigali City - Nyarugenge - Muhima

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Alice Uwase",
  "email": "alice@example.com",
  "location": {
    "name": "Village B",
    "code": "MUH-VB",
    "type": "VILLAGE",
    "parent": {
      "name": "Nyabugogo",
      "code": "NYB",
      "type": "CELL",
      "parent": {
        "name": "Muhima",
        "code": "MUH",
        "type": "SECTOR",
        "parent": {
          "name": "Nyarugenge",
          "code": "NYA",
          "type": "DISTRICT",
          "parent": {
            "name": "Kigali City",
            "code": "KC",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

---

### Example 3: Eastern Province - Rwamagana

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Bob Nkusi",
  "email": "bob@example.com",
  "location": {
    "name": "Village A",
    "code": "RWA-VA",
    "type": "VILLAGE",
    "parent": {
      "name": "Kajevuba",
      "code": "KAJ",
      "type": "CELL",
      "parent": {
        "name": "Gahengeri",
        "code": "GAH",
        "type": "SECTOR",
        "parent": {
          "name": "Rwamagana",
          "code": "RWA",
          "type": "DISTRICT",
          "parent": {
            "name": "Eastern Province",
            "code": "EP",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

---

### Example 4: Southern Province - Huye

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Grace Ingabire",
  "email": "grace@example.com",
  "location": {
    "name": "Village C",
    "code": "HUY-VC",
    "type": "VILLAGE",
    "parent": {
      "name": "Tumba Cell",
      "code": "TUM",
      "type": "CELL",
      "parent": {
        "name": "Tumba Sector",
        "code": "TUM-SEC",
        "type": "SECTOR",
        "parent": {
          "name": "Huye",
          "code": "HUY",
          "type": "DISTRICT",
          "parent": {
            "name": "Southern Province",
            "code": "SP",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

---

### Example 5: Western Province - Rubavu

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "David Habimana",
  "email": "david@example.com",
  "location": {
    "name": "Village D",
    "code": "RUB-VD",
    "type": "VILLAGE",
    "parent": {
      "name": "Gisenyi Cell",
      "code": "GIS",
      "type": "CELL",
      "parent": {
        "name": "Gisenyi Sector",
        "code": "GIS-SEC",
        "type": "SECTOR",
        "parent": {
          "name": "Rubavu",
          "code": "RUB",
          "type": "DISTRICT",
          "parent": {
            "name": "Western Province",
            "code": "WP",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

---

## 🔍 Query APIs

### Get All Locations
```
GET http://localhost:8080/api/pharmacy/locations
```

### Get Location by ID
```
GET http://localhost:8080/api/pharmacy/locations/1
```

### Get Users by Province Code
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```

### Get Users by Province Name
```
GET http://localhost:8080/api/pharmacy/users/province?name=Kigali City
```

### Get Users by Province (Code OR Name)
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC&name=Eastern Province
```

### Check if User Exists
```
GET http://localhost:8080/api/pharmacy/users/exists?email=shema.ken@auca.ac.rw
```

---

## 🔄 Update APIs

### Update Location
```
PUT http://localhost:8080/api/pharmacy/locations/1
Content-Type: application/json

{
  "name": "Kigali City - Updated",
  "code": "KC",
  "type": "PROVINCE"
}
```

### Update User
```
PUT http://localhost:8080/api/pharmacy/users/1
Content-Type: application/json

{
  "name": "Shema Ken - Updated",
  "email": "shema.ken.updated@auca.ac.rw"
}
```

---

## 🗑️ Delete APIs

### Delete Location
```
DELETE http://localhost:8080/api/pharmacy/locations/5
```

### Delete User
```
DELETE http://localhost:8080/api/pharmacy/users/1
```

---

## 📊 Rwanda's 5 Provinces

Use these as templates for creating locations:

### 1. Kigali City (KC)
- **Districts:** Gasabo, Kicukiro, Nyarugenge

### 2. Eastern Province (EP)
- **Districts:** Bugesera, Gatsibo, Kayonza, Kirehe, Ngoma, Nyagatare, Rwamagana

### 3. Northern Province (NP)
- **Districts:** Burera, Gakenke, Gicumbi, Musanze, Rulindo

### 4. Southern Province (SP)
- **Districts:** Gisagara, Huye, Kamonyi, Muhanga, Nyamagabe, Nyanza, Nyaruguru, Ruhango

### 5. Western Province (WP)
- **Districts:** Karongi, Ngororero, Nyabihu, Nyamasheke, Rubavu, Rusizi, Rutsiro

---

## 💡 Tips for Testing

1. **Use Option 1 (Full Hierarchy)** - Fastest way to create complete location
2. **Unique Codes** - Make sure location codes are unique
3. **Type Values** - Must be: PROVINCE, DISTRICT, SECTOR, CELL, or VILLAGE
4. **Parent Null** - Only provinces should have `parent: null`
5. **Cascade Save** - Creating a user with nested location saves everything

---

## ✅ Verification Queries

### Check Total Locations
```
GET http://localhost:8080/api/pharmacy/locations
```

### Check Users in Kigali City
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```

### Check if Location Hierarchy Works
```
GET http://localhost:8080/api/pharmacy/users/province?name=Eastern Province
```

---

## 🎯 Quick Test Sequence

```bash
# 1. Create user with full hierarchy (Kigali City)
POST /users (Example 1)

# 2. Create user in Eastern Province
POST /users (Example 3)

# 3. Create user in Southern Province
POST /users (Example 4)

# 4. Query users by province
GET /users/province?code=KC

# 5. Query users by province name
GET /users/province?name=Eastern Province

# 6. Query with OR condition
GET /users/province?code=KC&name=Southern Province
```

---

## 📝 Response Examples

### Successful User Creation:
```json
{
  "id": 1,
  "name": "Shema Ken",
  "email": "shema.ken@auca.ac.rw",
  "location": {
    "id": 5,
    "name": "Village A",
    "code": "REM-VA",
    "type": "VILLAGE",
    "parent": {
      "id": 4,
      "name": "Rukiri I",
      "code": "RUK1",
      "type": "CELL",
      "parent": {
        "id": 3,
        "name": "Remera",
        "code": "REM",
        "type": "SECTOR",
        "parent": {
          "id": 2,
          "name": "Gasabo",
          "code": "GAS",
          "type": "DISTRICT",
          "parent": {
            "id": 1,
            "name": "Kigali City",
            "code": "KC",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  },
  "provinceName": "Kigali City",
  "provinceCode": "KC",
  "fullAddress": "Kigali City > Gasabo > Remera > Rukiri I > Village A"
}
```

---

**Ready to test in Postman!** 🚀

Copy any example above directly into Postman and start testing!
