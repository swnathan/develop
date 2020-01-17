#Author: Swaminathan
Feature: List of scenarios to Complete Order

  Scenario: Verifying Complete Order
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Drive to Complete the Order

  Scenario: Verifying Complete Order - Invalid Order
    Then Drive to Complete the Invalid Order
      | OrderID |
      | 0011212 |

  Scenario: Verifying Complete Order - Already Order Completed
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Drive to Complete the Order
    Then Drive to Complete the Completed Order