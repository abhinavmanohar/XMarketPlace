import com.xmarketplace.DTO.ProductListingDTO;
import com.xmarketplace.Entity.ProductListing;
import com.xmarketplace.Repository.ProductListingRepository;
import com.xmarketplace.service.ProductListingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductListingServiceTest {

    @InjectMocks
    ProductListingService productListingService;

    @Mock
    ProductListingRepository productListingRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValidProductPrice() {
        ProductListingDTO productListingDTO = new ProductListingDTO();
        productListingDTO.setPrice(500);
        assertTrue(productListingService.isValidProductPrice(productListingDTO));
    }

    @Test
    public void testGetAllProductListings() {
        ProductListing productListing1 = new ProductListing();
        ProductListing productListing2 = new ProductListing();
        when(productListingRepository.findAll()).thenReturn(Arrays.asList(productListing1, productListing2));
        List<ProductListingDTO> productListingDTOs = productListingService.getAllProductListings(1);
        assertEquals(2, productListingDTOs.size());
        verify(productListingRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductListingById() {
        ProductListing productListing = new ProductListing();
        when(productListingRepository.findById(1)).thenReturn(Optional.of(productListing));
        ProductListingDTO found = productListingService.getProductListingById(1);
        assertNotNull(found);
    }

    @Test
    public void testIsQuantityAvailable() {
        ProductListing productListing = new ProductListing();
        productListing.setQuantity(5);
        when(productListingRepository.findById(1)).thenReturn(Optional.of(productListing));
        assertTrue(productListingService.isQuantityAvailable(1));
    }

    @Test
    public void testUpdateProductListing() throws Exception {
        ProductListing productListing = new ProductListing();
        productListing.setId(1);
        productListing.setQuantity(5);
        when(productListingRepository.getById(1)).thenReturn(productListing);
        productListingService.updateProductListing(productListing);
        assertEquals(4, productListing.getQuantity());
    }
}