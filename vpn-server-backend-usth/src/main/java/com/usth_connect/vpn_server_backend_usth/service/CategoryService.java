package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Category;
import com.usth_connect.vpn_server_backend_usth.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CategoryService {
    private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());
    public static final Long DUMMY_PARENT_CATEGORY_ID = -1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MoodleService moodleService;

    // Method to save category fetched from Moodle
    public Category saveCategoryFromMoodle(Map<String, Object> categoryData) {
        Long categoryId = ((Number) categoryData.get("id")).longValue();
        String categoryName = (String) categoryData.get("name");
        Long parentCategoryId = categoryData.get("parent") != null ? ((Number) categoryData.get("parent")).longValue() : null;

        // Check if the category already exists
        Optional<Category> existingCategoryOpt = categoryRepository.findByCategoryId(categoryId);
        Category category;

        if (existingCategoryOpt.isPresent()) {
            // Update existing category
            category = existingCategoryOpt.get();
            category.setCategoryName(categoryName);
            category.setParentCategoryId(parentCategoryId);
        } else {
            // Create a new category
            category = new Category();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryName);
            category.setParentCategoryId(parentCategoryId);
        }

        // Handle parent category (if present)
        if (parentCategoryId != null && parentCategoryId != 0) {
            Optional<Category> parentCategoryOpt = categoryRepository.findByCategoryId(parentCategoryId);
            if (parentCategoryOpt.isPresent()) {
                category.setParentCategory(parentCategoryOpt.get());
            } else {
                LOGGER.warning("Parent category not found for category: " + categoryId);

                // Create and use a dummy parent category if not found
                Category dummyParentCategory = getOrCreateDummyParentCategory();
                category.setParentCategory(dummyParentCategory);
            }
        } else {
            category.setParentCategory(null);
        }

        // Save the category to the database
        return categoryRepository.save(category);
    }

    private Category getOrCreateDummyParentCategory() {
        // Check if the dummy parent category already exists in the database
        Optional<Category> dummyParentOpt = categoryRepository.findByCategoryId(DUMMY_PARENT_CATEGORY_ID);
        Category dummyParentCategory;

        if (dummyParentOpt.isPresent()) {
            // Return the existing dummy parent category
            dummyParentCategory = dummyParentOpt.get();
        } else {
            // Create a new dummy parent category if it doesn't exist
            dummyParentCategory = new Category();
            dummyParentCategory.setCategoryId(DUMMY_PARENT_CATEGORY_ID);
            dummyParentCategory.setCategoryName("Dummy Parent");
            dummyParentCategory.setParentCategoryId(null);
            categoryRepository.save(dummyParentCategory); // Save the dummy parent category
        }

        return dummyParentCategory;
    }

    // Method to save all categories fetched from Moodle
    public List<Category> saveAllCategoriesFromMoodle() {
        List<Map<String, Object>> categoriesData = moodleService.fetchCategories();
        if (categoriesData == null || categoriesData.isEmpty()) {
            throw new IllegalArgumentException("No categories found in Moodle.");
        }

        List<Category> savedCategories = new ArrayList<>();
        for (Map<String, Object> categoryData : categoriesData) {
            try {
                savedCategories.add(saveCategoryFromMoodle(categoryData));
            } catch (Exception e) {
                // Log and skip problematic categories
                LOGGER.info("Error saving category from Moodle: " + e.getMessage());
            }
        }
        return savedCategories;
    }
}
