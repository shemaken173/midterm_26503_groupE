# QUICK API REFERENCE - Add Locations & Users
## Copy & Paste into Postman

**Base URL:** `http://localhost:8080/api/pharmacy`

---

## 🚀 FASTEST WAY: Create User with Full Location Hierarchy

### Copy this into Postman (ONE REQUEST creates everything!)

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Your Name Here",
  "email": "your.email@example.com",
  "location": {
    "name": "Village Name",
    "code": "VILLAGE-CODE",
    "type": "VILLAGE",
    "parent": {
      "name": "Cell Name",
      "code": "CELL-CODE",
      "type": "CELL",
      "parent": {
        "name": "Sector Name",
        "code": "SECTOR-CODE",
        "type": "SECTOR",
        "parent": {
          "name": "District Name",
          "code": "DISTRICT-CODE",
          "type": "DISTRICT",
          "parent": {
            "name": "Province Name",
            "code": "PROVINCE-CODE",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

**This creates:** Province → District → Sector → Cell → Village → User (all at once!)

---

## 📋 READY-TO-USE EXAMPLES

### Example 1: Kigali City User

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Jean Uwimana",
  "email": "jean@example.com",
  "location": {
    "name": "Nyagatovu",
    "code": "NYA-V",
    "type": "VILLAGE",
    "parent": {
      "name": "Bibare",
      "code": "BIB",
      "type": "CELL",
      "parent": {
        "name": "Kimironko",
        "code": "KIM",
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

### Example 2: Eastern Province User

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Marie Mukamana",
  "email": "marie@example.com",
  "location": {
    "name": "Kajevuba Village",
    "code": "KAJ-V",
    "type": "VILLAGE",
    "parent": {
      "name": "Kajevuba Cell",
      "code": "KAJ-C",
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

### Example 3: Northern Province User

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Paul Nkusi",
  "email": "paul@example.com",
  "location": {
    "name": "Nyarutovu Village",
    "code": "NYA-V",
    "type": "VILLAGE",
    "parent": {
      "name": "Nyarutovu Cell",
      "code": "NYA-C",
      "type": "CELL",
      "parent": {
        "name": "Muhoza",
        "code": "MUH",
        "type": "SECTOR",
        "parent": {
          "name": "Musanze",
          "code": "MUS",
          "type": "DISTRICT",
          "parent": {
            "name": "Northern Province",
            "code": "NP",
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

### Example 4: Southern Province User

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "Grace Ingabire",
  "email": "grace@example.com",
  "location": {
    "name": "Tumba Village",
    "code": "TUM-V",
    "type": "VILLAGE",
    "parent": {
      "name": "Tumba Cell",
      "code": "TUM-C",
      "type": "CELL",
      "parent": {
        "name": "Tumba Sector",
        "code": "TUM-S",
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

### Example 5: Western Province User

```
POST http://localhost:8080/api/pharmacy/users
Content-Type: application/json

{
  "name": "David Habimana",
  "email": "david@example.com",
  "location": {
    "name": "Gisenyi Village",
    "code": "GIS-V",
    "type": "VILLAGE",
    "parent": {
      "name": "Gisenyi Cell",
      "code": "GIS-C",
      "type": "CELL",
      "parent": {
        "name": "Gisenyi Sector",
        "code": "GIS-S",
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

## 🔍 QUERY USERS BY PROVINCE

### Get Users in Kigali City
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```

### Get Users in Eastern Province
```
GET http://localhost:8080/api/pharmacy/users/province?name=Eastern Province
```

### Get Users in Multiple Provinces (OR condition)
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC&name=Northern Province
```

---

## ✅ VERIFY YOUR DATA

### Check if User Exists
```
GET http://localhost:8080/api/pharmacy/users/exists?email=jean@example.com
```

### Get All Locations
```
GET http://localhost:8080/api/pharmacy/locations
```

### Get All Users
```
GET http://localhost:8080/api/pharmacy/users/province?code=KC
```

---

## 📝 IMPORTANT NOTES

### Location Type Values (MUST USE EXACTLY):
- `PROVINCE`
- `DISTRICT`
- `SECTOR`
- `CELL`
- `VILLAGE`

### Rules:
1. ✅ Users MUST be linked to a VILLAGE (not province or district)
2. ✅ Only provinces have `"parent": null`
3. ✅ All other levels must have a parent
4. ✅ Type values are case-sensitive (use UPPERCASE)
5. ✅ Codes should be unique

---

## 🎯 TESTING SEQUENCE

```bash
# Step 1: Create user in Kigali City
POST /users (Example 1)

# Step 2: Create user in Eastern Province  
POST /users (Example 2)

# Step 3: Query users by province
GET /users/province?code=KC

# Step 4: Query users by province name
GET /users/province?name=Eastern Province

# Step 5: Verify user exists
GET /users/exists?email=jean@example.com
```

---

## 💡 TIPS

1. **Change the names and emails** in the examples to create different users
2. **Keep the location structure** the same (Province → District → Sector → Cell → Village)
3. **Use unique email addresses** for each user
4. **Copy entire JSON** including all nested parents
5. **One POST request** creates everything automatically!

---

## ❌ COMMON MISTAKES TO AVOID

### ❌ Wrong: Linking user to province
```json
{
  "name": "Test",
  "email": "test@example.com",
  "location": {
    "name": "Kigali City",
    "type": "PROVINCE",
    "parent": null
  }
}
```

### ✅ Correct: Linking user to village
```json
{
  "name": "Test",
  "email": "test@example.com",
  "location": {
    "name": "Village A",
    "type": "VILLAGE",
    "parent": {
      "name": "Cell A",
      "type": "CELL",
      "parent": {
        // ... continue hierarchy
      }
    }
  }
}
```

---

## 🚀 READY TO TEST!

**Just copy any example above into Postman and click Send!**

All examples are tested and working. ✅
