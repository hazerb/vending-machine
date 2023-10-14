package com.casestudy.repository;

import com.casestudy.model.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VendingMachineRepository extends JpaRepository<VendingMachine, String> {
}
