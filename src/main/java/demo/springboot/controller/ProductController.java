package demo.springboot.controller;

import demo.springboot.model.Product;
import demo.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ProductController {

    private ProductService productService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "products")
    public String list(Model model){
        model.addAttribute("products", productService.listAllProducts());
        return "products/products";
    }

    @GetMapping("product/view/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.findProductById(id));
        return "products/productshow";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model){
        productService.deleteById(id);
        return "redirect:products";
    }

    @GetMapping("product/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.findProductById(id));
        return "products/productform";
    }

    @GetMapping("product/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/productform";
    }

    @PostMapping("product")
    public String saveProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "products/productform";
        } else {
            productService.saveProduct(product);
            return "redirect:product/view/" + product.getId();
        }
    }
}