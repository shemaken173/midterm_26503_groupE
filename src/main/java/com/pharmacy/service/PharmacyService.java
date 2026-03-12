package com.pharmacy.service;

import com.pharmacy.entity.*;
import com.pharmacy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PharmacyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // Assessment Point 2: Saving Location implicitly via CascadeType.ALL
    public User saveUserWithLocation(User user) {
        return userRepository.save(user); // Cascades into rendering Location automatically
    }

    // Assessment Point 3: Pagination and Sorting handling parameters
    public Page<Drug> getDrugs(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return drugRepository.findAll(pageable);
    }

    // Assessment Point 7: Evaluation capability mapped through derived interface
    public boolean checkUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Assessment Point 8: Cross mapping data query implementation
    public List<User> getUsersByProvince(String code, String name) {
        return userRepository.findByProvinceCodeOrName(code, name);
    }

    // Delete a user by ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Update a user's name and email by ID
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existing -> {
            if (updatedUser.getName() != null) existing.setName(updatedUser.getName());
            if (updatedUser.getEmail() != null) existing.setEmail(updatedUser.getEmail());
            return userRepository.save(existing);
        });
    }

    // ========== Drug CRUD Operations ==========
    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    public Optional<Drug> getDrugById(Long id) {
        return drugRepository.findById(id);
    }

    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    public Optional<Drug> updateDrug(Long id, Drug updatedDrug) {
        return drugRepository.findById(id).map(existing -> {
            if (updatedDrug.getName() != null) existing.setName(updatedDrug.getName());
            if (updatedDrug.getPrice() != null) existing.setPrice(updatedDrug.getPrice());
            if (updatedDrug.getCategory() != null) existing.setCategory(updatedDrug.getCategory());
            return drugRepository.save(existing);
        });
    }

    public boolean deleteDrug(Long id) {
        if (drugRepository.existsById(id)) {
            drugRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ========== Category CRUD Operations ==========
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existing -> {
            if (updatedCategory.getName() != null) existing.setName(updatedCategory.getName());
            return categoryRepository.save(existing);
        });
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Drug> getDrugsByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(Category::getDrugs)
                .orElse(List.of());
    }

    // ========== Location CRUD Operations ==========
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> updateLocation(Long id, Location updatedLocation) {
        return locationRepository.findById(id).map(existing -> {
            if (updatedLocation.getName() != null) existing.setName(updatedLocation.getName());
            if (updatedLocation.getCode() != null) existing.setCode(updatedLocation.getCode());
            if (updatedLocation.getType() != null) existing.setType(updatedLocation.getType());
            if (updatedLocation.getParent() != null) existing.setParent(updatedLocation.getParent());
            return locationRepository.save(existing);
        });
    }

    public boolean deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ========== Supplier CRUD Operations ==========
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> updateSupplier(Long id, Supplier updatedSupplier) {
        return supplierRepository.findById(id).map(existing -> {
            if (updatedSupplier.getName() != null) existing.setName(updatedSupplier.getName());
            if (updatedSupplier.getContactEmail() != null) existing.setContactEmail(updatedSupplier.getContactEmail());
            return supplierRepository.save(existing);
        });
    }

    public boolean deleteSupplier(Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Drug> getDrugsBySupplier(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .map(Supplier::getDrugs)
                .orElse(List.of());
    }

    public Optional<Supplier> addDrugToSupplier(Long supplierId, Long drugId) {
        return supplierRepository.findById(supplierId).flatMap(supplier ->
                drugRepository.findById(drugId).map(drug -> {
                    if (!supplier.getDrugs().contains(drug)) {
                        supplier.getDrugs().add(drug);
                        return supplierRepository.save(supplier);
                    }
                    return supplier;
                })
        );
    }

}
