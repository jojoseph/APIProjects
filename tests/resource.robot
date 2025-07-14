*** Settings ***
Documentation    A resource file which keeps all the reusable keyword and variables
Library    SeleniumLibrary
*** Variables ***
${username}   rahulshetty
${password}    learning
${invalid_password}    12345678
*** Keywords ***
 Open the browser with the mortgage payment url
     Create Webdriver    Chrome
     Go To    https://rahulshettyacademy.com/locatorspractice/
Close Browser resource
    Close Browser