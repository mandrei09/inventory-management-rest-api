package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Inventory;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IInventoryRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IInventoryService;
import org.example.project.utils.StatusMessages;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends BaseService<Inventory, InventoryDtoCreate, InventoryDtoUpdate> implements IInventoryService {

    private final IInventoryRepository inventoryRepository;
    private final ICompanyRepository companyRepository;

    public InventoryService(IInventoryRepository inventoryRepository, ICompanyRepository companyRepository) {
        super(inventoryRepository);
        this.inventoryRepository = inventoryRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Operation(
            summary = "Map InventoryDtoCreate to Inventory",
            description = "Converts an InventoryDtoCreate object into an Inventory entity.",
            responses = @ApiResponse(responseCode = "200", description = "Successfully mapped DTO to entity")
    )
    public Result<Inventory> mapToModel(InventoryDtoCreate dto) {
        Result<Inventory> result = new Result<>();

        Company company = companyRepository.findById(dto.getCompanyId());

        if (company == null) {
            return result.entityNotFound(dto.getCompanyId(), StatusMessages.COMPANY_NOT_FOUND);
        }

        return result.entityFound(
            Inventory.builder()
                    .company(company)
                    .code(dto.getCode().trim())
                    .name(dto.getName().trim())
                    .info(trimStringOrNull(dto.getInfo()))
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .build()
        );
    }

    @Override
    @Operation(
            summary = "Map Inventory to InventoryDtoCreate",
            description = "Converts an Inventory entity into an InventoryDtoCreate object.",
            responses = @ApiResponse(responseCode = "200", description = "Successfully mapped entity to DTO")
    )
    public InventoryDtoCreate mapToDto(Inventory inventory) {
        return InventoryDtoCreate.builder()
                .companyId(inventory.getCompany() != null ? inventory.getCompany().getId() : null)
                .code(inventory.getCode().trim())
                .name(inventory.getName().trim())
                .info(trimStringOrNull(inventory.getInfo()))
                .startDate(inventory.getStartDate())
                .endDate(inventory.getEndDate())
                .build();
    }

    @Override
    @Operation(
            summary = "Update Inventory from DTO",
            description = "Updates an existing Inventory entity using an InventoryDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the entity"),
                    @ApiResponse(responseCode = "404", description = StatusMessages.COMPANY_NOT_FOUND)
            }
    )
    public Result<Inventory> updateFromDto(Inventory inventory, InventoryDtoUpdate dto) {
        Result<Inventory> result = new Result<>();

        if (dto.getCode() != null) {
            inventory.setCode(dto.getCode().trim());
        }
        if (dto.getName() != null) {
            inventory.setName(dto.getName().trim());
        }
        if (dto.getInfo() != null) {
            inventory.setInfo(dto.getInfo().trim());
        }
        if (dto.getStartDate() != null) {
            inventory.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            inventory.setEndDate(dto.getEndDate());
        }

        if (dto.getCompanyId() != null) {
            var company = companyRepository.findById(dto.getCompanyId());
            if (company != null) {
                inventory.setCompany(company);
            } else {
                result.entityNotFound(dto.getCompanyId(), StatusMessages.COMPANY_NOT_FOUND);
            }
        }

        return result;
    }
}
