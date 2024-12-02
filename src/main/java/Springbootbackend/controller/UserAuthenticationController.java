package Springbootbackend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Springbootbackend.repositoty.EmployeeRepository;
import Springbootbackend.useraccessmanagementsystem.Employee;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
public class UserAuthenticationController {
	@Autowired
   private EmployeeRepository employeeRepository;

  @GetMapping("/useraccessmanagementsystem")
 
 public String useraccessmanagementsystem() {
	  return "useraccessmanagementsystem";
  }
    
   
    @GetMapping("/registerform")
    public String getregister(Model model) {
    	model.addAttribute("employee", new Employee());
    	return "UserRegistration";
    	
    }
    
    @PostMapping("/register")
    public String registeremployee(@ModelAttribute("employee") Employee employee, Model model) {
    	employee.setStatus("Pending");
    	
    	
    	 if (employeeRepository.existsByName(employee.getName())) {
    		  model.addAttribute("message", "This email Address already used please use another email address");
    		 return "UserRegistration";

 
		}else {    		
        employeeRepository.save(employee);
        model.addAttribute("message", "Register is successfully completed");
            return "UserAuthentication";
		
    	 }
    	
     
    }
    @GetMapping("/loginform")
    public String getLogin(Model model) {
        model.addAttribute("employee",new Employee());
        return "UserAuthentication"; 
    }
  
    @PostMapping("/loginpage")
    public String Studentlogin(@ModelAttribute("employee") Employee employee, Model model) {
        Optional<Employee> optional = employeeRepository.findByName(employee.getName());
        
        if (optional.isPresent()) {
            Employee existingStudent = optional.get();
            
            if (existingStudent.getPassword().equals(employee.getPassword())) {
                model.addAttribute("message", "Login successful!");
                
                 if (!"Approved".equalsIgnoreCase(existingStudent.getStatus())) {
                	 model.addAttribute("error", "Login not allowed. Manager approval pending.");
                	 return "UserAuthentication";
					
				}
                Employee employee2 = optional.get();
                model.addAttribute("user", employee2);
                return "home";
                
            }
           
            else {
                model.addAttribute("error", "Invalid password.");
                return "UserAuthentication";
            }
        } else {
            model.addAttribute("error", "Email not registered.");
            return "UserRegistration";
        }
    }
    
    @GetMapping("/updateemployee")
    public String getupdateLogin(Model model) {
        model.addAttribute("userupdateemployee",new Employee());
        return "Userupdatedetails"; 
    }
    
    
    @PostMapping("/userupdateemplyee")
    public String getupdate(@ModelAttribute("userupdateemployee") Employee employee, Model model) {
       Optional<Employee> optionalemployee = employeeRepository.findByName(employee.getName());
        
      
        if (optionalemployee.isEmpty()) {
            model.addAttribute("error", "No student found with the email " + employee.getName());
            return "Userupdatedetails";
        }
       
        
        Employee existingemployee = optionalemployee.get();
        existingemployee.setName(employee.getName());
        existingemployee.setPassword(employee.getPassword());
       
        

        employeeRepository.save(existingemployee);
        model.addAttribute("updatestudent", existingemployee);
        model.addAttribute("message", "Employee details updated successfully!");
        
         return "userupdate";
         
    }
    
    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false); 
        if (session != null) {
            session.invalidate(); 
            model.addAttribute("message", "Logout successful.");
        }
        return "redirect:/loginform"; 
    }
   
 
    
   
    
}
   
    
