package com.xmarketplace.Repository;

import com.xmarketplace.Entity.ProductListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductListingRepository extends JpaRepository<ProductListing, Integer> {
}
