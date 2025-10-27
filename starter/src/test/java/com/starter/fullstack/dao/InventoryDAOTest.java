package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

    /**
     * Test Create method.
     */
  @Test
  public void create() {
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(0, actualInventory.size());
    Inventory inventoryOne = new Inventory();
    inventoryOne.setName(NAME);
    inventoryOne.setProductType(PRODUCT_TYPE);
    var result = this.inventoryDAO.create(inventoryOne);
    Assert.assertNotNull(result);
    actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(1, actualInventory.size());

    result.setName("UPDATE");
    result = this.inventoryDAO.create(result);
    Assert.assertNotNull(result);
    actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(1, actualInventory.size());
    var newName = actualInventory.get(0).getName();
    Assert.assertEquals("UPDATE", newName);

  }

  /**
   * Test Delete by Ids method.
   */
  @Test
  public void delete() {
    Inventory inventoryOne = new Inventory();
    Inventory inventoryTwo = new Inventory();
    inventoryOne.setName(NAME);
    inventoryOne.setProductType(PRODUCT_TYPE);
    inventoryTwo.setName(NAME);
    inventoryTwo.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventoryOne);
    this.mongoTemplate.save(inventoryTwo);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(2, actualInventory.size());
    List<String> idsToDelete = actualInventory.stream().map(Inventory::getId).collect(Collectors.toList());
    this.inventoryDAO.delete(idsToDelete);
    actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(0, actualInventory.size());

  }
}
