package com.eternalcode.core.user;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(4, manager.getUsers().size());
    }

    @Test
    void testCreateSameUser() {
        UserManager manager = new UserManager();

        manager.create(UUID.randomUUID(), "Piotr");
        assertThrows(IllegalStateException.class, () -> manager.create(UUID.randomUUID(), "Piotr"));

        UUID uuid = UUID.randomUUID();
        manager.create(uuid, "Rollczi");
        assertThrows(IllegalStateException.class, () -> manager.create(uuid, "Lucky"));
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

        assertEquals(0, manager.getUsers().size());

        UUID uuid = UUID.randomUUID();
        manager.getOrCreate(uuid, "Adrian");

        assertEquals(1, manager.getUsers().size());
        assertTrue(manager.getUser(uuid).isPresent());
    }

    @Test
    void testGetOrCreate() {
        UserManager manager = new UserManager();

        UUID uuid = UUID.randomUUID();
        User user = manager.getOrCreate(uuid, "Michał");

        assertEquals(user, manager.getOrCreate(uuid, "Michał"));
        assertEquals(1, manager.getUsers().size());
        assertTrue(manager.getUsers().contains(user));

        assertEquals(user, manager.getOrCreate(uuid, "Michał"));
    }

}
