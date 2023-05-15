package ro.bogdansoftware.inventory;

import org.springframework.data.repository.CrudRepository;

public interface IInventoryRepository extends CrudRepository<Inventory, String> {

}
