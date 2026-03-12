package com.pharmacy.controller;

import com.pharmacy.entity.*;
import com.pharmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    // POST /api/pharmacy/users
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return pharmacyService.saveUserWithLocation(user);
    }

    // GET /api/pharmacy/users/exists?email=something@example.com
    @GetMapping("/users/exists")
    public boolean checkUserExists(@RequestParam String email) {
        return pharmacyService.checkUserExistsByEmail(email);
    }

    // GET /api/pharmacy/users/province?code=CA&name=California
    @GetMapping("/users/province")
    public List<User> getUsersByProvince(@RequestParam(required = false) String code,
                                         @RequestParam(required = false) String name) {
        return pharmacyService.getUsersByProvince(code, name);
    }

    // GET /api/pharmacy/drugs?page=0&size=10&sortBy=name
    @GetMapping("/drugs")
    public Page<Drug> getDrugs(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "name") String sortBy) {
        return pharmacyService.getDrugs(page, size, sortBy);
    }

    // DELETE /api/pharmacy/users/{id}
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = pharmacyService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /api/pharmacy/users/{id}
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return pharmacyService.updateUser(id, updatedUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ========== Drug Endpoints ==========
    
    // POST /api/pharmacy/drugs
    @PostMapping("/drugs")
    public Drug createDrug(@RequestBody Drug drug) {
        return pharmacyService.saveDrug(drug);
    }

    // GET /api/pharmacy/drugs/{id}
    @GetMapping("/drugs/{id}")
    public ResponseEntity<Drug> getDrugById(@PathVariable Long id) {
        return pharmacyService.getDrugById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/pharmacy/drugs/all
    @GetMapping("/drugs/all")
    public List<Drug> getAllDrugs() {
        return pharmacyService.getAllDrugs();
    }

    // PUT /api/pharmacy/drugs/{id}
    @PutMapping("/drugs/{id}")
    public ResponseEntity<Drug> updateDrug(@PathVariable Long id, @RequestBody Drug updatedDrug) {
        return pharmacyService.updateDrug(id, updatedDrug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/pharmacy/drugs/{id}
    @DeleteMapping("/drugs/{id}")
    public ResponseEntity<String> deleteDrug(@PathVariable Long id) {
        boolean deleted = pharmacyService.deleteDrug(id);
        if (deleted) {
            return ResponseEntity.ok("Drug deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // ========== Category Endpoints ==========
    
    // POST /api/pharmacy/categories
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return pharmacyService.saveCategory(category);
    }

    // GET /api/pharmacy/categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return pharmacyService.getAllCategories();
    }

    // GET /api/pharmacy/categories/{id}
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return pharmacyService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/pharmacy/categories/{id}
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return pharmacyService.updateCategory(id, updatedCategory)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/pharmacy/categories/{id}
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        boolean deleted = pharmacyService.deleteCategory(id);
        if (deleted) {
            return ResponseEntity.ok("Category deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/pharmacy/categories/{id}/drugs
    @GetMapping("/categories/{id}/drugs")
    public ResponseEntity<List<Drug>> getDrugsByCategory(@PathVariable Long id) {
        List<Drug> drugs = pharmacyService.getDrugsByCategory(id);
        return ResponseEntity.ok(drugs);
    }

    // ========== Location Endpoints ==========
    
    // POST /api/pharmacy/locations
    @PostMapping("/locations")
    public Location createLocation(@RequestBody Location location) {
        return pharmacyService.saveLocation(location);
    }

    // GET /api/pharmacy/locations
    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return pharmacyService.getAllLocations();
    }

    // GET /api/pharmacy/locations/{id}
    @GetMapping("/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return pharmacyService.getLocationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/pharmacy/locations/{id}
    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location updatedLocation) {
        return pharmacyService.updateLocation(id, updatedLocation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/pharmacy/locations/{id}
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        boolean deleted = pharmacyService.deleteLocation(id);
        if (deleted) {
            return ResponseEntity.ok("Location deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // ========== Supplier Endpoints ==========
    
    // POST /api/pharmacy/suppliers
    @PostMapping("/suppliers")
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return pharmacyService.saveSupplier(supplier);
    }

    // GET /api/pharmacy/suppliers
    @GetMapping("/suppliers")
    public List<Supplier> getAllSuppliers() {
        return pharmacyService.getAllSuppliers();
    }

    // GET /api/pharmacy/suppliers/{id}
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        return pharmacyService.getSupplierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/pharmacy/suppliers/{id}
    @PutMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier updatedSupplier) {
        return pharmacyService.updateSupplier(id, updatedSupplier)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/pharmacy/suppliers/{id}
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        boolean deleted = pharmacyService.deleteSupplier(id);
        if (deleted) {
            return ResponseEntity.ok("Supplier deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/pharmacy/suppliers/{id}/drugs
    @GetMapping("/suppliers/{id}/drugs")
    public ResponseEntity<List<Drug>> getDrugsBySupplier(@PathVariable Long id) {
        List<Drug> drugs = pharmacyService.getDrugsBySupplier(id);
        return ResponseEntity.ok(drugs);
    }

    // POST /api/pharmacy/suppliers/{supplierId}/drugs/{drugId}
    @PostMapping("/suppliers/{supplierId}/drugs/{drugId}")
    public ResponseEntity<Supplier> addDrugToSupplier(@PathVariable Long supplierId, @PathVariable Long drugId) {
        return pharmacyService.addDrugToSupplier(supplierId, drugId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
