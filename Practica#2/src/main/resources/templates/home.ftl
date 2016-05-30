<!DOCTYPE html>
<html>
    <head>
        <title></title>
    <link href="/css/miEstilo.css" rel="stylesheet">
    </head>
    <body>
        <table  border="2">
            <thead>
                <tr>
                    <th>Matricula</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Telefono</th>
                </tr>
            </thead>
            <tbody>
                <#list estudiantes as e>
                    <tr>
                        <td>${e.matricula}</td>
                        <td>${e.nombre}</td>
                        <td>${e.apellidos}</td>
                        <td>${e.telefono}</td>
                        <td><a href="/actualizar/${e.matricula}"><button name="Actualizar">Actualizar</button></a></td>
                        <td><a href="/borrar/${e.matricula}"><button name="Borrar">Borrar</button></a></td>
                    </tr>
                </#list>
            </tbody>
        </table>
        <form action="/insertar/" method="get">
            <button name="Insertar" type="submit">Insertar</button>
        </form>
    </body>
</html>