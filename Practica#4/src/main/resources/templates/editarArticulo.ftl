<!DOCTYPE html>
<html>
<head>
<title>Editar articulo</title>
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
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login/">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="col-md-3"></div>
        <div class="col-md-4">
            <h1>Edita el articulo</h1>
            <form action="/editar/${id}" method="post" >
                <label>Titulo:</label> <input name="titulo"  value='${a.titulo}' type="text"/><br/>
                <label>Body:</label> <textarea name="cuerpo"  maxlength="500" id="cuerpo">${a.cuerpo}</textarea><br/>
                <label>Etiquetas</label><input name="etiquetas" value='${etiquetas}' type="text"/><br/>
                <hr>
                <h4><strong>opcional</strong></h4><br/>
                <label>Dicho:</label> <input name="quote" value='${a.quote}' type="text"/><br/>
                <label>Escritor:</label> <input name="qname" value='${a.qname}'  type="text"/><br/>
                <button name="Editar" id="editar" type="submit">Editar</button>
            </form>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>