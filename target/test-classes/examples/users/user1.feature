Feature: sample karate test script

Scenario: defining JSON object and print it
    
    
    Given def jsonObject =
    """
        [
          {
            "name": "jack",
            "phone" : 15435667788
          },
          {
            "name": "jennie",
            "phone" : 13443567234
          }
        ]
      """
    * print jsonObject[1].name, jsonObject[1].phone