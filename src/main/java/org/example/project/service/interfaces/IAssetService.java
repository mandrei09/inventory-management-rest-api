package org.example.project.service.interfaces;

import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.service.generic.IBaseService;

import java.util.List;

public interface IAssetService extends IBaseService<Asset, AssetDtoCreate, AssetDtoUpdate> {
    List<Asset> findAllByCompanyId(Long companyId);
}
