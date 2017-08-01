<%-- 
    Document   : TipoReporte.jsp
    Created on : 07-30-2017, 05:08:18 PM
    Author     : User
--%>

<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>JSP Page</title>
 <script type="text/javascript" src="js/prototype.js"></script>
 <script type="text/javascript">
 function VerGrafica(tipo){
 $('cargando').innerHTML = "<img src='img/cargando.gif'>..::Espere Porfavor::...";
 var params="selTipo="+tipo;
 var url="GenerarReporte1";
 var ajx = new Ajax.Updater('Grafica',url,{parameters:params,
onComplete:muestraMensaje});
 }
 function muestraMensaje(){
 $('cargando').innerHTML = "";
 }
 </script>
 </head>
 <body>
 <h2>Reporte con Graficas</h2>
 <a href="GenerarReporte1?selTipo=pdf"><img src="img/pdf.jpg" width="40"
height="40" alt="pdf"/> Generar Reporte Pdf</a><br>
 <a href="#" onclick="VerGrafica('html')"><img src="img/web.jpg" width="40"
height="40" alt="web"/> Generar Reporte html</a>
 <div id="cargando">

 </div>
 <br>
 <div id="Grafica">

 </div>
 </body>
</html>
 nel prro solo io puedo :v

