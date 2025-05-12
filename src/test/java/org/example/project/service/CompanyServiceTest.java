package org.example.project.service;

import org.example.project.dto.company.CompanyDtoCreate;
import org.example.project.dto.company.CompanyDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Employee;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IEmployeeRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("h2")
public class CompanyServiceTest {

    @Mock
    private ICompanyRepository companyRepository;

    @Mock
    private IEmployeeRepository employeeRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void testMapToModelWithSuccess() {
        var companyDto = CompanyDtoCreate.builder()
                .code("C001")
                .name("Company 1")
                .managerId(1L)
                .build();

        var manager = new Employee();
        manager.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(manager);
        when(companyRepository.countAllByManagerId(manager.getId())).thenReturn(0);

        Result<Company> result = companyService.mapToModel(companyDto);

        assertEquals(true, result.isSuccess());
        assertEquals(companyDto.getCode(), result.getObject().getCode());
        assertEquals(companyDto.getName(), result.getObject().getName());
        assertEquals(manager, result.getObject().getManager());
        verify(employeeRepository).findById(1L);
        verify(companyRepository).countAllByManagerId(manager.getId());
    }

    @Test
    void testMapToModelWithManagerNotFound() {
        var companyDto = CompanyDtoCreate.builder()
                .code("C001")
                .name("Company 1")
                .managerId(999L)
                .build();

        when(employeeRepository.findById(999L)).thenReturn(null);

        Result<Company> result = companyService.mapToModel(companyDto);

        assertEquals(false, result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.MANAGER_NOT_FOUND), result.getMessages().get(0));
        verify(employeeRepository).findById(999L);
    }

    @Test
    void testMapToDtoWithSuccess() {
        var company = new Company();
        company.setCode("C001");
        company.setName("Company 1");
        var manager = new Employee();
        manager.setId(1L);
        company.setManager(manager);

        CompanyDtoCreate resultDto = companyService.mapToDto(company);

        assertEquals(company.getCode(), resultDto.getCode());
        assertEquals(company.getName(), resultDto.getName());
        assertEquals(manager.getId(), resultDto.getManagerId());
    }

    @Test
    void testUpdateFromDtoWithSuccess() {
        var company = new Company();
        company.setCode("C001");
        company.setName("Company 1");

        var companyDto = new CompanyDtoUpdate();
        companyDto.setCode("C002");
        companyDto.setName("Updated Company");
        companyDto.setManagerId(1L);

        var manager = new Employee();
        manager.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(manager);

        Result<Company> result = companyService.updateFromDto(company, companyDto);

        assertEquals(true, result.isSuccess());
        assertEquals("C002", company.getCode());
        assertEquals("Updated Company", company.getName());
        assertEquals(manager, company.getManager());
        verify(employeeRepository).findById(1L);
    }

    @Test
    void testUpdateFromDtoWithManagerNotFound() {
        var company = new Company();
        company.setCode("C001");
        company.setName("Company 1");

        var companyDto = new CompanyDtoUpdate();
        companyDto.setCode("C002");
        companyDto.setName("Updated Company");
        companyDto.setManagerId(999L);

        when(employeeRepository.findById(999L)).thenReturn(null);

        Result<Company> result = companyService.updateFromDto(company, companyDto);

        assertEquals(false, result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.MANAGER_NOT_FOUND), result.getMessages().get(0));
    }
}
