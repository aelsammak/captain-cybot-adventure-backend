package captain.cybot.adventure.backend.component;

import captain.cybot.adventure.backend.constants.COSMETICS;
import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.model.user.Cosmetic;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.repository.user.CosmeticRepository;
import captain.cybot.adventure.backend.repository.user.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DBInit implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;
    private CosmeticRepository cosmeticRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initDefaultRoles();
        initDefaultCosmetics();
    }

    private void initDefaultRoles() {
        Role userRole = roleRepository.findByName(ROLES.ROLE_USER.toString());
        Role adminRole = roleRepository.findByName(ROLES.ROLE_ADMIN.toString());
        if (userRole == null) {
            userRole = new Role(1L, ROLES.ROLE_USER.toString());
            roleRepository.save(userRole);
        }
        if (adminRole == null) {
            adminRole = new Role(2L, ROLES.ROLE_ADMIN.toString());
            roleRepository.save(adminRole);
        }
    }

    private void initDefaultCosmetics() {
        Cosmetic defaultShield = cosmeticRepository.findByFileName(COSMETICS.DEFAULT_SHIELD.toString());
        Cosmetic world1Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_1_SHIELD.toString());
        Cosmetic world2Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_2_SHIELD.toString());
        Cosmetic world3Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_3_SHIELD.toString());
        Cosmetic world4Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_4_SHIELD.toString());

        if (defaultShield == null) {
            defaultShield = new Cosmetic(1L, COSMETICS.DEFAULT_SHIELD.toString(), 0);
            cosmeticRepository.save(defaultShield);
        }

        if (world1Shield == null) {
            world1Shield = new Cosmetic(2L, COSMETICS.WORLD_1_SHIELD.toString(), 1);
            cosmeticRepository.save(world1Shield);
        }

        if (world2Shield == null) {
            world2Shield = new Cosmetic(3L, COSMETICS.WORLD_2_SHIELD.toString(), 2);
            cosmeticRepository.save(world2Shield);
        }

        if (world3Shield == null) {
            world3Shield = new Cosmetic(4L, COSMETICS.WORLD_3_SHIELD.toString(), 3);
            cosmeticRepository.save(world3Shield);
        }

        if (world4Shield == null) {
            world4Shield = new Cosmetic(5L, COSMETICS.WORLD_4_SHIELD.toString(), 4);
            cosmeticRepository.save(world4Shield);
        }
    }
}
