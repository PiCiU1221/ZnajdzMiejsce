package com.zut.znajdzmiejsce.script;

import com.zut.znajdzmiejsce.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetupScript implements CommandLineRunner {

    private final AdministratorService administratorService;

    @Override
    public void run(String... args) {
        //administratorService.createAdmin("admin@admin.com", "password");
    }
}
