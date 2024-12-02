package Springbootbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Springbootbackend.repositoty.EmployeeRepository;
import Springbootbackend.repositoty.ManagerRepository;
import Springbootbackend.useraccessmanagementsystem.Employee;
import Springbootbackend.useraccessmanagementsystem.Manager;

@Controller
public class ManagerAuthenticationController {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/managerform")
    public String getadminlogin(Model model) {
        model.addAttribute("manager", new Manager());
        return "Manager_login";
    }

    @PostMapping("/managerloginpage")
    public String managerLogin(@ModelAttribute("manager") Manager manager, Model model) {
        Optional<Manager> optional = managerRepository.findByName(manager.getName());

        if (optional.isPresent()) {
            Manager existingManager = optional.get();
            if (existingManager.getPassword().equals(manager.getPassword())) {
                model.addAttribute("message", "Login successful!");
                List<Employee> pending = employeeRepository.findByStatus("Pending");
                model.addAttribute("pendingrequest", pending);

                List<Employee> approved = employeeRepository.findByStatus("Approved");
                model.addAttribute("approveemployee", approved);

                List<Employee> rejected = employeeRepository.findByStatus("Rejected");
                model.addAttribute("rejectedemployee", rejected);

                return "managerhomepage";
            } else {
                model.addAttribute("error", "Invalid password.");
            }
        } else {
            model.addAttribute("error", "Email not registered.");
        }
        return "managerloginpage";
    }
    

    @GetMapping("/approve")
    public String approveEmployee(@RequestParam("id") Long id, Model model) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {
            Employee employee = optional.get();
            employee.setStatus("Approved");
            employeeRepository.save(employee);

            List<Employee> approved = employeeRepository.findByStatus("Approved");
            model.addAttribute("approveemployee", approved);

            return "approvalSuccess";
        }
        model.addAttribute("error", "Employee not found.");
        return "managerhomepage";
    }

    @GetMapping("/reject")
    public String rejectEmployee(@RequestParam("id") Long id, Model model) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {
            Employee employee = optional.get();
            employee.setStatus("Rejected");
            employeeRepository.save(employee);

            List<Employee> rejected = employeeRepository.findByStatus("Rejected");
            model.addAttribute("rejectedemployee", rejected);

            return "rejectionSuccess";
        }
        model.addAttribute("error", "Employee not found.");
        return "managerhomepage";
    }
}
