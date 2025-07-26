*** Settings ***
Documentation    To validate he login form
Library    SeleniumLibrary
#Resourcers
Test Teardown    Close Browser
*** Variables ***
${Login_error_message_class}    class:error

*** Test Cases ***
Validate Successful login
    Open the browser with the mortgage payment url
    Fill the login form
    Wait until it checks and display the error message
    Verify the error message is correct
*** Keywords ***
 Open the browser with the mortgage payment url
     Create Webdriver    Chrome
     Go To    https://rahulshettyacademy.com/locatorspractice/
Fill the login form
    Input Text    id:inputUsername    rahulshetty
    Input Password    name:inputPassword  12345678
    Click Button    xpath:/html/body/div/div/div/div/div/div/div[2]/form/button
Wait until it checks and display the error message
    Wait Until Element Is Visible    ${Login_error_message_class}
Verify the error message is correct
  #  ${result}=    Get Text    ${Login_error_message_class}
  # Should Be Equal As Strings    ${result}    * Incorrect username or password
  Element Text Should Be    ${Login_error_message_class}    * Incorrect username or password
