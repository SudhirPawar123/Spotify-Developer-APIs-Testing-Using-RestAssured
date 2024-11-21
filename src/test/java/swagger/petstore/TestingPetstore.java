package swagger.petstore;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class TestingPetstore {
    @Test
    public void createUser(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body(" {\n" +
                        "  \"id\": 111,\n" +
                        "  \"username\": \"Suresh\",\n" +
                        "  \"firstName\": \"Ram\",\n" +
                        "  \"lastName\": \"Pawar\",\n" +
                        "  \"email\": \"suresh123@gmail.com\",\n" +
                        "  \"password\": \"4567567\",\n" +
                        "  \"phone\": \"7869564328\",\n" +
                        "  \"userStatus\": 11\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/user");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getUserByName(){
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/user/Suresh");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void updateUser(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body(" {\n" +
                        "  \"id\": 111,\n" +
                        "  \"username\": \"Suresh\",\n" +
                        "  \"firstName\": \"Ram\",\n" +
                        "  \"lastName\": \"Pawar\",\n" +
                        "  \"email\": \"suresh123@gmail.com\",\n" +
                        "  \"password\": \"456777\",\n" +
                        "  \"phone\": \"7869564999\",\n" +
                        "  \"userStatus\": 11\n" +
                        "}")
                .when()
                .put("https://petstore.swagger.io/v2/user/Suresh");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


@Test
public void listOfUsersWithGivenInputArray(){
    Response response = given()
            .accept("application/json")
            .header("Content-Type", "application/json")
            .body(" [\n" +
                    "  {\n" +
                    "    \"id\": 101,\n" +
                    "    \"username\": \"Laxman\",\n" +
                    "    \"firstName\": \"Somesh\",\n" +
                    "    \"lastName\": \"Rathod\",\n" +
                    "    \"email\": \"sdfg@sdfghjk\",\n" +
                    "    \"password\": \"456549\",\n" +
                    "    \"phone\": \"4352678965\",\n" +
                    "    \"userStatus\": 0\n" +
                    "  },  {\n" +
                    "    \"id\": 102,\n" +
                    "    \"username\": \"John\",\n" +
                    "    \"firstName\": \"Smith\",\n" +
                    "    \"lastName\": \"brook\",\n" +
                    "    \"email\": \"sdfghjh@fghjhgf.com\",\n" +
                    "    \"password\": \"45676567\",\n" +
                    "    \"phone\": \"6784325678\",\n" +
                    "    \"userStatus\": 0\n" +
                    "  },  {\n" +
                    "    \"id\": 103,\n" +
                    "    \"username\": \"Lokesk\",\n" +
                    "    \"firstName\": \"Ganesh\",\n" +
                    "    \"lastName\": \"Patil\",\n" +
                    "    \"email\": \"lok123@gmail.com\",\n" +
                    "    \"password\": \"879654\",\n" +
                    "    \"phone\": \"6574984765\",\n" +
                    "    \"userStatus\": 0\n" +
                    "  }\n" +
                    "]")
            .when()
            .post("https://petstore.swagger.io/v2/user/createWithArray");
    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 200);
}

@Test
public void logsUserIntoTheSystem(){
    Response response = given()
            .queryParam("username","Lokesh")
            .queryParam("password","879654")
            .when()
            .get("https://petstore.swagger.io/v2/user/login");

    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 200);
}


    @Test
    public void logsOutCurrentLoggedInUserSession(){
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/user/logout");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void deleteUser(){
        Response response = given()
                .when()
                .delete("https://petstore.swagger.io/v2/user/John");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


@Test
public void returnsPetInventoriesByStatus(){
    Response response = given()
            .when()
            .get("https://petstore.swagger.io/v2/store/inventory");
    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 200);
}
    @Test
    public void placeAnOrderForAPet(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body(" {\n" +
                        "  \"id\": 110,\n" +
                        "  \"petId\": 1001,\n" +
                        "  \"quantity\": 2,\n" +
                        "  \"shipDate\": \"2024-11-15T12:10:42.285Z\",\n" +
                        "  \"status\": \"placed\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/store/order");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void deletePurchaseOrderByID(){
        Response response = given()
                .when()
                .delete("https://petstore.swagger.io/v2/store/order/110");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void findPurchaseOrderByID(){
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/store/order/1");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void addANewPetToTheStore(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 100,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 111,\n" +
                        "    \"name\": \"militry\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 222,\n" +
                        "      \"name\": \"#doggie\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/pet");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
public void findPetById(){
    Response response = given()
            .when()
            .get("https://petstore.swagger.io/v2/pet/100");

    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 200);
}

@Test
public void findsPetsByStatus(){
    Response response = given()
            .when()
            .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available");

    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 200);
}

@Test
public void updateAnExistingPet(){
    Response response = given()
            .accept("application/json")
            .header("Content-Type", "application/json")
            .body("{\n" +
                    "  \"id\": 100,\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 0,\n" +
                    "    \"name\": \"militry\"\n" +
                    "  },\n" +
                    "  \"name\": \"doggie\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"string\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": 0,\n" +
                    "      \"name\": \"string\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"available\"\n" +
                    "}")
            .when()
            .put("https://petstore.swagger.io/v2/pet");
    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 200);
}

    @Test
    public void updatesAPetInTheStoreWithFormData(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .queryParam("petId","28")
                .queryParam("name","doggie")
                .queryParam("status","available")
                .body(" {\n" +
                        "        \"id\": 28,\n" +
                        "        \"category\": {\n" +
                        "            \"id\": 0,\n" +
                        "            \"name\": \"CBI\"\n" +
                        "        },\n" +
                        "        \"name\": \"doggie\",\n" +
                        "        \"photoUrls\": [\n" +
                        "            \"string\"\n" +
                        "        ],\n" +
                        "        \"tags\": [\n" +
                        "            {\n" +
                        "                \"id\": 0,\n" +
                        "                \"name\": \"string\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"status\": \"available\"\n" +
                        "    }")
                .when()
                .put("https://petstore.swagger.io/v2/pet");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void uploadImage() {
        File file = new File("C:\\Users\\dell\\Downloads\\pet101.jpeg");

        Response response = given()
                .accept("application/json")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", file)
                .queryParam("petId", "28")
                .when()
                .post("https://petstore.swagger.io/v2/pet/28/uploadImage"); // Updated endpoint to include /uploadImage if applicable
            response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void deletesAPet(){
        Response response = given()
                .when()
                .delete("https://petstore.swagger.io/v2/pet/100");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
