#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Title of your feature
  I want to use this template for my feature file

  @tag1
  Scenario: Title of your scenario
    Given I want to write a step with precondition
    And some other precondition
    When I complete action
    And some other action
    And yet another action
    Then I validate the outcomes
    And check more outcomes

  @tag2
  Scenario Outline: Title of your scenario outline
    Given I want to write a step with <name>
    When I check for the <value> in step
    Then I verify the <status> in step

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
      
      
Feature: Gestionar los habilidades de los estudiantes 

    Scenario: Verificar la adición de una habilidad a un estudiante 
        Given el servicio de gestión de habilidades "StudentsSkills" con autenticación válida 
        When se hace una petición POST al endpoint /classes/StudentSkills especificando información de un estudiante y una habilidad existente 
        Then el servicio responde un código HTTP 201 
        And el cuerpo de la respuesta debe tener los campos "objectId" y "createdAt"
        When cuando se hace una petición GET al endpoint /classes/Students/{studentId} para el mismo estudiante
        Then el cuerpo de la respuesta debe incluir el campo "skillNames"
        
    Scenario: Verificar la adición de una habilidad repetida a un estudiante 
        Given el servicio de gestión de habilidades "StudentsSkills" con autenticación válida 
        When se hace una petición POST al endpoint /classes/StudentSkills especificando información de un estudiante y una habilidad ya asociada al estudiante 
        Then el servicio responde un código HTTP 400 
        And el cuerpo de la respuesta debe incluir un mensaje de error diciendo que hay unformación duplicada
    
    Scenario: Verificar que para poder asociar nuevas habilidades se deba estar autenticado en el sistema 
        Given el servicio de gestión de habilidades de un estudiante "StudentsSkills" 
        And se especifican los encabezados "X-Parse-REST-API-Key" y "X-Parse-Application-Id" con valores válidos
        And NO se especifica el encabezado "X-Parse-Session-Token" 
        When se hace una petición POST al endpoint /classes/StudentSkills especificando información de un estudiante y una habilidad existente
        Then el servicio responde un código HTTP 404 
        And el cuerpo de la respuesta se debe mostrar un mensaje diciendo que el usuario debe estar autenticado
