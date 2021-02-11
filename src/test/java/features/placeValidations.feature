Feature: Validating Place APIs


Scenario Outline: Verify if place is added successfully using AddPlaceApi

	Given add place Payload with "<name>""<language>""<address>"
	When user calls "addPlaceAPI"  with "post" http request
	Then the Api calls is success status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verfity place_ID created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name		|language		|address			|
	|Bala		|English		|85,Tulipgardens	|
	|Neshu		|Spanish		|80,Tulipgardens	|
	|Hrik		|German 		|85,Samroc			|


Scenario: Verify if Delete Place functionality is working
 
	Given delecte payLoad	
	When user calls "deletePlaceAPI"  with "POST" http request
	Then the Api calls is success status code 200
	And "status" in response body is "OK"
	