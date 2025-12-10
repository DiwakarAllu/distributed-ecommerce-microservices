package com.diwakarallu.ecommerce.product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import static org.hamcrest.Matchers.*;

import java.util.List;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
		System.setProperty("MONGO_DB_URL", mongoDBContainer.getReplicaSetUrl());
	}

	// -------------------------------------------------------
	// TEST: CONTEXT LOADS
	// -------------------------------------------------------
	@Test
	void contextLoads() {
	}

	// -------------------------------------------------------
	// TEST: CREATE PRODUCT
	// -------------------------------------------------------
	@Test
	void testCreateProduct() {
		String requestBody = """
				{
				  "name": "Phone",
				  "description": "Smartphone",
				  "skuCode": "PH123",
				  "price": 999
				}
				""";

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.body("name", equalTo("Phone"))
				.body("skuCode", equalTo("PH123"));
	}

	// -------------------------------------------------------
	// TEST: GET ALL PRODUCTS (AFTER CREATION)
	// -------------------------------------------------------
	@Test
	void testGetAllProducts() {
		RestAssured.given()
				.when()
				.get("/api/products")
				.then()
				.statusCode(200)
				.body("$", isA(List.class));
	}

	// -------------------------------------------------------
	// TEST: CREATE PRODUCT + GET BY ID
	// -------------------------------------------------------
	@Test
	void testGetProductById() {
		String id = RestAssured.given()
				.contentType(ContentType.JSON)
				.body("""
						{
						  "name": "Laptop",
						  "description": "Gaming",
						  "skuCode": "LP100",
						  "price": 1500
						}
						""")
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.extract()
				.path("id");

		RestAssured.given()
				.when()
				.get("/api/products/" + id)
				.then()
				.statusCode(200)
				.body("name", equalTo("Laptop"))
				.body("skuCode", equalTo("LP100"));
	}

	// -------------------------------------------------------
	// TEST: UPDATE EXISTING PRODUCT
	// -------------------------------------------------------
	@Test
	void testUpdateProduct() {
		// First create product
		String id = RestAssured.given()
				.contentType(ContentType.JSON)
				.body("""
						{
						  "name": "Watch",
						  "description": "Digital",
						  "skuCode": "WT12",
						  "price": 200
						}
						""")
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.extract()
				.path("id");

		// Update product
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body("""
						{
						  "name": "Watch Updated",
						  "description": "Digital V2",
						  "skuCode": "WT12",
						  "price": 300
						}
						""")
				.when()
				.put("/api/products/" + id)
				.then()
				.statusCode(200)
				.body("name", equalTo("Watch Updated"))
				.body("price", equalTo(300));
	}

	// -------------------------------------------------------
	// TEST: DELETE PRODUCT
	// -------------------------------------------------------
	@Test
	void testDeleteProduct() {
		String id = RestAssured.given()
				.contentType(ContentType.JSON)
				.body("""
						{
						  "name": "TV",
						  "description": "4K",
						  "skuCode": "TV40",
						  "price": 2200
						}
						""")
				.when()
				.post("/api/products")
				.then()
				.extract()
				.path("id");

		RestAssured.given()
				.when()
				.delete("/api/products/" + id)
				.then()
				.statusCode(204);

		// Verify it's gone
		RestAssured.given()
				.when()
				.get("/api/products/" + id)
				.then()
				.statusCode(404);
	}
}
