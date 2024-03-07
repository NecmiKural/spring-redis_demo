package com.example.redis;

import com.example.redis.entity.Product;
import com.example.redis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class RedisApplication {

    private final ProductDao dao;

    public RedisApplication(ProductDao dao) {
        this.dao = dao;
    }

    // we already define product above so no need another path
    @PostMapping
    public Product save(@RequestBody Product product) {
        return dao.save(product);
    }

    @GetMapping
    public List<Product> fetchAll() {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result == null")
    // if price is bigger than 1000 it will bring it from database, ot from cache
    //@Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
    // we can also use @Cacheable(value = "product", unless = "#result == null" as well
    public Product findProductById(@PathVariable int id) {
        return dao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String removeProduct(@PathVariable int id) {
        return dao.deleteProduct(id);
    }

    public static void main(String[] args) {

        SpringApplication.run(RedisApplication.class, args);
    }

}
