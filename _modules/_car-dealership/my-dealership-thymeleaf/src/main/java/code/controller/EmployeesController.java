package code.controller;

import code.infrastructure.database.entity.EmployeeEntity;
import code.infrastructure.database.repositories.EmployeeRepository;
import code.infrastructure.database.repositories.jpa.EmployeeJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeesController {

    private final EmployeeJpaRepository employeeRepository;

    @GetMapping
    public String employees(Model model) {
        List<EmployeeEntity> list = employeeRepository.findAll();
        model.addAttribute("employees", list);
        return "employees";
    }

    @PostMapping("/add")
    public String addEmployee(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "salary") String salary
    ) {
        EmployeeEntity newEmp = EmployeeEntity.builder()
                .name(name)
                .surname(surname)
                .salary(new BigDecimal(salary))
                .build();
        employeeRepository.save(newEmp);
        return "redirect:/employees";
    }

    @GetMapping("/show/{employeeId}")
    public String showEmployeeDetails(
            @PathVariable Integer employeeId,
            Model model
    ) {
        EmployeeEntity employeeEntity = employeeRepository.findById((employeeId))
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("employee", employeeEntity);
        return "employeeDetails";
    }


}