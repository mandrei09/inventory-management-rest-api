package org.example.project.service.interfaces;

import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.dto.company.CompanyDtoCreate;
import org.example.project.dto.company.CompanyDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.model.Company;
import org.example.project.service.generic.IBaseService;

public interface ICompanyService extends IBaseService<Company, CompanyDtoCreate, CompanyDtoUpdate> {

}
