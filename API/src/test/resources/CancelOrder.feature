#Author: Swaminathan
Feature: List of scenarios to Cancel Order

  Scenario: Verifying Cancel Order - Created Order
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Cancel the Order Details

  Scenario: Verifying Cancel Order - Ongoing
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Cancel the Order Details

  Scenario: Verifying Cancel Order - Invalid Order
    Then Cancel the Invalid Order Details
      | OrderID |
      | 0011212 |

  Scenario: Verifying Cancel Order - Completed Order
    Given Take Order Requests at Stops Latitue and Longitude
    Then Verify the Placed Order Details
    Then Drive to Take the Order EndPoint
    Then Drive to Complete the Order
    Then Cancel the Completed Order Details