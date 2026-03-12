# Location Hierarchy Design – Theory and Implementation
## Hierarchical Data Model Using Self-Referencing Table

**Student:** Shema Ken  
**University:** Adventist University of Central Africa (AUCA)  
**Course:** Web Technology and Internet  
**Practical Examination**

---

## 1. Introduction to Location Hierarchy

A **location hierarchy** is a way of organizing geographical areas in different administrative levels such as Province, District, Sector, Cell, and Village. Instead of creating a separate table for each level, a better design is to store all locations in **one single table** called `Location`.

This design is known as a **hierarchical data model** using a **self-referencing table**.

---

## 2. Single Location Table Design

### 2.1 Concept

All administrative locations are stored in **one table**. Each record represents a location at any level.

### 2.2 Table Structure

```sql
CREATE TABLE locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50),
    type VARCHAR(20) NOT NULL,  -- PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
    parent_id BIGINT REFERENCES locations(id)
);
```

### 2.3 Attributes Explained

| Attribute | Description | Example |
|-----------|-------------|---------|
| `id` | Unique identifier of the location | 1, 2, 3... |
| `name` | Name of the location | "Kigali City", "Gasabo", "Kimironko" |
| `code` | Optional short code for identification | "KC", "GAS", "KIM" |
| `type` | Level of the location (Enum) | PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE |
| `parent_id` | Reference to parent location | NULL for provinces, otherwise parent's id |

### 2.4 Implementation (Location.java)

```java
@Entity
@Table(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    @Enumerated(EnumType.STRING)
    private LocationType type;

    // Self-referencing relationship
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Location parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Location> children;
}
```

---

## 3. Hierarchical Structure (Parent–Child Relationship)

### 3.1 Concept

The hierarchy is created using a **self-referencing relationship**, meaning a location can reference another location in the same table as its parent.

### 3.2 Tree Structure

```
Province (parent_id = NULL)
  └─ District (parent_id = Province.id)
      └─ Sector (parent_id = District.id)
          └─ Cell (parent_id = Sector.id)
              └─ Village (parent_id = Cell.id)
```

### 3.3 Real Example: Rwanda

```
Kigali City (Province, parent_id = NULL)
  └─ Gasabo (District, parent_id = 1)
      └─ Kimironko (Sector, parent_id = 2)
          └─ Bibare (Cell, parent_id = 3)
              └─ Nyagatovu (Village, parent_id = 4)
```

### 3.4 Database Records

| id | name | code | type | parent_id |
|----|------|------|------|-----------|
| 1 | Kigali City | KC | PROVINCE | NULL |
| 2 | Gasabo | GAS | DISTRICT | 1 |
| 3 | Kimironko | KIM | SECTOR | 2 |
| 4 | Bibare | BIB | CELL | 3 |
| 5 | Nyagatovu | NYA | VILLAGE | 4 |

### 3.5 Relationship Rules

- Each location has **one parent** (except provinces)
- Each location can have **many children**
- Provinces have `parent_id = NULL` (root nodes)
- Villages are leaf nodes (no children)

---

## 4. Location Type Using Enumeration

### 4.1 Concept

The system uses an **enumeration (Enum)** to define the level of each location.

### 4.2 Implementation (LocationType.java)

```java
public enum LocationType {
    PROVINCE,
    DISTRICT,
    SECTOR,
    CELL,
    VILLAGE
}
```

### 4.3 Benefits

1. **Type Safety** - Only valid location levels can exist
2. **Data Consistency** - Prevents invalid types like "Town" or "Region"
3. **Code Clarity** - Easy to understand location levels
4. **Database Integrity** - Stored as VARCHAR in database

### 4.4 Usage in Entity

```java
@Enumerated(EnumType.STRING)
private LocationType type;
```

This stores the enum as a string in the database: "PROVINCE", "DISTRICT", etc.

---

## 5. Navigating the Hierarchy

### 5.1 Moving Up the Hierarchy

Because each location stores its parent reference, it is possible to **move up** the hierarchy.

**Example:** Starting from a village, determine its province:

```java
public Location getProvince() {
    Location current = this;
    while (current.parent != null) {
        current = current.parent;
    }
    return current;  // Returns the province (root)
}
```

**Traversal Path:**
```
Village → Cell → Sector → District → Province
```

### 5.2 Moving Down the Hierarchy

To get all children of a location:

```java
@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
private List<Location> children;
```

**Example:** Get all districts in a province:
```java
Province kigali = locationRepository.findById(1L).get();
List<Location> districts = kigali.getChildren();
```

