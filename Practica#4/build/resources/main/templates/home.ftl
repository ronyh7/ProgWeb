<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>BLOG</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
      <link href="/css/blog.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="/insertar/${user.username}" class="navbar-brand">BLOG Here!</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"> <a href="/home/">Inicio</a></li>
                    <#if user.administrador==true>
                        <li><a href="/usuario/">Crear Usuario</a></li>
                    </#if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <#if user.username=="">
                        <li><a href="/login/">Login</a></li>
                    <#else>
                        <li><a href="/login/">Logout</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
    <div class="col-md-12">
        <#if articulos?has_content>
            <h1>${titulo}</h1>
        <ul>
            <#list articulos as a>
                <li><a href="/articulo/${a.id}"><h5 style="color:#9acfea">${a.titulo}</h5></a> &nbsp&nbsp&nbsp Likes:<strong>${a.cantidadLikes}</strong> &nbsp Dislikes:<strong>${a.cantidadDislikes}</strong></br>
                    <pre>${a.cuerpo}</pre></li>
                <#list a.listaEtiquetas as e>
                    <a href="/etiqueta/1/${e.id}">${e.etiqueta}</a>
                </#list>
                </br>
                </br>
            </#list>
        </ul>
        <#else>
            <h1>No hay articulos</h1>
        </#if>
        <div class="pag">
                <ul class="pagination pagination-lg">
                    <#if tipo=='home'>
                        <li><a href="/home/1">1</a></li>
                        <li><a href="/home/2">2</a></li>
                        <li><a href="/home/3">3</a></li>
                        <li><a href="/home/4">4</a></li>
                        <li><a href="/home/5">5</a></li>
                    <#else>
                        <li><a href="/etiqueta/1/${etiqueta}">1</a></li>
                        <li><a href="/etiqueta/2/${etiqueta}">2</a></li>
                        <li><a href="/etiqueta/3/${etiqueta}">3</a></li>
                        <li><a href="/etiqueta/4/${etiqueta}">4</a></li>
                        <li><a href="/etiqueta/5/${etiqueta}">5</a></li>
                    </#if>
                </ul>
            </div>
    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>