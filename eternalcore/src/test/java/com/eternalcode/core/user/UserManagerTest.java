package com.eternalcode.core.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserManagerTest {

    @Test
    void testUsersCreate() {
        UserManager manager = new UserManager();

        assertEquals(0, manager.getUsers().size());

        manager.create(UUID.randomUUID(), "Piotr");
        assertEquals(1, manager.getUsers().size());

        manager.create(UUID.randomUUID(), "Igor");
        manager.create(UUID.randomUUID(), "Norbert");
        manager.create(UUID.randomUUID(), "Martin");
        assertEquals(3, manager.getUsers().size());
    }

    @Test
    void testGetUsers() {
        UserManager manager = new UserManager();

        assertEquals(0, manager.getUsers().size());

        UUID uuid = UUID.randomUUID();
        User user = manager.getOrCreate(uuid, "Paweł");

        assertEquals(1, manager.getUsers().size());
        assertTrue(manager.getUsers().contains(user));
    }

    @Test
    void testGetUser() {
        UserManager manager = new UserManager();

        UUID uuid = UUID.randomUUID();
        User user = manager.getOrCreate(uuid, "Krzysztof");

        assertTrue(manager.getUsers().contains(user));
        assertEquals(1, manager.getUsers().size());
    }

    @Test
    void testGetOrCreate() {
        UserManager manager = new UserManager();

        UUID uuid = UUID.randomUUID();
        User user = manager.getOrCreate(uuid, "Michał");

        assertEquals(user, manager.getOrCreate(uuid, "Michał"));
        assertEquals(1, manager.getUsers().size());
        assertTrue(manager.getUsers().contains(user));
    }

}
