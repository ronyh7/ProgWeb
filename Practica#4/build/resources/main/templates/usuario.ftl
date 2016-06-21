<!DOCTYPE html>
<html>
<head>
<title>Insertar</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/insertar.css" rel="stylesheet" >

</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="/insertar/" class="navbar-brand">BLOG</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li> <a href="/home/">Inicio</a></li>
                    <li><a href="/articulo/">Mi Perfil</a></li>
                    <li class="active"><a href="/usuario/">Crear Usuario</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/home/">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="col-md-3"></div>
        <div class="col-md-5">
            <h1>Inserta un nuevo usuario</h1>
            <form action="/usuario/" method="post" >
                <label>Username:</label> <input name="username" type="text"/><br/>
                <label>Nombre:</label> <input name="nombre"  type="text"/><br/>
                <label>Password:</label> <input name="password" type="password"></input><br/>
                <label>About(Opcional):</label> <input name="about" type="text"/><br/>
                <label>Autor:</label><input name="autor" type="checkbox" value="true"/><br/>
                <label>Administrador:</label><input name="admin" type="checkbox" value="true"/><br/>
                <button name="usuario" id="usuario" type="submit">Enviar</button>
            </form>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>