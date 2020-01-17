package tests.api.stepdefinitions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import test.api.orderstates.enums.OrderStatuses;

public class MicroServicesAPIStepDefs {

	@Given("Take Order Requests at Stops Latitue and Longitude")
	public void take_Order_Request() throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("Orders.json"));
			JSONObject jsonObject = (JSONObject) obj;
			Response respone = SerenityRest.given().header("Content-Type", "application/json")
					.body(jsonObject.toString()).post("	http://localhost:51544/v1/orders");
			JsonPath jsonPathEvaluator = respone.jsonPath();
			List<String> drivingMeters = jsonPathEvaluator.getList("drivingDistancesInMeters");
			HashMap<String, String> fareDetails = jsonPathEvaluator.get("fare");
			String orderID = (String) Serenity.getCurrentSession().put("orderID", jsonPathEvaluator.getString("id"));
			Assert.assertEquals("Respone for Place Order API is different", 201, respone.getStatusCode());
			Assert.assertFalse("Order ID is not displayed in the API repsone",
					jsonPathEvaluator.getString("id").isEmpty());
			Assert.assertFalse("Driving Meters Data is not empty in the API repsone", drivingMeters.isEmpty());
			Assert.assertFalse("Fare Details Data is not empty in the API repsone", fareDetails.isEmpty());
			Assert.assertFalse("Amount Value in the response is not displayed", fareDetails.get("amount").isEmpty());
			Assert.assertFalse("Currency Value in the response is not displayed",
					fareDetails.get("currency").isEmpty());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Given("Take Future Order Requests at Stops Latitue and Longitude")
	public void take_Future_Order_Request() throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("FutureOrders.json"));
			JSONObject jsonObject = (JSONObject) obj;
			Response respone = SerenityRest.given().header("Content-Type", "application/json")
					.body(jsonObject.toString()).post("	http://localhost:51544/v1/orders");
			JsonPath jsonPathEvaluator = respone.jsonPath();
			List<String> drivingMeters = jsonPathEvaluator.getList("drivingDistancesInMeters");
			System.out.println(drivingMeters.size());
			HashMap<String, String> fareDetails = jsonPathEvaluator.get("fare");
			String orderID = (String) Serenity.getCurrentSession().put("orderID", jsonPathEvaluator.getString("id"));
			Assert.assertEquals("Respone for Place Order API is different", 201, respone.getStatusCode());
			Assert.assertFalse("Order ID is not displayed in the API repsone",
					jsonPathEvaluator.getString("id").isEmpty());
			Assert.assertFalse("Driving Meters Data is not empty in the API repsone", drivingMeters.isEmpty());
			Assert.assertFalse("Fare Details Data is not empty in the API repsone", fareDetails.isEmpty());
			Assert.assertFalse("Amount Value in the response is not displayed", fareDetails.get("amount").isEmpty());
			Assert.assertFalse("Currency Value in the response is not displayed",
					fareDetails.get("currency").isEmpty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Given("Take Past Order Requests at Stops Latitue and Longitude")
	public void take_Past_Order_Request() throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("PastOrder.json"));
			JSONObject jsonObject = (JSONObject) obj;
			Response respone = SerenityRest.given().header("Content-Type", "application/json")
					.body(jsonObject.toString()).post("	http://localhost:51544/v1/orders");
			JsonPath jsonPathEvaluator = respone.jsonPath();
			Assert.assertEquals("Respone for Past Order is different", 400, respone.getStatusCode());
			Assert.assertEquals("Respone Message for Past Order is different",
					"field orderAt is behind the present time", jsonPathEvaluator.getString("message"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Given("Verify Order Requests for Past Time")
	public void take_Before_Time_Order_Request() throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("PastOrder.json"));
			JSONObject jsonObject = (JSONObject) obj;
			Response respone = SerenityRest.given().header("Content-Type", "application/json")
					.body(jsonObject.toString()).post("	http://localhost:51544/v1/orders");
			JsonPath jsonPathEvaluator = respone.jsonPath();
			Assert.assertEquals("Respone for Place Order API for Past Time is different", 400, respone.getStatusCode());
			Assert.assertEquals("Message for Place Order API for Past Time is different",
					"field orderAt is behind the present time", jsonPathEvaluator.getString("message"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Then("Verify the Placed Order Details")
	public void verify_Placed_Order() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.get("http://localhost:51544/v1/orders/" + "" + OrderId + "");
		JsonPath jsonPathEvaluator = response.jsonPath();
		List<String> drivingMeters = jsonPathEvaluator.getList("drivingDistancesInMeters");
		HashMap<String, String> fareDetails = jsonPathEvaluator.get("fare");
		Assert.assertEquals("Respone for Get Order API is different", 200, response.getStatusCode());
		Assert.assertEquals("Order ID displayed in Get API call is different", OrderId,
				jsonPathEvaluator.getString("id"));
		Assert.assertEquals("Order Status for Newly Placed Order is different", OrderStatuses.ASSIGNING.toString(),
				jsonPathEvaluator.getString("status"));
		Assert.assertFalse("Created Time for Newly Placed Order is empty",
				jsonPathEvaluator.getString("createdTime").isEmpty());
		Assert.assertFalse("Order Date Time for Newly Placed Order is empty",
				jsonPathEvaluator.getString("orderDateTime").isEmpty());
		Assert.assertFalse("Driving Meters Data is not empty in the API repsone", drivingMeters.isEmpty());
		Assert.assertFalse("Fare Details Data is not empty in the API repsone", fareDetails.isEmpty());
		Assert.assertFalse("Amount Value in the response is not displayed", fareDetails.get("amount").isEmpty());
		Assert.assertFalse("Currency Value in the response is not displayed", fareDetails.get("currency").isEmpty());

	}

	@Then("Drive to Take the Order EndPoint")
	public void take_Order_EndPoint() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderId + "" + "/take");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for  Take Order PUT API is different", 200, response.getStatusCode());
		Assert.assertEquals("Order ID displayed in Put Take Order API  is different", OrderId,
				jsonPathEvaluator.getString("id"));
		Assert.assertEquals("Order Status for Taken Order is different", OrderStatuses.ONGOING.toString(),
				jsonPathEvaluator.getString("status"));
		Assert.assertFalse("Ongoing Time is not displayed in Take Order PUT API repsone",
				jsonPathEvaluator.getString("ongoingTime").isEmpty());
	}

	@Then("Drive to Complete the Order")
	public void complete_Order_EndPoint() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderId + "" + "/complete");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for  Complete Order PUT API is different", 200, response.getStatusCode());
		Assert.assertEquals("Order ID displayed in Put Complete Order API  is different", OrderId,
				jsonPathEvaluator.getString("id"));
		Assert.assertEquals("Order Status for Complete Order is different", OrderStatuses.COMPLETED.toString(),
				jsonPathEvaluator.getString("status"));
		Assert.assertFalse("Completed Time is not displayed in Take Order PUT API repsone",
				jsonPathEvaluator.getString("completedAt").isEmpty());
	}

	@Then("Cancel the Order Details")
	public void cancel_Order_EndPoint() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderId + "" + "/cancel");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for  Complete Cancel PUT API is different", 200, response.getStatusCode());
		Assert.assertEquals("Order ID displayed in Put Cancel Order API  is different", OrderId,
				jsonPathEvaluator.getString("id"));
		Assert.assertEquals("Order Status for Cancel Order is different", OrderStatuses.CANCELLED.toString(),
				jsonPathEvaluator.getString("status"));
		Assert.assertFalse("Cancelled Time is not displayed in Cancel Order PUT API repsone",
				jsonPathEvaluator.getString("cancelledAt").isEmpty());
	}

	@Then("Verify the Invalid Order Details")
	public void verify_Invalid_Order(DataTable table) {
		List<List<String>> OrderData = table.raw();
		System.out.println(OrderData.get(1).get(0));
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.get("http://localhost:51544/v1/orders/" + "" + OrderData.get(1).get(0) + "");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Verifying Invalid Order is different", 404, response.getStatusCode());
		Assert.assertEquals("Message displayed for Invalid Order is different", "ORDER_NOT_FOUND",
				jsonPathEvaluator.getString("message"));
	}

	@Then("Take Order for Invalid Order")
	public void take_Invalid_Order(DataTable table) {
		List<List<String>> OrderData = table.raw();
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderData.get(1).get(0) + "/take");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Taking Invalid Order is different", 404, response.getStatusCode());
		Assert.assertEquals("Message displayed for Taking Order is different", "ORDER_NOT_FOUND",
				jsonPathEvaluator.getString("message"));
	}

	@Then("Take Order for Completed Order")
	public void take_Completed_Order() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderId + "/take");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Taking Completed Order is different", 422, response.getStatusCode());
		Assert.assertEquals("Message displayed for Completed Order is different", "Order status is not ASSIGNING",
				jsonPathEvaluator.getString("message"));
	}

	@Then("Drive to Complete the Invalid Order")
	public void driver_Invalid_Order(DataTable table) {
		List<List<String>> OrderData = table.raw();
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderData.get(1).get(0) + "/complete");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Completing Invalid Order is different", 404, response.getStatusCode());
		Assert.assertEquals("Message Completing for Taking Order is different", "ORDER_NOT_FOUND",
				jsonPathEvaluator.getString("message"));
	}

	@Then("Drive to Complete the Completed Order")
	public void driver_Completed_Order() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderId + "/complete");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Completing Already Completed Order is different", 422,
				response.getStatusCode());
		Assert.assertEquals("Message Completing Already Completed Order is different", "Order status is not ONGOING",
				jsonPathEvaluator.getString("message"));
	}

	@Then("Cancel the Invalid Order Details")
	public void cancel_Invalid_Order(DataTable table) {
		List<List<String>> OrderData = table.raw();
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderData.get(1).get(0) + "/cancel");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Cancelling Invalid  Order is different", 404, response.getStatusCode());
		Assert.assertEquals("Message for Cancelling Invalid  Order is different", "ORDER_NOT_FOUND",
				jsonPathEvaluator.getString("message"));
	}

	@Then("Cancel the Completed Order Details")
	public void cancel_Completed_Order() {
		String OrderId = (String) Serenity.getCurrentSession().get("orderID");
		Response response = SerenityRest.given().header("Content-Type", "application/json")
				.put("http://localhost:51544/v1/orders/" + "" + OrderId + "/cancel");
		JsonPath jsonPathEvaluator = response.jsonPath();
		Assert.assertEquals("Respone for Cancelling Completed  Order is different", 422, response.getStatusCode());
		Assert.assertEquals("Message for Cancelling Completed  Order is different", "Order status is COMPLETED already",
				jsonPathEvaluator.getString("message"));
	}

}
