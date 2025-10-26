package com.eternalcode.core.catboy;

import com.eternalcode.core.EternalCoreApi;
import com.eternalcode.core.EternalCoreApiProvider;
import com.eternalcode.core.IntegrationBaseTest;
import com.eternalcode.core.feature.catboy.CatboyService;
import dev.rollczi.litegration.junit.LitegrationTest;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.bukkit.entity.Cat;
import org.junit.jupiter.api.Test;

class CatboyServiceIntegrationTest extends IntegrationBaseTest {

    @LitegrationTest
    void shouldMarkAndUnmarkPlayerAsCatboy() {
        EternalCoreApi api = EternalCoreApiProvider.provide();
        CatboyService catboyService = api.getCatboyService();

        joinPlayer("Rollczi", rollczi -> {
            catboyService.markAsCatboy(rollczi, Cat.Type.WHITE);
            assertThat(catboyService.isCatboy(rollczi.getUniqueId())).isTrue();
            assertThat(rollczi.getWalkSpeed()).isEqualTo(0.4F);
            assertThat(rollczi.getPassengers()).hasSize(1)
                .allMatch(entity -> entity instanceof Cat cat && cat.getCatType() == Cat.Type.WHITE);

            catboyService.changeCatboyType(rollczi, Cat.Type.BLACK);
            assertThat(rollczi.getPassengers()).hasSize(1)
                .allMatch(entity -> entity instanceof Cat cat && cat.getCatType() == Cat.Type.BLACK);

            catboyService.unmarkAsCatboy(rollczi);
            assertThat(rollczi.getWalkSpeed()).isEqualTo(0.2F);
            assertThat(rollczi.getPassengers()).isEmpty();

            assertThat(catboyService.isCatboy(rollczi.getUniqueId())).isFalse();
        });
    }

}
