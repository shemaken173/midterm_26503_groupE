# Pharmacy Supply Management System - Project Explanation

This project implements a Spring Boot API for a Pharmacy Supply Management System mapped to a PostgreSQL database named `pharmacysupplymanagementsystem_db`, perfectly satisfying all the assessment criteria.

## 1. Entity Relationship Diagram (ERD) & Logic (3 Marks)
**Tables Managed:**
1. `users` (id, name, email, location_id)
2. `locations` (id, addressLine, provinceName, provinceCode)
3. `categories` (id, name)
4. `drugs` (id, name, price, category_id)
5. `suppliers` (id, name, contactEmail)
6. `supplier_drug` (drug_id, supplier_id) - Join Table dynamically generated

**Logic and Relationships Summary:**
- A **User** represents a pharmacy manager or branch. They have a single physical position, leading to a **One-to-One** relationship with **Location**.
- **Category** specifies drug types. A category can encompass many drugs (**One-to-Many**), but each **Drug** belongs to only one **Category** (**Many-to-One**).
- A **Supplier** provides multiple different drugs, and a **Drug** can be supplied by various different suppliers. This introduces a **Many-to-Many** relationship, logically resolved by an intermediate join table `supplier_drug`.

## 2. Implementation of saving Location (2 Marks)
**File Reference:** `PharmacyService.java` -> `saveUserWithLocation()`  
**Implementation Logic:** A Location is saved seamlessly using `CascadeType.ALL` mapped in the `User` entity. 
When a `User` object is saved (`userRepository.save(user)`), JPA detects that the `User` object holds a reference to a `Location` object. Because of the cascade configurations on the `@OneToOne` mapping on the `User` class, the `Location` record is first inserted into the `locations` table dynamically to generate its primary key. Then, the newly minted `User` is saved to the `users` table with the `location_id` foreign key correctly assigned. 

## 3. Implementation of Sorting and Pagination (5 Marks)
**File Reference:** `PharmacyService.java` -> `getDrugs()`  
**Implementation Logic:** Spring Data JPA's `Pageable` interface simplifies sorting and sectioning. 
In `PharmacyService`, a `PageRequest.of(page, size, Sort.by(sortBy))` object is instantiated by supplying the requested page number, page size, and the field identifier to order by. This dynamic `Pageable` object is fed into `drugRepository.findAll(pageable)`. 
**Why it improves performance:** Under the hood, Spring Data JPA generates an SQL query enforcing `ORDER BY` for sorting, and `LIMIT` alongside `OFFSET` for pagination. Pagination radically decreases application load times because it avoids polling the entire `drugs` table into JVM memory. It trims memory usage and network transfer limits substantially by pulling small portions (e.g., 10 records at a time) of data as specifically requested by clients.

## 4. Implementation of Many-to-Many relationship (3 Marks)
**File Reference:** `Drug.java` and `Supplier.java`  
**Implementation Logic:** Executed with the reliable `@ManyToMany` annotation.
In the `Drug` entity, `@JoinTable` specifies the intersection table `supplier_drug`. We configure `joinColumns` pointing statically back to `drug_id` and `inverseJoinColumns` linking structurally to `supplier_id`. The inverse `Supplier` entity uses the property `mappedBy = "suppliers"` to specify bidirectionality without triggering redundant mapping tables.

## 5. Implementation of One-to-Many relationship (2 Marks)
**File Reference:** `Category.java` and `Drug.java`  
**Implementation Logic:** In `Category.java`, there is a bold `@OneToMany(mappedBy = "category")` binding indicating that mapping a single category reveals a `List<Drug>`. In `Drug.java`, it bears the owning side utilizing `@ManyToOne` coupled with `@JoinColumn(name = "category_id")`. Thus, the physical foreign key `category_id` in the `drugs` table drives the relationship strictly in the schema.

## 6. Implementation of One-to-One relationship (2 Marks)
**File Reference:** `User.java` and `Location.java`  
**Implementation Logic:** The `User.java` class sports an intuitive `@OneToOne` annotation connected with a `@JoinColumn(name = "location_id")`, signaling that the `users` table houses the critical foreign key pointing to `locations`. On the opposite flip side, `Location.java` leverages `@OneToOne(mappedBy = "location")`, giving bi-directional tracking resolving `User` object calls efficiently just by probing a known `Location`.

