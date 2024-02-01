package com.vitor.course.mapper;

import com.vitor.course.domain.Module;
import com.vitor.course.request.ModulePostRequest;
import com.vitor.course.request.ModulePutRequest;
import com.vitor.course.response.ModuleGetResponse;
import com.vitor.course.response.ModulePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModuleMapper {


     Module toModule(ModulePostRequest request);

    ModulePostResponse toModulePostResponse(Module module);

    List<ModuleGetResponse> toModuleGetResponse(List<Module> module);

    ModuleGetResponse toModuleGetResponse(Module module);

    Module toModule(ModulePutRequest request);
}
