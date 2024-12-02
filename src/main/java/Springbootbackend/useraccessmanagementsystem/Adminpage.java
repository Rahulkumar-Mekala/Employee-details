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
	@Table(name = "AdminDetails")
	public class Adminpage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	@Column(name = "Admin_Name",nullable = false)
	private String name;
	@Column(name = "Admin_password",nullable = false)
	private String password;
	@Column(name = "Admin_Role",nullable = false)
	private String role;
	@Column(name = "Admin_Status",nullable = false)
	private String status ;

	}

