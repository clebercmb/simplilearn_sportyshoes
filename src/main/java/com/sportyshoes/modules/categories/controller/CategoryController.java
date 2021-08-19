package com.sportyshoes.modules.categories.controller;


import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadAllCategoriesService readAllCategoriesService;
    private final ReadCategoryService readCategoryService;
    private final CreateCategoryService createCategoryService;
    private final UpdateCategoryService updateCategoryService;
    private final DeleteCategoryService deleteCategoryService;

    @Autowired
    public CategoryController(ReadAllCategoriesService readAllCategoriesService,
                              ReadCategoryService readCategoryService,
                              CreateCategoryService createCategoryService,
                              UpdateCategoryService updateCategoryService,
                              DeleteCategoryService deleteCategoryService) {
        this.readAllCategoriesService = readAllCategoriesService;
        this.readCategoryService = readCategoryService;
        this.createCategoryService = createCategoryService;
        this.updateCategoryService = updateCategoryService;
        this.deleteCategoryService = deleteCategoryService;
    }

    @GetMapping
    public ModelAndView all() {
        logger.info(">>>>All");
        ModelAndView modelAndView = new ModelAndView("categories");
        List<CategoryDto> categoryDtoList = readAllCategoriesService.execute();
        modelAndView.addObject("categories", categoryDtoList);
        return modelAndView;
    }

    @GetMapping(value="/add_form")
    ModelAndView add() {
        logger.info(">>>>All");
        ModelAndView modelAndView = new ModelAndView("categoriesAddForm");
        modelAndView.addObject("create", true);

        return modelAndView;
    }

    //  @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public ModelAndView read(@PathVariable Optional<Long> id) {

        ModelAndView modelAndView = new ModelAndView("categoriesAddForm");
        if(id.isPresent()) {
            Optional<CategoryDto> categoryDtoOptional = readCategoryService.execute(id.get());
            modelAndView.addObject("update", true);
            if(categoryDtoOptional.isPresent()) {
                modelAndView.addObject("category", categoryDtoOptional.get());
            } else {
                modelAndView.addObject("category", CategoryDto.builder().build());
            }
        } else {
            modelAndView.addObject("create", true);
            modelAndView.addObject("category", CategoryDto.builder().build());
        }

        return modelAndView;

    }


    @RequestMapping(path = "/saveCategory", method = RequestMethod.POST)
    public String createOrUpdateCategory(CategoryDto category) {
        if (category.getId() == null) {
            createCategoryService.execute(category);
        } else {
            updateCategoryService.execute(category);
        }

        return "redirect:/categories";
    }

    //  @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    @RequestMapping(path = {"/delete/{id}"})
    public String delete(@PathVariable Long id) {

        deleteCategoryService.execute(id);

        return "redirect:/categories";

    }

}
