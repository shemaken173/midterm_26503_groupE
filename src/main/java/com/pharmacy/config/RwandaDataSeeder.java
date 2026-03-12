package com.pharmacy.config;

import com.pharmacy.entity.*;
import com.pharmacy.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RwandaDataSeeder {

    @Bean
    CommandLineRunner initRwandaDatabase(
            CategoryRepository categoryRepository,
            DrugRepository drugRepository,
            UserRepository userRepository,
            LocationRepository locationRepository,
            SupplierRepository supplierRepository) {

        return args -> {
            // Check if data already exists
            if (categoryRepository.count() > 0) {
                System.out.println("Database already seeded. Skipping...");
                return;
            }

            System.out.println("========================================");
            System.out.println("Starting Rwanda Location Database Seeding...");
            System.out.println("========================================");

            // ========== BUILD RWANDA LOCATION HIERARCHY ==========
            System.out.println("\n1. Creating Rwanda Location Hierarchy...");

            // PROVINCE: Kigali City
            Location kigaliCity = new Location();
            kigaliCity.setName("Kigali City");
            kigaliCity.setCode("KC");
            kigaliCity.setType(LocationType.PROVINCE);
            kigaliCity.setParent(null); // Top level

            // DISTRICT: Gasabo
            Location gasabo = new Location();
            gasabo.setName("Gasabo");
            gasabo.setCode("GAS");
            gasabo.setType(LocationType.DISTRICT);
            gasabo.setParent(kigaliCity);

            // SECTOR: Remera
            Location remera = new Location();
            remera.setName("Remera");
            remera.setCode("REM");
            remera.setType(LocationType.SECTOR);
            remera.setParent(gasabo);

            // CELL: Rukiri I
            Location rukiriI = new Location();
            rukiriI.setName("Rukiri I");
            rukiriI.setCode("RUK1");
            rukiriI.setType(LocationType.CELL);
            rukiriI.setParent(remera);

            // VILLAGES under Rukiri I
            Location rukiriI_VillageA = new Location();
            rukiriI_VillageA.setName("Village A");
            rukiriI_VillageA.setCode("RUK1-VA");
            rukiriI_VillageA.setType(LocationType.VILLAGE);
            rukiriI_VillageA.setParent(rukiriI);

            Location rukiriI_VillageB = new Location();
            rukiriI_VillageB.setName("Village B");
            rukiriI_VillageB.setCode("RUK1-VB");
            rukiriI_VillageB.setType(LocationType.VILLAGE);
            rukiriI_VillageB.setParent(rukiriI);

            // CELL: Rukiri II
            Location rukiriII = new Location();
            rukiriII.setName("Rukiri II");
            rukiriII.setCode("RUK2");
            rukiriII.setType(LocationType.CELL);
            rukiriII.setParent(remera);

            // VILLAGES under Rukiri II
            Location rukiriII_VillageA = new Location();
            rukiriII_VillageA.setName("Village A");
            rukiriII_VillageA.setCode("RUK2-VA");
            rukiriII_VillageA.setType(LocationType.VILLAGE);
            rukiriII_VillageA.setParent(rukiriII);

            // SECTOR: Kacyiru
            Location kacyiru = new Location();
            kacyiru.setName("Kacyiru");
            kacyiru.setCode("KAC");
            kacyiru.setType(LocationType.SECTOR);
            kacyiru.setParent(gasabo);

            // CELL: Kamatamu
            Location kamatamu = new Location();
            kamatamu.setName("Kamatamu");
            kamatamu.setCode("KAM");
            kamatamu.setType(LocationType.CELL);
            kamatamu.setParent(kacyiru);

            // VILLAGE under Kamatamu
            Location kamatamu_VillageA = new Location();
            kamatamu_VillageA.setName("Village A");
            kamatamu_VillageA.setCode("KAM-VA");
            kamatamu_VillageA.setType(LocationType.VILLAGE);
            kamatamu_VillageA.setParent(kamatamu);

            // DISTRICT: Nyarugenge
            Location nyarugenge = new Location();
            nyarugenge.setName("Nyarugenge");
            nyarugenge.setCode("NYA");
            nyarugenge.setType(LocationType.DISTRICT);
            nyarugenge.setParent(kigaliCity);

            // SECTOR: Muhima
            Location muhima = new Location();
            muhima.setName("Muhima");
            muhima.setCode("MUH");
            muhima.setType(LocationType.SECTOR);
            muhima.setParent(nyarugenge);

            // CELL: Nyabugogo
            Location nyabugogo = new Location();
            nyabugogo.setName("Nyabugogo");
            nyabugogo.setCode("NYB");
            nyabugogo.setType(LocationType.CELL);
            nyabugogo.setParent(muhima);

            // VILLAGE under Nyabugogo
            Location nyabugogo_VillageA = new Location();
            nyabugogo_VillageA.setName("Village A");
            nyabugogo_VillageA.setCode("NYB-VA");
            nyabugogo_VillageA.setType(LocationType.VILLAGE);
            nyabugogo_VillageA.setParent(nyabugogo);

            // PROVINCE: Eastern Province
            Location easternProvince = new Location();
            easternProvince.setName("Eastern Province");
            easternProvince.setCode("EP");
            easternProvince.setType(LocationType.PROVINCE);
            easternProvince.setParent(null);

            // DISTRICT: Rwamagana
            Location rwamagana = new Location();
            rwamagana.setName("Rwamagana");
            rwamagana.setCode("RWA");
            rwamagana.setType(LocationType.DISTRICT);
            rwamagana.setParent(easternProvince);

            // SECTOR: Gahengeri
            Location gahengeri = new Location();
            gahengeri.setName("Gahengeri");
            gahengeri.setCode("GAH");
            gahengeri.setType(LocationType.SECTOR);
            gahengeri.setParent(rwamagana);

            // CELL: Kajevuba
            Location kajevuba = new Location();
            kajevuba.setName("Kajevuba");
            kajevuba.setCode("KAJ");
            kajevuba.setType(LocationType.CELL);
            kajevuba.setParent(gahengeri);

            // VILLAGE under Kajevuba
            Location kajevuba_VillageA = new Location();
            kajevuba_VillageA.setName("Village A");
            kajevuba_VillageA.setCode("KAJ-VA");
            kajevuba_VillageA.setType(LocationType.VILLAGE);
            kajevuba_VillageA.setParent(kajevuba);

            System.out.println("   ✓ Created Rwanda location hierarchy");

            // ========== SEED CATEGORIES ==========
            System.out.println("\n2. Creating Categories...");
            Category antibiotics = new Category();
            antibiotics.setName("Antibiotics");
            categoryRepository.save(antibiotics);

            Category painRelievers = new Category();
            painRelievers.setName("Pain Relievers");
            categoryRepository.save(painRelievers);

            Category vitamins = new Category();
            vitamins.setName("Vitamins");
            categoryRepository.save(vitamins);

            System.out.println("   ✓ Created 3 categories");

            // ========== SEED DRUGS ==========
            System.out.println("\n3. Creating Drugs...");
            
            Drug amoxicillin = new Drug();
            amoxicillin.setName("Amoxicillin 500mg");
            amoxicillin.setPrice(15.99);
            amoxicillin.setCategory(antibiotics);
            drugRepository.save(amoxicillin);

            Drug aspirin = new Drug();
            aspirin.setName("Aspirin 500mg");
            aspirin.setPrice(9.99);
            aspirin.setCategory(painRelievers);
            drugRepository.save(aspirin);

            Drug vitaminC = new Drug();
            vitaminC.setName("Vitamin C 1000mg");
            vitaminC.setPrice(12.50);
            vitaminC.setCategory(vitamins);
            drugRepository.save(vitaminC);

            Drug ibuprofen = new Drug();
            ibuprofen.setName("Ibuprofen 400mg");
            ibuprofen.setPrice(11.99);
            ibuprofen.setCategory(painRelievers);
            drugRepository.save(ibuprofen);

            Drug penicillin = new Drug();
            penicillin.setName("Penicillin 250mg");
            penicillin.setPrice(18.50);
            penicillin.setCategory(antibiotics);
            drugRepository.save(penicillin);

            System.out.println("   ✓ Created 5 drugs");

            // ========== SEED USERS WITH VILLAGE LOCATIONS ==========
            System.out.println("\n4. Creating Users linked to Villages...");

            // User 1 - Shema Ken in Rukiri I, Village A (Kigali City)
            User shemaKen = new User();
            shemaKen.setName("Shema Ken");
            shemaKen.setEmail("shema.ken@auca.ac.rw");
            shemaKen.setLocation(rukiriI_VillageA);
            userRepository.save(shemaKen);

            // User 2 - Alice in Rukiri I, Village B (Kigali City)
            User alice = new User();
            alice.setName("Alice Mukamana");
            alice.setEmail("alice@example.com");
            alice.setLocation(rukiriI_VillageB);
            userRepository.save(alice);

            // User 3 - Bob in Rukiri II, Village A (Kigali City)
            User bob = new User();
            bob.setName("Bob Nkusi");
            bob.setEmail("bob@example.com");
            bob.setLocation(rukiriII_VillageA);
            userRepository.save(bob);

            // User 4 - Grace in Kamatamu, Village A (Kigali City)
            User grace = new User();
            grace.setName("Grace Uwase");
            grace.setEmail("grace@example.com");
            grace.setLocation(kamatamu_VillageA);
            userRepository.save(grace);

            // User 5 - David in Nyabugogo, Village A (Kigali City)
            User david = new User();
            david.setName("David Habimana");
            david.setEmail("david@example.com");
            david.setLocation(nyabugogo_VillageA);
            userRepository.save(david);

            // User 6 - Mary in Kajevuba, Village A (Eastern Province)
            User mary = new User();
            mary.setName("Mary Umutoni");
            mary.setEmail("mary@example.com");
            mary.setLocation(kajevuba_VillageA);
            userRepository.save(mary);

            System.out.println("   ✓ Created 6 users linked to villages");

            // ========== SEED SUPPLIERS ==========
            System.out.println("\n5. Creating Suppliers...");

            Supplier medSupply = new Supplier();
            medSupply.setName("MedSupply Rwanda Ltd");
            medSupply.setContactEmail("contact@medsupply.rw");
            supplierRepository.save(medSupply);

            Supplier pharmaCorp = new Supplier();
            pharmaCorp.setName("PharmaCorp International");
            pharmaCorp.setContactEmail("info@pharmacorp.com");
            supplierRepository.save(pharmaCorp);

            Supplier healthPlus = new Supplier();
            healthPlus.setName("HealthPlus Distributors");
            healthPlus.setContactEmail("sales@healthplus.rw");
            supplierRepository.save(healthPlus);

            System.out.println("   ✓ Created 3 suppliers");

            // ========== SEED MANY-TO-MANY RELATIONSHIPS ==========
            System.out.println("\n6. Creating Supplier-Drug Relationships...");

            medSupply.setDrugs(Arrays.asList(amoxicillin, aspirin, vitaminC));
            supplierRepository.save(medSupply);

            pharmaCorp.setDrugs(Arrays.asList(amoxicillin, ibuprofen, penicillin));
            supplierRepository.save(pharmaCorp);

            healthPlus.setDrugs(Arrays.asList(aspirin, vitaminC, ibuprofen));
            supplierRepository.save(healthPlus);

            System.out.println("   ✓ Created supplier-drug relationships");

            // ========== SUMMARY ==========
            System.out.println("\n========================================");
            System.out.println("Rwanda Location Database Seeding Completed!");
            System.out.println("========================================");
            System.out.println("Summary:");
            System.out.println("  - Location Hierarchy: Province → District → Sector → Cell → Village");
            System.out.println("  - Provinces: 2 (Kigali City, Eastern Province)");
            System.out.println("  - Total Locations: " + locationRepository.count());
            System.out.println("  - Categories: " + categoryRepository.count());
            System.out.println("  - Drugs: " + drugRepository.count());
            System.out.println("  - Users: " + userRepository.count() + " (all linked to villages)");
            System.out.println("  - Suppliers: " + supplierRepository.count());
            System.out.println("========================================");
            System.out.println("\nLocation Examples:");
            System.out.println("  - shemaKen: " + shemaKen.getFullAddress());
            System.out.println("  - Mary: " + mary.getFullAddress());
            System.out.println("\nProvince Resolution:");
            System.out.println("  - shemaKen's Province: " + shemaKen.getProvinceName() + " (" + shemaKen.getProvinceCode() + ")");
            System.out.println("  - Mary's Province: " + mary.getProvinceName() + " (" + mary.getProvinceCode() + ")");
            System.out.println("========================================");
            System.out.println("\nYou can now test all endpoints!");
            System.out.println("Base URL: http://localhost:8080/api/pharmacy");
            System.out.println("========================================\n");
        };
    }
}
