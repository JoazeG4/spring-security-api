package Security.SpringSecurity.service;

import Security.SpringSecurity.dto.ProductResponseDto;
import Security.SpringSecurity.model.Product;
import Security.SpringSecurity.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ResponseEntity<Object> save(ProductResponseDto product){
        productRepository.save(new Product(product.name(), product.model(), product.brand(), product.price(), product.amount()));
        return ResponseEntity.ok().build();
    }

    public List<Product> getProductAll(){
        return productRepository.findAll();
    }
}
