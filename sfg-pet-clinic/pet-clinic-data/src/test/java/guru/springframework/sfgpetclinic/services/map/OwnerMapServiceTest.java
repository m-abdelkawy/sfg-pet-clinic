package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    private final long ownerId = 1L;
    private final String ownerFirstName = "Ahmed";
    private final String ownerLastName = "Ali";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(Owner.builder().id(ownerId).firstName(ownerFirstName).lastName(ownerLastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner1 = ownerMapService.findById(1L);

        assertEquals(ownerFirstName, owner1.getFirstName());
    }

    @Test
    void save() {
        Owner owner2 = Owner.builder().id(2L).firstName("Mohammed").lastName("Hammam").build();
        ownerMapService.save(owner2);

        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(2, ownerSet.size());
    }

    @Test
    void saveExistingId() {
        long id = 2L;
        Owner owner2 = Owner.builder().id(id).firstName("Mohammed").lastName("Hammam").build();
        Owner savedOwner = ownerMapService.save(owner2);

        assertEquals(id, savedOwner.getId());
    }

    void saveNoId() {

        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner savedOwner = ownerMapService.findByLastName(ownerLastName);

        assertNotNull(savedOwner);
        assertEquals(ownerId, savedOwner.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner savedOwner = ownerMapService.findByLastName("foo");

        assertNull(savedOwner);
    }
}