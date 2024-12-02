package Springbootbackend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Springbootbackend.repositoty.AdminRepository;
import Springbootbackend.repositoty.EmployeeRepository;
import Springbootbackend.useraccessmanagementsystem.Adminpage;
import Springbootbackend.useraccessmanagementsystem.Employee;
import Springbootbackend.useraccessmanagementsystem.Manager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class AdminAuthenticationController {
@Autowired
private AdminRepository adminRepository;
@Autowired
private EmployeeRepository employeeRepository;

@GetMapping("Adminform")
public String getadminlogin(Model model) {
	model.addAttribute("admin", new Adminpage());
	return "Adminlogin";
	
}

@PostMapping("/adminloginpage")
public String AdminLogin(@ModelAttribute("admin") Manager manager, Model model) {
    Optional<Adminpage> optional = adminRepository.findByName(manager.getName());
    
    if (optional.isPresent()) {
        Adminpage existingAdmin = optional.get();
        
        if (existingAdmin.getPassword().equals(manager.getPassword())) {
        	
        	
            model.addAttribute("message", "Login successful!");
            List<Employee> approved = employeeRepository.findByStatus("Approved");
            model.addAttribute("approveemployee", approved);
             
            List<Employee> rejected = employeeRepository.findByStatus("Rejected");
            model.addAttribute("rejectedemployee", rejected);
           model.addAttribute("message", " Update the Employee Details :");
            
             return "Adminhomepage";
        } else {
            model.addAttribute("error", "Invalid password.");
        }
    } else {
        model.addAttribute("error", "Email not registered.");
    }
    return "Adminlogin"; 
}


@GetMapping("/update")
public String update(Model model) {
    model.addAttribute("UpdateEmployee", new Employee());
    return "Adminupdatedate";  
}

@PostMapping("/updateemplyee")
public String getupdate(@ModelAttribute("UpdateEmployee") Employee employee, Model model) {
   Optional<Employee> optionalemployee = employeeRepository.findByName(employee.getName());
    
  
    if (optionalemployee.isEmpty()) {
        model.addAttribute("error", "No student found with the email " + employee.getName());
        return "Adminupdatedate";
    }
   
    
    Employee existingemployee = optionalemployee.get();
    existingemployee.setName(employee.getName());
    existingemployee.setPassword(employee.getPassword());
    existingemployee.setRole(employee.getRole());
    

    employeeRepository.save(existingemployee);
    model.addAttribute("updatestudent", existingemployee);
    model.addAttribute("message", "Employee details updated successfully!");

    return "userupdatesucessfully";
}

@GetMapping("/delete")
public String delete(Model model) {
    model.addAttribute("deleteEmployee", new Employee());
    return "Admindelete";  
}
	
@PostMapping("/Admindeletemployee")
public String AdmindeleteUser(@ModelAttribute("deleteEmployee") Employee employee, Model model) {
    Optional<Employee> optionalDelete = employeeRepository.findByName(employee.getName());

    if (optionalDelete.isPresent()) {
        Employee existingStudent = optionalDelete.get();

        if (existingStudent.getPassword().equals(employee.getPassword())) {
            employeeRepository.delete(existingStudent);
            model.addAttribute("message", "Delete successful!");
            return "admindeletesucessfully";
        } else {
            model.addAttribute("error", "Incorrect password. Deletion failed.");
            return "Admindelete";
        }
    } else {
        model.addAttribute("error", "User not found with provided email.");
        return "Admindelete";
    }
}

@GetMapping("/Adminlogout")
public String Adminlogout(RedirectAttributes redirectAttributes, HttpServletRequest request) {
    HttpSession session = request.getSession(false); 
    if (session != null) {
        session.invalidate(); 
        redirectAttributes.addFlashAttribute("message", "Logout successful.");
    }
    return "redirect:/Adminform"; 
}

public AdminAuthenticationController() {
	
}
 @GetMapping("/userdd")
public String useradd(Model model) {
    model.addAttribute("adminuseremployee", new Employee());
    return "newuseradd";  
}
   @PostMapping("/newuseradd")
 public String registeremployee(@ModelAttribute("employee") Employee employee, Model model) {
 	employee.setStatus("panding");
 	
 	
 	 if (employeeRepository.existsByName(employee.getName())) {
 		  model.addAttribute("message", "This email Address already used please use another email address");
 		 return "UserRegistration";


		}else {    		
     employeeRepository.save(employee);
         return "UserAuthentication";
		
 	 }
 	
  
 }
}




  


    
