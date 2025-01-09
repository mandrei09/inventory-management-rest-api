package org.example.project.service;

import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.Inventory;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IInventoryRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IAssetService;
import org.example.project.service.interfaces.IInventoryService;
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
    public Inventory mapToModel(InventoryDtoCreate dto) {
        return Inventory.builder()
                .company(companyRepository.findById(dto.getCompanyId()))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
                .info(trimStringOrNull(dto.getInfo()))
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
            .build();
    }

    @Override
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
    public Result<Inventory> updateFromDto(Inventory inventory, InventoryDtoUpdate dto) {
        Result<Inventory> result = new Result<>();

        if(dto.getCode() != null) {
            inventory.setCode(dto.getCode().trim());
        }
        if(dto.getName() != null) {
            inventory.setName(dto.getName().trim());
        }

        if(dto.getInfo() != null) {
            inventory.setInfo(dto.getInfo().trim());
        }

        if(dto.getStartDate() != null) {
            inventory.setStartDate(dto.getStartDate());
        }

        if(dto.getStartDate() != null) {
            inventory.setStartDate(dto.getStartDate());
        }

        if(dto.getCompanyId() != null) {
            var company = companyRepository.findById(dto.getCompanyId()).orElse(null);
            if(company != null) {
                inventory.setCompany(company);
            }
            else {
                result.entityNotFound("Company not found!");
            }
        }

        return result;
    }

    @Override
    public Long getLastInventoryIdByCompanyId(Long companyId) {
        return inventoryRepository.findByCompanyId(companyId);
    }

    @Override
    public Inventory findById(Long id) {
        return inventoryRepository.findById(id);
    }
}
