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
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import com.sportyshoes.modules.purchases.services.*;
import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.entity.UserType;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.modules.users.services.*;
import com.sportyshoes.share.exceptions.SportyShoesException;
import com.sportyshoes.share.exceptions.SportyShoesResourceNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private DeleteUserService deleteUserService;

    @Autowired
    private ReadAllUserService readAllUserService;

    @Autowired
    private ReadUserService readUserService;

    @Autowired
    private UpdateUserService updateUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CreatePurchaseService createPurchaseService;

    @Autowired
    private DeletePurchaseService deletePurchaseService;

    @Autowired
    private ReadAllPurchaseService readAllPurchaseService;

    @Autowired
    private ReadPurchaseByUserService readPurchaseByUserService;

    @Autowired
    private ReadPurchaseService readPurchaseService;

    @Autowired
    private UpdatePurchaseService updatePurchaseService;

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



    @Test
    @DirtiesContext
    public void createUserService_basic() throws SportyShoesException {

        UserDto userDto = UserDto.builder().
                name("User 1").
                email("tom@gmail.com").
                password("password").
                userType(UserType.USER).build();

        UserDto userDto2 = createUserService.execute(userDto);

        User user = userRepository.findById(userDto2.getId()).get();

        assertEquals(userDto2.getId(), user.getId());
        assertEquals(userDto2.getName(), user.getName());
        assertEquals(userDto2.getEmail(), user.getEmail());
        assertEquals(userDto2.getPassword(), user.getPassword());
        assertEquals(userDto2.getUserType(), user.getUserType());

    }

    @Test
    @DirtiesContext
    public void createUserService_try_add_duplicated_email() {

        assertThrows(SportyShoesException.class, ()-> {
            UserDto userDto1 = UserDto.builder().
                    name("User 1").
                    email("tom@gmail.com").
                    password("password").
                    userType(UserType.USER).build();

            UserDto userDto2 = createUserService.execute(userDto1);

            UserDto userDtoNew = UserDto.builder().
                    name("User 2").
                    email("tom@gmail.com").
                    password("password").
                    userType(UserType.USER).build();

            UserDto userDto3 = createUserService.execute(userDtoNew);

        });

    }


    @Test
    @DirtiesContext
    public void deleteUserService() {
        User user = User.builder().
                name("User 1").
                email("tom@gmail.co,").
                password("password").
                userType(UserType.ADMIN).
                build();

        user = userRepository.save(user);

        deleteUserService.execute(user.getId());

        Optional<User> userOptional = userRepository.findById(user.getId());
        assertTrue(userOptional.isEmpty());

    }

    @Test
    @DirtiesContext
    public void readAllUserService_Basic() {
        User user1 = User.builder().
                name("Tom").
                email("tom@gmail.com").
                password("123").
                userType(UserType.USER).build();

        User user2 = User.builder().
                name("Tom").
                email("hulk@gmail.com").
                password("123").
                userType(UserType.USER).build();

        User user3 = User.builder().
                name("Thor").
                email("thor@gmail.com").
                password("123").
                userType(UserType.USER).build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<UserDto> users = readAllUserService.execute();

        assertTrue( users.size() >= 3);

        Optional<UserDto> userDtoOptional1 =  users.stream().filter(userDto -> userDto.getId().equals(user1.getId())).findFirst();
        Optional<UserDto> userDtoOptional2 =  users.stream().filter(userDto -> userDto.getId().equals(user2.getId())).findFirst();
        Optional<UserDto> userDtoOptional3 =  users.stream().filter(userDto -> userDto.getId().equals(user3.getId())).findFirst();

        assertEquals(user1.getId(), userDtoOptional1.get().getId());
        assertEquals(user1.getEmail(), userDtoOptional1.get().getEmail());
        assertEquals(user1.getPassword(), userDtoOptional1.get().getPassword());
        assertEquals(user1.getUserType(), userDtoOptional1.get().getUserType());

        assertEquals(user2.getId(), userDtoOptional2.get().getId());
        assertEquals(user2.getEmail(), userDtoOptional2.get().getEmail());
        assertEquals(user2.getPassword(), userDtoOptional2.get().getPassword());
        assertEquals(user2.getUserType(), userDtoOptional2.get().getUserType());

        assertEquals(user3.getId(), userDtoOptional3.get().getId());
        assertEquals(user3.getEmail(), userDtoOptional3.get().getEmail());
        assertEquals(user3.getPassword(), userDtoOptional3.get().getPassword());
        assertEquals(user3.getUserType(), userDtoOptional3.get().getUserType());

    }

    @Test
    @DirtiesContext
    public void readUserService() {
        User user = User.builder().
                name("Tom").
                email("tom@gmail.com").
                password("123").
                userType(UserType.USER).build();

        userRepository.save(user);

        Optional<UserDto> userDtoOptional = readUserService.execute(user.getId());

        assertEquals(user.getId(), userDtoOptional.get().getId());
        assertEquals(user.getName(), userDtoOptional.get().getName());
        assertEquals(user.getEmail(), userDtoOptional.get().getEmail());
        assertEquals(user.getPassword(), userDtoOptional.get().getPassword());
        assertEquals(user.getUserType(), userDtoOptional.get().getUserType());

    }

    @Test
    @DirtiesContext
    public void setUpdateUserService_basic() throws SportyShoesException {

        User user = User.builder().
                name("Tom").
                email("tom@gmail.com").
                password("123").
                userType(UserType.USER).build();

        userRepository.save(user);

        UserDto userDto = user.toDto();
        userDto.setName("Tom changed");
        userDto.setPassword("1234");
        userDto.setUserType(UserType.ADMIN);
        userDto.setEmail("tom_changed@gmail.com");

        updateUserService.execute(userDto);

        Optional<User> userOptional = userRepository.findById(userDto.getId());

        assert userOptional.isPresent();

        User user2 = userOptional.get();

        assertEquals(userDto.getId(), user2.getId());
        assertEquals(userDto.getName(), user2.getName());
        assertEquals(userDto.getEmail(), user2.getEmail());
        assertEquals(userDto.getPassword(), user2.getPassword());
        assertEquals(userDto.getUserType(), user2.getUserType());

    }


    @Test
    @DirtiesContext
    @Transactional
    public void createPurchaseService_basic() {

        User user = new User();
        user.setName("Doctor Strange");
        user.setEmail("doctor_stranger@gmail.com");
        user.setPassword("123");
        user.setUserType(UserType.USER);

        userRepository.save(user);

        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory(category);
        product1.setPrice(11.23);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory(category);
        product2.setPrice(34.56);
        productRepository.save(product2);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(user.toDto());

        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto();
        productPurchaseDto1.setPurchase(purchaseDto);
        productPurchaseDto1.setQuantity(10L);
        productPurchaseDto1.setProduct(product1.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto1);

        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto();
        productPurchaseDto2.setPurchase(purchaseDto);
        productPurchaseDto2.setQuantity(22L);
        productPurchaseDto2.setProduct(product2.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto2);

        PurchaseDto purchaseDto2 = createPurchaseService.execute(purchaseDto);

        Optional<Purchase> purchaseCreated = purchaseRepository.findById(purchaseDto2.getId());

        assertTrue(purchaseCreated.isPresent());
        assertEquals(2, purchaseCreated.get().getProductPurchaseList().size());

    }

    @Test
    @DirtiesContext
    public void deletePurchaseService_basic() {
        User user = new User();
        user.setName("Doctor Strange");
        user.setEmail("doctor_stranger@gmail.com");
        user.setPassword("123");
        user.setUserType(UserType.USER);

        userRepository.save(user);

        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory(category);
        product1.setPrice(11.23);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory(category);
        product2.setPrice(34.56);
        productRepository.save(product2);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(user.toDto());

        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto();
        productPurchaseDto1.setPurchase(purchaseDto);
        productPurchaseDto1.setQuantity(10L);
        productPurchaseDto1.setProduct(product1.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto1);

        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto();
        productPurchaseDto2.setPurchase(purchaseDto);
        productPurchaseDto2.setQuantity(22L);
        productPurchaseDto2.setProduct(product2.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto2);

        PurchaseDto purchaseDto2 = createPurchaseService.execute(purchaseDto);

        Optional<Purchase> purchaseCreated = purchaseRepository.findById(purchaseDto2.getId());

        assertTrue(purchaseCreated.isPresent());

        deletePurchaseService.execute(purchaseCreated.get().getId());

        Optional<Purchase> purchaseFound = purchaseRepository.findById(purchaseDto2.getId());

        assertTrue(purchaseFound.isEmpty());

    }


    @Test
    @DirtiesContext
    public void readAllPurchaseService_basic() {
        User user = new User();
        user.setName("Doctor Strange");
        user.setEmail("doctor_stranger@gmail.com");
        user.setPassword("123");
        user.setUserType(UserType.USER);

        userRepository.save(user);

        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory(category);
        product1.setPrice(11.23);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory(category);
        product2.setPrice(34.56);
        productRepository.save(product2);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(user.toDto());

        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto();
        productPurchaseDto1.setPurchase(purchaseDto);
        productPurchaseDto1.setQuantity(10L);
        productPurchaseDto1.setProduct(product1.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto1);

        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto();
        productPurchaseDto2.setPurchase(purchaseDto);
        productPurchaseDto2.setQuantity(22L);
        productPurchaseDto2.setProduct(product2.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto2);

        PurchaseDto purchaseDto2 = createPurchaseService.execute(purchaseDto);

        List<PurchaseDto> purchaseDtoList = readAllPurchaseService.execute();

        assertTrue(purchaseDtoList.size()>=1);
        PurchaseDto purchaseDtoFound=null;
        for ( PurchaseDto pDto: purchaseDtoList ) {
            if(pDto.getId().equals(purchaseDto2.getId())) {
                purchaseDtoFound = pDto;
            }
        }

        assertNotNull(purchaseDtoFound);

    }


    @Test
    @DirtiesContext
    public void readPurchaseByUserService_basic() {
        // Purchase I
        User user = new User();
        user.setName("Doctor Strange");
        user.setEmail("doctor_stranger@gmail.com");
        user.setPassword("123");
        user.setUserType(UserType.USER);

        userRepository.save(user);

        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory(category);
        product1.setPrice(11.23);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory(category);
        product2.setPrice(34.56);
        productRepository.save(product2);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(user.toDto());

        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto();
        productPurchaseDto1.setPurchase(purchaseDto);
        productPurchaseDto1.setQuantity(10L);
        productPurchaseDto1.setProduct(product1.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto1);

        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto();
        productPurchaseDto2.setPurchase(purchaseDto);
        productPurchaseDto2.setQuantity(22L);
        productPurchaseDto2.setProduct(product2.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto2);

        PurchaseDto purchaseDto2 = createPurchaseService.execute(purchaseDto);


        // Purchase II
        User user2 = new User();
        user2.setName("Doctor Strange II");
        user2.setEmail("doctor_strangerII@gmail.com");
        user2.setPassword("123");
        user2.setUserType(UserType.USER);

        userRepository.save(user2);


        PurchaseDto purchaseDtoNew = new PurchaseDto();
        purchaseDtoNew.setUser(user2.toDto());

        ProductPurchaseDto productPurchaseDtoNew = new ProductPurchaseDto();
        productPurchaseDtoNew.setPurchase(purchaseDtoNew);
        productPurchaseDtoNew.setQuantity(10L);
        productPurchaseDtoNew.setProduct(product1.toDto());

        purchaseDtoNew.addProductPurchaseDto(productPurchaseDtoNew);

        ProductPurchaseDto productPurchaseDtoNew2 = new ProductPurchaseDto();
        productPurchaseDtoNew2.setPurchase(purchaseDtoNew);
        productPurchaseDtoNew2.setQuantity(22L);
        productPurchaseDtoNew2.setProduct(product2.toDto());

        purchaseDtoNew.addProductPurchaseDto(productPurchaseDtoNew2);

        PurchaseDto purchaseDtoNew2 = createPurchaseService.execute(purchaseDtoNew);


        List<PurchaseDto> purchaseDtoList = readPurchaseByUserService.execute(user.getId());

        assertTrue(purchaseDtoList.size()==1);
        PurchaseDto purchaseDtoFound=null;
        for ( PurchaseDto pDto: purchaseDtoList ) {
            if(pDto.getId().equals(purchaseDto2.getId())) {
                purchaseDtoFound = pDto;
            }
        }

        assertNotNull(purchaseDtoFound);

    }


    @Test
    @DirtiesContext
    @Transactional
    public void readPurchaseService_basic() {

        User user = new User();
        user.setName("Doctor Strange");
        user.setEmail("doctor_stranger@gmail.com");
        user.setPassword("123");
        user.setUserType(UserType.USER);

        userRepository.save(user);

        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory(category);
        product1.setPrice(11.23);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory(category);
        product2.setPrice(34.56);
        productRepository.save(product2);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(user.toDto());

        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto();
        productPurchaseDto1.setPurchase(purchaseDto);
        productPurchaseDto1.setQuantity(10L);
        productPurchaseDto1.setProduct(product1.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto1);

        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto();
        productPurchaseDto2.setPurchase(purchaseDto);
        productPurchaseDto2.setQuantity(22L);
        productPurchaseDto2.setProduct(product2.toDto());

        purchaseDto.addProductPurchaseDto(productPurchaseDto2);

        PurchaseDto purchaseDto2 = createPurchaseService.execute(purchaseDto);

        Optional<PurchaseDto> purchaseCreated = readPurchaseService.execute(purchaseDto2.getId());
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseDto2.getId());

        assertTrue(purchaseCreated.isPresent());
        assertTrue(purchaseOptional.isPresent());

        assertEquals(purchaseOptional.get().getId(), purchaseCreated.get().getId());

        assertEquals(purchaseOptional.get().getProductPurchaseList().size(), purchaseCreated.get().getProductPurchaseDtoList().size());


    }

}
