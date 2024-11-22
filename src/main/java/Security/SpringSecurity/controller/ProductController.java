package Security.SpringSecurity.controller;

import Security.SpringSecurity.dto.ProductResponseDto;
import Security.SpringSecurity.model.Product;
import Security.SpringSecurity.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Valid ProductResponseDto product){
        return productService.save(product);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProductAll(){
        return ResponseEntity.ok(productService.getProductAll());
    }
}
