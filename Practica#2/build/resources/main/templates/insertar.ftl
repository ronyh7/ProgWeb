<!DOCTYPE html>
<html>
<head>
<title>Insertar</title>
    <link href="/css/miEstilo.css" rel="stylesheet" >
</head>
<body>
    <h1>Insertar estudiante</h1>
    <form action="/insertar/" method="post">
         <label>Matricula:</label> <input name="matricula" type="text"/><br/>
         <label>Nombre:</label> <input name="nombre" id="nombre" type="text"/><br/>
         <label>Apellidos:</label> <input name="apellidos" id="apellidos" type="text"/><br/>
         <label>Telefono:</label> <input name="telefono" id="telefono" type="text"/><br/>
        <button name="Enviar" id="insertar" type="submit">Enviar</button>
    </form>
</body>
</html>