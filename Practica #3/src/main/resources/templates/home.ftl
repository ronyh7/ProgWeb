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
                <a href="/insertar/${user.username}" class="navbar-brand">BLOG</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"> <a href="">Inicio</a></li>
                    <li><a href="/articulo/">Mi Perfil</a></li>
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
        <#if user.username=="">
            <#if marticulos?has_content>
                <h1>Articulos</h1>
            <ul>
                <#list articulos as a>
                    <li><a href="/articulo//">${a.titulo}</br>
                        <pre>${a.cuerpo}</pre> </br></a></li>
                </#list>
            </ul>
            <#else>
                <h1>No hay articulos</h1>
            </#if>
        <#else>
        <div class="col-md-6">
            <h1>Tus Articulos</h1>
            <#if marticulos?has_content>
                <ul>
                    <#list marticulos as a>
                        <li><a href="/articulo/${user.username}/${a.id}">${a.titulo}</br>
                        <pre>${a.cuerpo}</pre> </br></a></li>
                    </#list>
                </ul>
            </#if>
        </div>
        <div class="col-md-6">
            <h1>Articulos de otros usuarios</h1>
            <#if darticulos?has_content>
                <ol>
                    <#list darticulos as d>
                        <li>${d.titulo}</li>
                    </#list>
                </ol>
            </#if>
        </div>
        </#if>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>