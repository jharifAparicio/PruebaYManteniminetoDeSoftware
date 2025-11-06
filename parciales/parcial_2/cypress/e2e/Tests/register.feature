Feature: Registro de Trámites de Interesados
  Como usuario administrador
  Quiero registrar trámites para 5 interesados
  Y verificar que se procesen correctamente

Background: El usuario está autenticado en el dashboard
  Given que estoy en la página de login
  When inicio sesión con mis credenciales
  Then estoy en el dashboard

Scenario Outline: Registrar, imprimir y verificar un trámite
  Given estoy en el dashboard
  When hago clic en el botón "Nuevo"
  And lleno el formulario principal con los datos del interesado: "<ci>"
  And selecciono el motivo "CERTIFICACION LEY Nº 348 Y 1153"
  And hago clic en el botón "Guardar"
  And inserto el numero de valorado "<valorado>"
  And registro el motivo minimo 15 caracteres "<motivo>" y hago clic en "Si"
  When navego a la página de "Respuestas"

  And extraigo y guardo el ID de la solicitud usando el CI "<ci>"
  
  And selecciono la solicitud usando el ID guardado
  And presiono "Imprimir" para la solicitud seleccionada
  When navego a la página de "Entregados"
  Then el trámite debe estar visible en la lista de entregados usando el ID guardado

Examples:
  | ci        | motivo                    | valorado |
  | "13475981" | "Trámite de prueba 2"     | 7654321  |
  | "12345579" | "Trámite de prueba 3"     | 8888888  |
  | "4198152" | "Trámite de prueba 4"     | 9999999  |
  | "3836587" | "Trámite de prueba 5"     | 1111111  |
  | "8016735" | "Trámite de prueba 6"     | 2222222  |
  | "9435845" | "Trámite de prueba 7"     | 3333333  |
  | "5222459" | "Trámite de prueba 8"     | 4444444  |
  | "6456422" | "Trámite de prueba 9"     | 5555555  |