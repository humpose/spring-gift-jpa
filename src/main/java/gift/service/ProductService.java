package gift.service;

import gift.converter.ProductConverter;
import gift.dto.ProductDTO;
import gift.dto.ProductPageRequestDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final int MAX_PAGE_SIZE = 30;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Pageable createPageRequest(ProductPageRequestDTO pageRequestDTO) {
        int size = pageRequestDTO.getSize();
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }

        Sort sort;
        if (pageRequestDTO.getDirection().equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = Sort.by(pageRequestDTO.getSortBy()).descending();
        } else {
            sort = Sort.by(pageRequestDTO.getSortBy()).ascending();
        }

        return PageRequest.of(pageRequestDTO.getPage(), size, sort);
    }

    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductConverter::convertToDTO);
    }

    public Long addProduct(ProductDTO productDTO) {
        Product product = ProductConverter.convertToEntity(productDTO);
        productRepository.save(product);
        return product.getId();
    }

    public Optional<ProductDTO> findProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductConverter::convertToDTO);
    }

    public void updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Product updatedProduct = new Product(existingProduct.getId(),
            ProductConverter.convertToEntity(productDTO).getName(),
            productDTO.getPrice(),
            productDTO.getImageUrl());

        productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}