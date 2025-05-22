package org.example.project.controller.view;

import org.example.project.dto.company.CompanyDtoCreate;
import org.example.project.dto.company.CompanyDtoUpdate;
import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.model.Company;
import org.example.project.model.Employee;
import org.example.project.result.Result;
import org.example.project.service.CompanyService;
import org.example.project.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("view/companies")
public class CompanyViewController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public CompanyViewController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listCompanies(Model model) {
        Result<?> result = companyService.findEntities(Map.of(), null, null, null, null);
        List<Company> companies = result.isSuccess() ? (List<Company>) result.getObject() : List.of();
        model.addAttribute("companies", companies);
        return "companies/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Result<?> result = employeeService.findEntities(Map.of(), null, null, null, null);
        List<Employee> employees = result.isSuccess() ? (List<Employee>) result.getObject() : List.of();
        model.addAttribute("company", new CompanyDtoCreate());
        model.addAttribute("employees", employees);
        return "companies/create";
    }

    @PostMapping
    public String createCompany(@ModelAttribute("company") CompanyDtoCreate dto, RedirectAttributes redirectAttrs) {
        Result<Company> result = companyService.create(dto);
        if (result.isSuccess()) {
            redirectAttrs.addFlashAttribute("successMessage", "Company created successfully.");
            return "redirect:/view/companies";
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", String.join(", ", result.getMessages()));
            return "redirect:/view/companies/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        Result<Company> result = companyService.findById(id);
        if (result.isSuccess()) {
            Company company = result.getObject();
            CompanyDtoCreate dto = companyService.mapToDto(company);

            Result<?> employeesResult = employeeService.findEntities(Map.of(), null, null, null, null);
            List<Employee> employees = employeesResult.isSuccess() ? (List<Employee>) employeesResult.getObject() : List.of();

            model.addAttribute("companyId", company.getId());
            model.addAttribute("company", dto);
            model.addAttribute("employees", employees);
            return "companies/edit";
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", "Company not found.");
            return "redirect:/view/companies";
        }
    }

    @PostMapping("/{id}")
    public String updateCompany(@PathVariable Long id, @ModelAttribute("company") CompanyDtoUpdate dto, RedirectAttributes redirectAttrs) {
        Result<Company> result = companyService.update(id, dto);
        if (result.isSuccess()) {
            redirectAttrs.addFlashAttribute("successMessage", "Company updated successfully.");
            return "redirect:/view/companies";
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", String.join(", ", result.getMessages()));
            return "redirect:/view/companies/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteCompany(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        Result<Company> result = companyService.softDelete(id);
        if (result.isSuccess()) {
            redirectAttrs.addFlashAttribute("successMessage", "Company deleted successfully.");
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", String.join(", ", result.getMessages()));
        }
        return "redirect:/view/companies";
    }
}


