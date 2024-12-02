
package Springbootbackend.repositoty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Springbootbackend.useraccessmanagementsystem.Employee;
import Springbootbackend.useraccessmanagementsystem.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

	Optional<Manager> findByName(String name);

	

  
}
