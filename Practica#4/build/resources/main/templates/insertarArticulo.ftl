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
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/home/">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="col-md-3"></div>
        <div class="col-md-4">
            <h1>Escribe un articulo</h1>
            <form action="/insertar/" method="post" >
                <label>Titulo:</label> <input name="titulo" type="text"/><br/>
                <label>Body:</label> <textarea name="cuerpo" maxlength="500" id="cuerpo"></textarea><br/>
                <label>Etiquetas:</label><input name="etiquetas" type="text"/><br/>
                <mark>(ejemplo: #etiqueta1,#etiqueta2)</mark><br/>
                <hr>
                <h4><strong>opcional</strong></h4><br/>
                <label>Dicho:</label> <input name="quote" type="text"/><br/>
                <label>Escritor:</label> <input name="qname"  type="text"/><br/>
                <button name="Insertar" id="insertar" type="submit">Enviar</button>
            </form>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>