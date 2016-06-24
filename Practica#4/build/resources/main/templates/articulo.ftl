<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Articulo</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/blog.css" rel="stylesheet">
      <link href="/css/articulo.css" rel="stylesheet">
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>
      <nav class="navbar navbar-inverse">
          <div class="container-fluid">
              <div class="navbar-header">
                  <a href="/insertar/${u.username}" class="navbar-brand">BLOG</a>
              </div>
              <div>
                  <ul class="nav navbar-nav">
                      <li> <a href="/home/">Inicio</a></li>
                      <#if ul.username== a.autor.username || ul.administrador=true>
                         <li><a href="/editar/${a.id}">Editar articulo</a></li>
                          <li><a href="/eliminar/${a.id}">Eliminar articulo</a></li>
                      </#if>
                  </ul>

                  <ul class="nav navbar-nav navbar-right">
                        <#if ul.username=="">
                            <li><a href="/login/">Login</a></li>
                        <#else >
                            <li><a href="/login/">Logout</a></li>
                        </#if>
                  </ul>
              </div>
          </div>
      </nav>

    <div class="container">

      <div class="blog-header">
        <h1 class="blog-title">Blog de ${u.nombre}</h1>
          <#if a.opinionDada==true || ul.username=="">
              Likes:<strong>${a.cantidadLikes}</strong> Dislikes:<strong>${a.cantidadDislikes}</strong>
          <#else>
              <form action="/articulo/${a.id}" method="post">
                  <input type="submit" id="Like" name="Like" value="Like">
                  <input type="submit" id="Dislike" name="Dislike" value="Dislike">
              </form>
          </#if>
      </div>

      <div class="row">

        <div class="col-sm-8 blog-main">

          <div class="blog-post">
            <h2 class="blog-post-title">${a.titulo}</h2>
            <p class="blog-post-meta">${a.fecha} by <a href="#">${u.username}</a></p>

             <hr>
            <#if a.quote="">
                <p>${a.cuerpo}</p>
            <#else>
                <blockquote>
                    <p>${a.quote}</p>
                    <footer>${a.qname}</footer>
                </blockquote>
                <p>${a.cuerpo}</p>
            </#if>
          </div><!-- /.blog-post -->


        <#if ul.username!="">
            </br>
            <h2>Escribe un comentario</h2>
            <form action="/articulo/${a.id}" method="post" >
                <label>Comentario:</label> <textarea name="comentario" maxlength="200" id="comentario"></textarea><br/>
                <button name="bcomentario" id="bcomentario" type="submit">Enviar</button>
            </form>
        </#if>

            <hr>
            <div class="divbutton">
            <#if comentarios?has_content>
                <h1>Comentarios</h1>
                <ol class="list-unstyled">
                <#list comentarios as c>
                    <#if ul.username== a.autor.username || ul.administrador=true>
                        <form action="/articulo/${a.id}/${c.id}" method="post">
                            <li id="com">${c.comentario}<button name="bbcomentario" type="submit">Borrar</button></li>
                        </form>
                    <#else>
                        <li id="com">${c.comentario}</li>
                    </#if>
                </#list>
                </ol>
            </#if>
            </div>
            </br>

        </div><!-- /.blog-main -->

        <div class="col-sm-3 col-sm-offset-1 blog-sidebar">
          <div class="sidebar-module sidebar-module-inset">
              <if ${u.about}!="">
                  <h4>Sobre el autor:</h4>
                  <p>${u.about}</p>
              </if>
            </div>
          <div class="sidebar-module">
          <#if etiquetas?has_content>
            <h4>Etiquetas</h4>
            <ol class="list-unstyled">
              <#list etiquetas as e>
                <li>${e.etiqueta}</li>
              </#list>
            </ol>
          </#if>
          </div>
        </div><!-- /.blog-sidebar -->

      </div><!-- /.row -->

    </div><!-- /.container -->

    <footer class="blog-footer">
      <p>Blog template built for <a href="http://getbootstrap.com">Bootstrap</a> by <a href="https://twitter.com/mdo">@mdo</a>.</p>
      <p>
        <a href="#">Back to top</a>
      </p>
    </footer>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
