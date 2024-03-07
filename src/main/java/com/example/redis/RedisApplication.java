package com.example.redis;

import com.example.redis.entity.Product;
import com.example.redis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
public class RedisApplication {

    @Autowired
    private ProductDao dao;

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
    public Product findProductById(@PathVariable int id) {
        return dao.findProductById(id);
    }

    public String removeProduct(@PathVariable int id) {
        return dao.deleteProduct(id);
    }

    public static void main(String[] args) {

        SpringApplication.run(RedisApplication.class, args);
    }

}
