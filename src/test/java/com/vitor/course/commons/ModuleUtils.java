package com.vitor.course.commons;

import com.vitor.course.domain.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ModuleUtils {

    public List<Module> createModuleList() {

        var firstModule = Module.builder()
                .moduleId(UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd"))
                .title("Method Stronger Module")
                .description("Method Stronger Module Lorem Ipsum")
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lessons(new HashSet<>())
                .build();

        return new ArrayList<>(List.of(firstModule, firstModule));

    }

    public Module createModule() {

        return Module.builder()
                .moduleId(UUID.fromString("401aea30-01bd-470f-b607-ef833eca8a70"))
                .title("Method Lazy Module")
                .description("Method Lazy Module Lorem Ipsum")
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lessons(new HashSet<>())
                .build();
    }
}
