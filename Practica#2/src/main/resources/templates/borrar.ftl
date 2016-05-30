<!DOCTYPE html>
<html>
<head>
    <title>${titulo}</title>
    <link href="/css/miEstilo.css" rel="stylesheet" >
</head>
<body>
    <h1>Borrar estudiante</h1>
    <form action="/borrar/" method="post">
        Matricula: <input value=${estudiante.matricula} name="matricula" readonly/><br/>
        Nombre: <input value=${estudiante.nombre} name="nombre" type="text" readonly/><br/>
        Apellidos: <input value=${estudiante.apellidos} name="apellidos" type="text" readonly/><br/>
        Telefono: <input value=${estudiante.telefono} name="telefono" type="text" readonly/><br/>
        <button name="borrar" type="submit">borrar</button>
    </form>
</body>
</html>