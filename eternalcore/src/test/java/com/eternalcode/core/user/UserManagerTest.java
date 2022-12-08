package com.eternalcode.core.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserManagerTest {
    @Test
    void testUsersCreate() {
        UserManager manager = new UserManager();

        assertEquals(0, manager.getUsers().size());

        manager.create(UUID.randomUUID(), "Piotr").orNull();
        assertEquals(1, manager.getUsers().size());

        manager.create(UUID.randomUUID(), "Piotr").orNull();
        manager.create(UUID.randomUUID(), "Norbert").orNull();
        manager.create(UUID.randomUUID(), "Martin").orNull();
        assertEquals(3, manager.getUsers().size());
    }

    @Test
    void testGetUsers() {
        UserManager manager = new UserManager();

        assertEquals(0, manager.getUsers().size());

        User user = manager.create(UUID.randomUUID(), "Paweł").orNull();

        assertEquals(1, manager.getUsers().size());
        assertTrue(manager.getUsers().contains(user));
    }

    @Test
    void testGetUser() {
        UserManager manager = new UserManager();

        UUID uuid = UUID.randomUUID();
        User user = manager.create(uuid, "Krzysztof").orNull();

        assertEquals(user, manager.getUser(uuid).orNull());
        assertEquals(user, manager.getUser("Krzysztof").orNull());
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
