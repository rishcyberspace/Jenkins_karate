Feature: sample karate test script
  for help, see: https://github.com/intuit/karate/wiki/IDE-Support

  Background:
    * url 'https://reqres.in'

 Scenario: Get a Single User and Verify its JSON Fields
    Given path '/api/users/2'
    When method get
    Then status 200
    Then match response == '#object'
    * def jsonSchemaExpected = 
"""
    {
    "data": {
        "id": '#number',
        "email": "#string",
        "first_name": "#string",
        "last_name": "#string",
        "avatar": "#string"
    },
    "support": {
        "url": "#string",
        "text": "#string"
    }
}]
"""

  Then match response.data.first_name == "Janet"
  * def rn = Math.floor(Math.random() * 999999999) + 10000000
  * print 'The random value is', rn
  

   