*** Settings ***
Documentation    To validate he login form
Library    SeleniumLibrary

#Resourcers
Test Setup    Open the browser with the mortgage payment url
Test Teardown    Close Browser resource
Resource    resource.robot

*** Variables ***
${invalid_error_message}    class:error
${verify_page_load}    css:.nav-link

*** Test Cases ***
Validate invalid login
    Validate the login form    ${username}     ${invalid_password}    
    Wait until it checks element is present in a page    ${invalid_error_message}
    Verify the error message is correct
Validate the login form
    Validate the login form    ${username}    ${password}
    Wait until it checks element is present in a page    ${verify_page_load}

*** Keywords ***
Validate the login form
    [Arguments]    ${username}    ${password}
    Input Text    id:inputUsername    ${username}
    Input Password    name:inputPassword  ${password}
    Click Button    xpath:/html/body/div/div/div/div/div/div/div[2]/form/button
Wait until it checks element is present in a page
    [Arguments]    ${element_present}
    Wait Until Element Is Visible    ${element_present}
Verify the error message is correct
  #  ${result}=    Get Text    ${Login_error_message_class}
  # Should Be Equal As Strings    ${result}    * Incorrect username or password
  Element Text Should Be    ${Login_error_message_class}    * Incorrect username or password
