<!DOCTYPE html>
<html>
<head>
<title>Insertar</title>
    <link href="/css/miEstilo.css" rel="stylesheet" >
</head>
<body>
    <h1>Insertar estudiante</h1>
    <form action="/insertar/" method="post">
         Matricula: <input name="matricula" type="text"/><br/>
         Nombre: <input name="nombre" id="nombre" type="text"/><br/>
         Apellidos: <input name="apellidos" id="apellidos" type="text"/><br/>
         Telefono: <input name="telefono" id="telefono" type="text"/><br/>
        <button name="Enviar" type="submit">Enviar</button>
    </form>
</body>
</html>