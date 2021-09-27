Feature: Validating Book APIs

Background: The Base Url is assigned
     Given The Base Url is assigned

Scenario: Verify if the user is able to add a book
     Given The user adds a book with name "See Test" isbn "12340" aisle "5678" author "William Wald" as "Object"
     Then  The user gets the book added with the "ID"
     And   The user gets the book added with the "author"
     And   The user deletes the book
     And   Verifies whether the book has been deleted

Scenario Outline: Verify that the user is able to add multiple books
     Given The user adds a book with name "<bookName>" isbn "<isbn>" aisle "<aisle>" author "<authorName>" as "<form>" 
     Then  The user gets the book added with the "ID"
     And   The user gets the book added with the "author"
     And   The user deletes the book
     And   Verifies whether the book has been deleted

Examples:
	|bookName 	   |isbn       |aisle		  |authorName      |form     |
	|Cypress       |15670      |9087          |Robert Wald    |Object   | 
	|Webdirver IO  |14560      |5678          |Robert Wald    |Object   |

Scenario: Verify the user is not able to add the same book again
     Given The user adds a book with name "Learn Selenium 15" isbn "90024379154" aisle "345723714" author "Rahul Shetty4" as "Object"
     Then The user adds a book with name "Learn Selenium 15" isbn "90024379154" aisle "345723714" author "Rahul Shetty4" as "Object"
     