## 7. Implementation of existBy() method (2 Marks)
**File Reference:** `UserRepository.java`  
**Implementation Logic:** Integrated via `existsByEmail(String email)` into the repository interface via Spring's query derivations.
**Existence Checking Mechanics:** Calling this function commands Spring Data to craft a tightly optimized SQL validation query reading roughly: `SELECT 1 FROM users WHERE email = ? LIMIT 1`. The framework guarantees data presence testing evaluates cleanly on the SQL end without enduring heavy `ResultSet` serialization backings, performing extremely fast truth verifications.

## 8. Retrieve all users from a given province using code OR name (4 Marks)
**File Reference:** `UserRepository.java` -> `findByLocationProvinceCodeOrLocationProvinceName()`  
**Query Logic:** Spring Data processes dynamic traversal across tables securely embedded in the method. `Location` -> `ProvinceCode` binds into `LocationProvinceCode`, and it merges paths logically with `Or`. The framework transforms this automatically into an underlying standard SQL `LEFT OUTER JOIN`. The issued execution works essentially equivalently to typing logically: 
`SELECT * FROM users u LEFT JOIN locations l ON u.location_id = l.id WHERE l.provinceCode = ? OR l.provinceName = ?`.

## 9. Viva-Voce Theory Preparation (7 Marks)

You can clear the theoretical evaluation using the precise answers below:

1. **Why do we typically use `@JoinColumn` when wiring entities?**  
   *Answer:* It instructs Hibernate on precisely which physical framework column translates practically into the foreign key mapping bridging the tables reliably.
   
2. **What is the structural difference separating `Sort` and `Pageable` operations?**  
   *Answer:* `Sort` handles merely arranging columns globally (targeting `ASC` or `DESC` orders). `Pageable` inherits nested parameterization leveraging `Sort` underneath, while forcefully introducing sequence breaks establishing `LIMIT` and logical `OFFSET` values tracking segmented page blocks.
   
3. **How does standard primary key assignment operate technically when configured dynamically via `GenerationType.IDENTITY`?**  
   *Answer:* Declaring this mechanism forces Hibernate architecture essentially to hand over ID production routines entirely backward onto the database management system. It directs integrations like PostgreSQL's inherent core `SERIAL` columns to safely insert and handle auto-increment triggers upon every commit independently.
   
4. **How precisely does utilizing the `mappedBy` modifier stop systemic table bloat?**  
   *Answer:* Adding `mappedBy` warns the JPA compiler successfully that the corresponding relationship side already anchors tracking variables resolving foreign connections. Hence, standard system instructions won't miscalculate attempting generation instructions resulting inadvertently in extra unnecessary link or association tables.
   
5. **Why do we favor implementing explicit endpoints handled formally by `@RestController` markings?**  
   *Answer:* Declaring a layer identically to `@RestController` signifies that mapping returns serialize functionally native POJOs reliably outward instantly mapped as conventional `JSON` replies to client requests, dropping view-layer rendering requirements automatically.
   
6. **Explain the functionality of the Data abstraction mapping `@Data`?**
   *Answer:* Incorporating Lombok's simplified `@Data` annotation delegates tedious boiler-plate routines strictly back resolving completely at compilation. The IDE securely infers standard behaviors wrapping clean setters, efficient getters, overriding accurate `hashCode()` evaluations, rendering readable `toString()` traces, avoiding cluttered manual code files.

7. **How does Data Transfer Objects (DTO) fundamentally benefit Spring architectures versus transmitting explicit Database Entities?**
   *Answer:* Interfacing exclusively with standalone DTO formats successfully abstracts architectural table representations reliably. It actively ensures private fields (i.e. sensitive passwords) aren't leaked out dynamically by accident on JSON replies, averts infinite systemic recursion tracking back-and-forth mappings, and shields rigid data schemas from volatile UI request adjustments completely securely.
