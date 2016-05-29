<!DOCTYPE html>
<html>
    <head>
        <title></title>
    <!--<link href="/css/miEstilo.css" rel="stylesheet" >-->
    </head>
    <body>
        <table>
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
                    </tr>
                </#list>
            </tbody>
        </table>
        <form action="/formulario" method="get">
            <button name="Insertar" type="submit">Insertar</button>
        </form>
    </body>
</html>