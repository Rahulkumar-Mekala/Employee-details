package Springbootbackend.useraccessmanagementsystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "EmployeeDetails")
public class Employee {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name = "Employee_Name",nullable = false)
private String name;
@Column(name = "Employee_password",nullable = false)
private String password;
@Column(name = "Employee_Role",nullable = false)
private String role;
@Column(name = "Employee_Status",nullable = false)
private String status ;

}