---

## 6. Full Location Path

### 6.1 Concept

The system can build a **full path** showing the complete hierarchy of a location.

### 6.2 Implementation

```java
public String getFullPath() {
    if (parent == null) {
        return name;
    }
    return parent.getFullPath() + " > " + name;
}
```

### 6.3 Example Output

```
Input: Village "Nyagatovu" (id=5)
Output: "Kigali City > Gasabo > Kimironko > Bibare > Nyagatovu"
```

### 6.4 How It Works

1. Start from the village
2. Recursively call `getFullPath()` on parent
3. Concatenate names with " > " separator
4. Stop when reaching province (parent = null)

---

## 7. Advantages of This Design

### 7.1 Flexibility
- New location levels can be added without changing database structure
- Can represent any geographical hierarchy
- Easy to extend (e.g., add "Region" level)

### 7.2 Reduced Redundancy
- Only **one table** needed instead of 5 separate tables
- No duplicate data
- Easier to maintain

### 7.3 Scalability
- Can handle unlimited locations
- Efficient for large datasets
- Tree structure grows naturally

### 7.4 Efficient Organization
- Locations easily grouped by parent
- Simple queries to get all children
- Natural representation of real-world hierarchy

### 7.5 Data Integrity
- Foreign key constraints ensure valid parent references
- Cascade operations maintain consistency
- Enum ensures valid location types

---

## 8. Database Pattern: Adjacency List Model

### 8.1 Concept

This design follows the **Adjacency List Model**, a common database pattern for representing hierarchical data.

### 8.2 Characteristics

- Each node stores a reference to its parent
- Simple to implement
- Easy to query immediate parent/children
- Commonly used for:
  - Geographical hierarchies
  - Organizational structures
  - Category systems
  - File systems

### 8.3 Alternative Patterns (Not Used Here)

1. **Nested Set Model** - Stores left/right boundaries
2. **Path Enumeration** - Stores full path as string
3. **Closure Table** - Separate table for all relationships

**Why Adjacency List?**
- Simplest to understand and implement
- Sufficient for our use case
- Easy to maintain and query

---

## 9. Tree Data Structure Analogy

### 9.1 Computer Science Concept

In computer science terms, this structure behaves like a **Tree Data Structure**:

```
         [Kigali City]  ← Root (Province)
              |
         [Gasabo]       ← Internal Node (District)
              |
         [Kimironko]    ← Internal Node (Sector)
              |
         [Bibare]       ← Internal Node (Cell)
              |
         [Nyagatovu]    ← Leaf Node (Village)
```

### 9.2 Tree Terminology

| Term | Location Equivalent | Example |
|------|---------------------|---------|
| Root | Province | Kigali City |
| Internal Node | District, Sector, Cell | Gasabo, Kimironko |
| Leaf Node | Village | Nyagatovu |
| Parent | Higher level location | Gasabo is parent of Kimironko |
| Child | Lower level location | Kimironko is child of Gasabo |
| Depth | Number of levels | Village is at depth 4 |
| Height | Max depth from node | Province has height 4 |

### 9.3 Tree Operations

**Traversal (Moving Up):**
```java
Location village = locationRepository.findById(5L).get();
Location province = village.getProvince();
```

**Breadth-First (Get All Children):**
```java
Location province = locationRepository.findById(1L).get();
List<Location> allDescendants = getAllDescendants(province);
```

---

## 10. Implementation in User Entity

### 10.1 User-Location Relationship

Users are linked to **VILLAGES** (the most specific location):

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // User linked to VILLAGE
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
}
```

### 10.2 Helper Methods

```java
// Get province from user's village
public String getProvinceName() {
    return location != null ? location.getProvince().getName() : null;
}

// Get province code
public String getProvinceCode() {
    return location != null ? location.getProvince().getCode() : null;
}

// Get full address
public String getFullAddress() {
    return location != null ? location.getFullPath() : null;
}
```

### 10.3 Usage Example

```java
User shemaKen = userRepository.findById(1L).get();

System.out.println(shemaKen.getName());           // "Shema Ken"
System.out.println(shemaKen.getProvinceName());   // "Kigali City"
System.out.println(shemaKen.getProvinceCode());   // "KC"
System.out.println(shemaKen.getFullAddress());    
// "Kigali City > Gasabo > Kimironko > Bibare > Nyagatovu"
```

---

## 11. Querying Users by Province

### 11.1 The Challenge

Users are linked to villages, but we need to query by province (4 levels up).

### 11.2 Solution: Multi-Level JOIN

```java
@Query("SELECT u FROM User u " +
       "JOIN u.location v " +        // v = Village
       "JOIN v.parent c " +           // c = Cell
       "JOIN c.parent s " +           // s = Sector
       "JOIN s.parent d " +           // d = District
       "JOIN d.parent p " +           // p = Province
       "WHERE (p.code = :provinceCode OR p.name = :provinceName)")
