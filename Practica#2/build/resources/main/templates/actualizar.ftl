<!DOCTYPE html>
<html>
    <head>
    <title>${titulo}</title>
    <link href="/css/miEstiloActualizar.css" rel="stylesheet" >
    </head>
    <body>
        <h1>Actualizar estudiante</h1>
        <form action="/actualizar/" method="post">
             <label>Matricula:</label> <input value=${estudiante.matricula} name="matricula" id="matricula2" readonly/><br/>
             <label>Nombre:</label>    <input value=${estudiante.nombre} name="nombre" id="nombre" type="text"/><br/>
             <label>Apellidos:</label> <input value=${estudiante.apellidos} name="apellidos" id="apellidos" type="text"/><br/>
             <label>Telefono:</label>  <input value=${estudiante.telefono} name="telefono" id="telefono" type="text"/><br/>
            <button name="Actualizar" id="actualizar" type="submit">Actualizar</button>
        </form>
    </body>
</html>