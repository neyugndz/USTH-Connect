package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Category;
import com.usth_connect.vpn_server_backend_usth.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());

    @Autowired
    private CategoryService categoryService;

    // Endpoint to save a single category
//    @PostMapping("/save")
//    public ResponseEntity<Category> saveCategory(@RequestBody Map<String, Object> categoryData) {
//        try {
//            Category savedCategory = categoryService.saveCategory(categoryData);
//            return ResponseEntity.ok(savedCategory);
//        } catch (Exception e) {
//            LOGGER.info("Error saving category: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    // Endpoint to save multiple categories
    @PostMapping("/save-all")
    public ResponseEntity<List<Category>> saveAllCategories() {
        try {
            List<Category> savedCategories = categoryService.saveAllCategoriesFromMoodle();
            return ResponseEntity.ok(savedCategories);
        } catch (Exception e) {
            LOGGER.info("Error saving categories: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}
