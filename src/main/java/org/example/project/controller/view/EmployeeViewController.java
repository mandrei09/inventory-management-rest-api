package org.example.project.controller.view;

import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.dto.employee.EmployeeDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Employee;
import org.example.project.result.Result;
import org.example.project.service.CompanyService;
import org.example.project.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("view/employees")
public class EmployeeViewController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    public EmployeeViewController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        Result<?> result = employeeService.findEntities(Map.of(), null, null, null, null);
        List<Employee> employees = result.isSuccess() ? (List<Employee>) result.getObject() : List.of();
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Company> companies = (List<Company>)companyService.findEntities(Map.of(), null, null, null, null);

        List<Employee> managers = (List<Employee>)employeeService.findEntities(Map.of(), null, null, null, null);

        model.addAttribute("employee", new EmployeeDtoCreate());
        model.addAttribute("companies", companies);
        model.addAttribute("managers", managers);
        return "employees/create";
    }

    @PostMapping
    public String createEmployee(@ModelAttribute("employee") EmployeeDtoCreate dto, RedirectAttributes redirectAttrs) {
        Result<Employee> result = employeeService.create(dto);
        if (result.isSuccess()) {
            redirectAttrs.addFlashAttribute("successMessage", "Employee created successfully.");
            return "redirect:/view/employees";
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", String.join(", ", result.getMessages()));
            return "redirect:/view/employees/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        Result<Employee> result = employeeService.findById(id);
        if (!result.isSuccess()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Employee not found.");
            return "redirect:/view/employees";
        }

        List<Company> companies = (List<Company>)companyService.findEntities(Map.of(), null, null, null, null);

        List<Employee> managers = (List<Employee>)employeeService.findEntities(Map.of(), null, null, null, null);

        model.addAttribute("employeeId", result.getObject().getId());
        model.addAttribute("employee", employeeService.mapToDto(result.getObject()));
        model.addAttribute("companies", companies);
        model.addAttribute("managers", managers);
        return "employees/edit";
    }

    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute("employee") EmployeeDtoUpdate dto, RedirectAttributes redirectAttrs) {
        Result<Employee> result = employeeService.update(id, dto);
        if (result.isSuccess()) {
            redirectAttrs.addFlashAttribute("successMessage", "Employee updated successfully.");
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", String.join(", ", result.getMessages()));
        }
        return "redirect:/view/employees";
    }
}
