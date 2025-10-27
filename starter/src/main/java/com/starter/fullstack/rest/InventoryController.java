package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  /**
   * Save Inventory.
   * @param inventory inventory.
   * @return Inventory.
   */

  @PostMapping
  public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  /**
  * Delete Inventory By Ids.
  * @param ids ids.
  */
  @DeleteMapping
  public void deleteInventoryById(@RequestBody List<String> ids) {
    Assert.notEmpty(ids, "Inventory Ids were not provided");
    this.inventoryDAO.delete(ids);
  }
}