List<User> findByProvinceCodeOrName(
    @Param("provinceCode") String provinceCode,
    @Param("provinceName") String provinceName
);
```

### 11.3 How It Works

1. Start from User
2. Join to location (Village)
3. Join to village's parent (Cell)
4. Join to cell's parent (Sector)
5. Join to sector's parent (District)
6. Join to district's parent (Province)
7. Filter by province code OR name

### 11.4 Generated SQL

```sql
SELECT u.* 
FROM users u
JOIN locations v ON u.location_id = v.id          -- Village
JOIN locations c ON v.parent_id = c.id            -- Cell
JOIN locations s ON c.parent_id = s.id            -- Sector
JOIN locations d ON s.parent_id = d.id            -- District
JOIN locations p ON d.parent_id = p.id            -- Province
WHERE (p.code = 'KC' OR p.name = 'Kigali City');
```

---

## 12. Cascade Operations

### 12.1 Concept

When saving a user with a nested location hierarchy, **all locations are saved automatically**.

### 12.2 Implementation

```java
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "parent_id")
private Location parent;
```

### 12.3 Example

**Single POST Request:**
```json
{
  "name": "Shema Ken",
  "email": "shema.ken@auca.ac.rw",
  "location": {
    "name": "Village A",
    "type": "VILLAGE",
    "parent": {
      "name": "Cell A",
      "type": "CELL",
      "parent": {
        "name": "Sector A",
        "type": "SECTOR",
        "parent": {
          "name": "District A",
          "type": "DISTRICT",
          "parent": {
            "name": "Province A",
            "type": "PROVINCE",
            "parent": null
          }
        }
      }
    }
  }
}
```

**Result:** Creates 5 location records + 1 user record in one transaction!

---

## 13. Assessment Points Coverage

| Point | Topic | Implementation | Status |
|-------|-------|----------------|--------|
| 1 | ERD (5 tables) | ✅ Self-referencing Location table | Complete |
| 2 | Saving Location | ✅ Cascade.ALL saves entire hierarchy | Complete |
| 3 | Pagination & Sorting | ✅ Implemented for drugs | Complete |
| 4 | Many-to-Many | ✅ Supplier-Drug relationship | Complete |
| 5 | One-to-Many | ✅ Category-Drug relationship | Complete |
| 6 | One-to-One | ✅ User-Village relationship | Complete |
| 7 | existBy() | ✅ existsByEmail method | Complete |
| 8 | Province Query | ✅ 4-level JOIN traversal | Complete |

---

## 14. Key Points for Exam Explanation

### 14.1 Why Single Table?
- **Flexibility:** Can add new levels without schema changes
- **Simplicity:** One table instead of five
- **Scalability:** Handles unlimited locations

### 14.2 Why Self-Referencing?
- **Natural Hierarchy:** Mirrors real-world structure
- **Easy Navigation:** Can traverse up/down the tree
- **Data Integrity:** Foreign key ensures valid parents

### 14.3 Why Enum for Type?
- **Type Safety:** Only valid levels allowed
- **Consistency:** Prevents typos and invalid data
- **Clarity:** Clear definition of location levels

### 14.4 Why Cascade?
- **Convenience:** Save entire hierarchy in one operation
- **Atomicity:** All or nothing (transaction safety)
- **Simplicity:** No manual saving of each level

### 14.5 Why Link Users to Villages?
- **Specificity:** Village is the most detailed level
- **Automatic Resolution:** Province derived from village
- **Flexibility:** Can query at any level

---

## 15. Summary

This implementation demonstrates:

1. ✅ **Hierarchical Data Model** using Adjacency List pattern
2. ✅ **Self-Referencing Table** for flexible location storage
3. ✅ **Tree Data Structure** for geographical hierarchy
4. ✅ **Enum Type** for location level validation
5. ✅ **Cascade Operations** for automatic hierarchy saving
6. ✅ **Multi-Level JOINs** for province-based queries
7. ✅ **Helper Methods** for easy navigation
8. ✅ **Full Path Generation** for complete address display

**This design is production-ready and follows industry best practices!** 🎓

---

**Good luck with your exam!** 🚀
