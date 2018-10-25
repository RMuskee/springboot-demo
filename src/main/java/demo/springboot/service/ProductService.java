package demo.springboot.service;


import demo.springboot.model.Product;

public interface ProductService {
    Iterable<Product> listAllProducts();

    Product findProductById(Integer id);

    Product saveProduct(Product product);

    void deleteById(Integer id);
}
