package demo.springboot.controller;

import demo.springboot.model.Product;
import demo.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public String list(Model model){
        model.addAttribute("products", productService.listAllProducts());
        return "/products/products";
    }

    @GetMapping("product/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.findProductById(id));
        return "/products/productshow";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model){
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("product/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.findProductById(id));
        return "/products/productform";
    }

    @GetMapping("product/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "/products/productform";
    }

    @PostMapping(value = "product")
    public String saveProduct(Product product){
        productService.saveProduct(product);
        return "redirect:product/" + product.getId();
    }
}
