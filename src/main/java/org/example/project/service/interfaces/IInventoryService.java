package org.example.project.service.interfaces;

import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.Inventory;
import org.example.project.service.generic.IBaseService;
import org.springframework.data.jpa.repository.Query;

public interface IInventoryService extends IBaseService<Inventory, InventoryDtoCreate, InventoryDtoUpdate> {
}
