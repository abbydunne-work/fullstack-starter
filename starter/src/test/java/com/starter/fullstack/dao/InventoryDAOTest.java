package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.math.BigDecimal;
import java.util.List;
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
    Inventory inventoryOne = new Inventory();
    inventoryOne.setName(NAME);
    inventoryOne.setProductType(PRODUCT_TYPE);
    inventoryOne.setAmount(BigDecimal.ONE);
    Inventory inventoryTwo = new Inventory();
    inventoryTwo.setName(NAME);
    inventoryTwo.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventoryOne);
    this.inventoryDAO.create(inventoryTwo);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    // Asserting equal to one because in this case it would increment the amount
    Assert.assertEquals(2, actualInventory.size());
    var firstItem = actualInventory.get(0);
    Assert.assertEquals(BigDecimal.ONE, firstItem.getAmount());
  }
}
