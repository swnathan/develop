#Author: Swaminathan
Feature: List of scenarios to Take Order

  Scenario: Verifying Take Order - valid Order
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint

  Scenario: Verifying Take Order - Invalid Order
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Take Order for Invalid Order
      | OrderID |
      | 0011212 |

  Scenario: Verifying Take Order - Completed Order
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Drive to Complete the Order
    Then Take Order for Completed Order