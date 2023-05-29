Feature: Gestionar los estudiantes de la aplicación 

    Scenario: verificar que se pueda crear un estudiante y este quede disponible para consultar 
        Given el servicio de gestión de estudiantes "Students" con autenticación válida 
        When se hace una petición POST al endpoint /classes/Students con información valida de un estudiante no existente 
        Then el servicio responde con código 201 
        And el cuerpo de la respuesta debe incluir el campo "objectId" y "createdAt" 
        When se consultan los usuarios haciendo una petición GET al endpoint /classes/Students
        Then La respuesta del servicio presenta una lista de estudiantes existentes que debe incluir el estudiante recién creado
        
     Scenario: verificar que no se puedan crear estudiantes con el mismo nombre y apellido 
        Given el servicio de gestión de estudiantes "Students" con autenticación válida 
        When se hace una petición POST al endpoint /classes/Students con información valida de un estudiante existente 
        Then el servicio responde un código HTTP 400 
        And el cuerpo de la respuesta debe mostrar un mensaje diciendo que hay duplicidad de información
        
    Scenario: verificar que no se pueden ingresar caracteres especiales en el nombre 
        Given el servicio de gestión de estudiantes "Students" con autenticación válida 
        When se hace una petición POST al endpoint /classes/Students enviando caracteres especiales en el campo "name" 
        Then el servicio responde un código HTTP 400 
        And el cuerpo de la respuesta debe mostrar un mensaje diciendo que se encontraron caracteres inválidos en el campo "name"   
    
    Scenario: verificar que los estudiantes puedan ser actualizados 
        Given el servicio de gestión de estudiantes "Students" con autenticación válida 
        When se hace una petición PUT al endpoint /classes/Students/{studentId} para un estudiante existente 
        And se envía una nueva lista de intereses en el parámetro "interests" en el payload del request 
        Then el servicio responde con código HTTP 200
        And el cuerpo de la respuesta debe incluir el campo "updatedAt" 
        
    Scenario: Verificar que para poder crear nuevos estudiantes se deba estar autenticado en el sistema 
        Given el servicio de gestión estudiantes "Students" 
        And se especifican los encabezados "X-Parse-REST-API-Key" y "X-Parse-Application-Id" con valores válidos
        And NO se especifica el encabezado "X-Parse-Session-Token"
        When se hace una petición POST al endpoint /classes/Students con información valida de un estudiante no existente
        Then el servicio responde un código HTTP 404 
        And el cuerpo de la respuesta se debe mostrar un mensaje diciendo que el usuario debe estar autenticado