#Author: Swaminathan
Feature: List of scenarios  to Process the Orders

  Scenario: Verifying Place Order Details
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Drive to Complete the Order

  Scenario: Verifying Place Order Details - Advance Order
    Given Take Future Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Drive to Complete the Order

  Scenario: Verifying Place Order Details - Advance Order Time Less than Current Time
    Given Take Past Order Requests at Stops Latitue and Longitude