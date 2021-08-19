package com.sportyshoes;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.categories.services.CreateCategoryService;
import com.sportyshoes.modules.categories.services.ReadCategoryService;
import com.sportyshoes.modules.categories.services.UpdateCategoryService;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.products.services.*;
import com.sportyshoes.share.SportyShoesException;
import com.sportyshoes.share.SportyShoesResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SportyshoesApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CreateCategoryService createCategoryService;

    @Autowired
    private ReadCategoryService readCategoryService;

    @Autowired
    private UpdateCategoryService updateCategoryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CreateProductService createProductService;

    @Autowired
    private DeleteProductService deleteProductService;

    @Autowired
    private ReadAllProductService readAllProductService;

    @Autowired
    private ReadProductService readProductService;

    @Autowired
    private UpdateProductService updateProductService;

    @Test
    @DirtiesContext
    @Transactional
    public void save_basic() {
        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);

        Category category2 = categoryRepository.findById(category.getId()).get();

        assertEquals(category,category2);

    }

    @Test
    @DirtiesContext
    public void setCreateCategoryService_basic() {
        CategoryDto categoryDto = CategoryDto.builder().name("Category 2").build();

        CategoryDto categoryDto2 = createCategoryService.execute(categoryDto);

        Category category = categoryRepository.findById(categoryDto2.getId()).get();

        assertEquals(categoryDto2.getId(), category.getId());
        assertEquals(categoryDto2.getName(), category.getName());

    }


    @Test
    @DirtiesContext
    public void readCategoryService() {

        CategoryDto categoryDto = CategoryDto.builder().name("Category Service").build();
        CategoryDto categoryDto2 = createCategoryService.execute(categoryDto);
        CategoryDto categoryDto3 = readCategoryService.execute(categoryDto2.getId()).get();

        assertEquals(categoryDto2.getId(), categoryDto3.getId());
        assertEquals(categoryDto2.getName(), categoryDto3.getName());

    }

    @Test
    @DirtiesContext
    public void readCategoryService_return_null() {
        Optional<CategoryDto> categoryDto = readCategoryService.execute(-1L);
        logger.debug(">>>readCategoryService_return_null={}",String.valueOf(categoryDto));
        assertTrue(categoryDto.isEmpty());

    }

    @Test
    @DirtiesContext
    public void updateCategoryService() {

        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);
        Optional<Category> category2 = categoryRepository.findById(category.getId());

        CategoryDto categoryDto = category2.get().toDto();
        categoryDto.setName("Category Changed");

        updateCategoryService.execute(categoryDto);

        Category category3 = categoryRepository.findById(category.getId()).get();

        assertEquals(category.getId(), category3.getId());
        assertEquals("Category Changed", category3.getName());


    }


    @Test
    @DirtiesContext
    public void updateCategoryService_non_exist_category() {

        Category category = Category.builder().id(-1L).name("Category 1").build();

        CategoryDto categoryDto = category.toDto();

        Optional<CategoryDto> categoryDto2Optional = updateCategoryService.execute(categoryDto);

        assertTrue(categoryDto2Optional.isEmpty());

    }


    @Test
    @DirtiesContext
    @Transactional
    public void createProductService_basic() throws SportyShoesException {
        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);

        ProductDto productDto = ProductDto.builder().name("Product 1").category(category.toDto()).build();

        ProductDto productDto2 = createProductService.execute(productDto);

        Product product = productRepository.getById(productDto2.getId());

        assertEquals(productDto.getName(), product.getName());

    }


    @Test
    @DirtiesContext
    @Transactional
    public void createProductService_category_not_found_exception()  {

        assertThrows(SportyShoesResourceNotFoundException.class, () -> {
            //Code under test
            Category category = Category.builder().id(-1L).name("Category 1").build();

            ProductDto productDto = ProductDto.builder().name("Product 1").category(category.toDto()).build();

            ProductDto productDto2 = createProductService.execute(productDto);

            Product product = productRepository.getById(productDto2.getId());

            assertEquals(productDto.getName(), product.getName());

        });

    }

    @Test
    @DirtiesContext
    public void deleteProductService_basic() {

        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);

        Product product = Product.builder().name("Product 1").category(category).build();
        product = productRepository.save(product);

        deleteProductService.execute(product.getId());

        Optional<Product> productDeleted = productRepository.findById(product.getId());

        assertTrue(productDeleted.isEmpty());

    }

    @Test
    @DirtiesContext
    public void ReadAllProductService() {

        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);

        Product product1 = Product.builder().name("Product 1").category(category).build();
        Product product2 = Product.builder().name("Product 2").category(category).build();
        Product product3 = Product.builder().name("Product 3").category(category).build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<ProductDto> productList = readAllProductService.execute();

        assertTrue(productList.size() >=3);

    }

    @Test
    @DirtiesContext
    public void readProductService() {

        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);
        Product product = Product.builder().name("Product 1").category(category).build();
        product = productRepository.save(product);

        ProductDto productDto = readProductService.execute(product.getId()).get();

        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getCategory().getId(), productDto.getCategory().getId());
        assertEquals(product.getCategory().getName(), productDto.getCategory().getName());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void updadateProductService_basic() {

        Category category = Category.builder().name("Category 1").build();
        category = categoryRepository.save(category);

        Category category2 = Category.builder().name("Category 2").build();
        category2 = categoryRepository.save(category2);

        Product product = Product.builder().name("Product 1").category(category).build();
        product = productRepository.save(product);

        ProductDto productDto = product.toDto();
        productDto.setName("Category changed");
        productDto.setCategory(category2.toDto());

        ProductDto productDto2 = updateProductService.execute(productDto).get();

        Product product2 = productRepository.getById(productDto.getId());
        assertEquals(productDto.getId(), product2.getId());
        assertEquals(productDto.getName(), product2.getName());
        assertEquals(productDto.getCategory().getId(), product2.getCategory().getId());
    }

    @Test
    @DirtiesContext
    public void updateProductService_with_null_product() {

        assertThrows(SportyShoesResourceNotFoundException.class, () -> {
            updateProductService.execute(null).get();
        });

    }

    @Test
    @DirtiesContext
    public void updateProductService_with_non_exist_product() {

        assertThrows(SportyShoesResourceNotFoundException.class, () -> {
            Category category = Category.builder().name("Category 1").build();
            category = categoryRepository.save(category);

            ProductDto productDto = ProductDto.builder().id(-1L).name("Product New").category(category.toDto()).build();
            ProductDto productDto2 = updateProductService.execute(productDto).get();
        });

    }

    @Test
    @DirtiesContext
    public void updateProductService_with_non_exist_category() {

        assertThrows(SportyShoesResourceNotFoundException.class, () -> {
            Category category = Category.builder().name("Category 1").build();
            categoryRepository.save(category);

            Product product = Product.builder().name("Product 1").category(category).build();
            productRepository.save(product);

            CategoryDto categoryNew = Category.builder().id(-1L).name("Category New").build().toDto();
            ProductDto productDto = product.toDto();
            productDto.setCategory(categoryNew);

            ProductDto productDto2 = updateProductService.execute(productDto).get();
        });

    }






}
