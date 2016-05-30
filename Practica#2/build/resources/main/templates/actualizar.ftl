<!DOCTYPE html>
<html>
    <head>
    <title>${titulo}</title>
    <link href="/css/miEstilo.css" rel="stylesheet" >
    </head>
    <body>
        <h1>Actualizar estudiante</h1>
        <form action="/actualizar/" method="post">
             Matricula: <input value=${estudiante.matricula} name="matricula" readonly/><br/>
             Nombre:    <input value=${estudiante.nombre} name="nombre" id="nombre" type="text"/><br/>
             Apellidos: <input value=${estudiante.apellidos} name="apellidos" id="apellidos" type="text"/><br/>
             Telefono:  <input value=${estudiante.telefono} name="telefono" id="telefono" type="text"/><br/>
            <button name="actualizar" type="submit">actualizar</button>
        </form>
    </body>
</html>