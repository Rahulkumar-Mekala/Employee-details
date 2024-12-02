package Springbootbackend.repositoty;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Springbootbackend.useraccessmanagementsystem.Adminpage;
import Springbootbackend.useraccessmanagementsystem.Employee;
import Springbootbackend.useraccessmanagementsystem.Manager;

@Repository
public interface AdminRepository extends JpaRepository<Adminpage, Long> {

	Optional<Adminpage> findByName(String name);

	
	
	
	
  
}
