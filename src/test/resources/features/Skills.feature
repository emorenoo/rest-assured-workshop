Feature: Gestionar las habilidades disponibles en el sistema

		@API 
    Scenario: Listar todas las habilidades disponibles en el sistema 
        Given el servicio de gestión de habilidades "Skills"  
        When se hace una petición GET al endpoint /classes/Skills  
        Then el servicio responde un código 200 
        And en el cuerpo de la respuesta la longitud de la propiedad "results" es mayor a 0 
    
    Scenario: Consultar una habilidad por código 
        Given el servicio de gestión de habilidades "Skills"  
        When se hace una petición GET al endpoint /classes/Skills/{skillId} especificando el id de una habilidad existente 
        Then el servicio responde un código 200 
        And en el cuerpo de la respuesta se presenta la información de la habilidad que debe incluir los campos "name", "description" y "objectId"  
    
    Scenario: Verificar que no se permite eliminar habilidades 
        Given el servicio de gestión de habilidades "Skills"  
        When se hace una petición DELETE al endpoint /classes/Skills/{skillId} especificando el id de una habilidad existente 
        Then el servicio retorna un error HTTP 400 
        And el cuerpo de la respuesta debe incluir el campo "error" con un mensaje diciendo que el usuario no está autorizado para realizar esta acción
        
    Scenario: Verificar que para poder consultar las habilidades se deben especificar los encabezados válidos 
        Given el servicio de gestión estudiantes "Skills"
        And NO se especifican los encabezados "X-Parse-REST-API-Key" y "X-Parse-Application-Id" 
        When se hace una petición GET al endpoint /classes/Skills 
        Then el servicio responde un código HTTP 401 
        And el cuerpo de la respuesta debe incluir el campo "error" con un mensaje diciendo "unauthorized" 
        
