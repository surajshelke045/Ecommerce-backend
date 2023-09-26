package com.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.Model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
