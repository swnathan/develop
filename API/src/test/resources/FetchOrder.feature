#Author: Swaminathan
Feature: List of scenarios to Verify the Fetch Orders

  Scenario: Verifying Fetch Order Details
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    
    Scenario: Verifying Fetch Order Details for Invalid Order 
    Then Verify the Invalid Order Details
    | OrderID|
    | 0011212|