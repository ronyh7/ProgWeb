<!DOCTYPE html>
<html>
<head>
    <title>${titulo}</title>
    <link href="/css/miEstiloBorrar.css" rel="stylesheet" >
</head>
<body>
    <h1>Borrar estudiante</h1>
    <form action="/borrar/" method="post">
        <label>Matricula:</label> <input value=${estudiante.matricula} name="matricula" id="matricula3" readonly/><br/>
        <label>Nombre:</label> <input value=${estudiante.nombre} name="nombre" type="text" id="nombre3" readonly/><br/>
        <label>Apellidos:</label> <input value=${estudiante.apellidos} name="apellidos" type="text" id="apellidos3" readonly/><br/>
        <label>Telefono:</label> <input value=${estudiante.telefono} name="telefono" type="text" id="telefono3" readonly/><br/>
        <button name="Borrar" id="borrar" type="submit">Borrar</button>
    </form>
</body>
</html>