
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html style="font-size: 16px;">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="Nome gruppo., Le tue liste., I tuoi gruppi.">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>Gruppo</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/logged_in/gruppo/nicepage.css" media="screen">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/logged_in/gruppo/Gruppo.css" media="screen">
    <meta name="generator" content="Nicepage 3.5.3, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i|Archivo+Black:400">
    <link id="u-page-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Archivo+Black:400">



    <script type="application/ld+json">{
		"@context": "http://schema.org",
		"@type": "Organization",
		"name": "",
		"url": "index.html"
}</script>
    <meta property="og:title" content="Gruppo">
    <meta property="og:type" content="website">
    <meta name="theme-color" content="#478ac9">
    <link rel="canonical" href="index.html">
    <meta property="og:url" content="index.html">
  </head>
  <body class="u-body"><header class="u-clearfix u-header u-header" id="sec-ddeb"><div class="u-clearfix u-sheet u-sheet-1"></div></header>
    <section class="u-clearfix u-section-1" id="sec-36aa">
      <div class="u-clearfix u-sheet u-sheet-1">
        <a href="${pageContext.servletContext.contextPath}/logout" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-1">LOGOUT</a>
        <a href="${pageContext.servletContext.contextPath}/logged_in/dashboard" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-2">DASHBOARD</a>
      </div>
    </section>
    <section class="u-clearfix u-section-2" id="sec-91ba">
      <div class="u-clearfix u-sheet u-sheet-1">
        <h1 class="u-custom-font u-text u-text-body-color u-text-1">${gruppo.nome}.</h1>

        <form action="${pageContext.servletContext.contextPath}/logged_in/gruppo/" method="post">
           <button type="submit" name="button_pushed" value="elimina_gruppo" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-1">ELIMINA GRUPPO</button>
           <button type="submit" name="button_pushed" value="rinomina_gruppo" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-2">RINOMINA GRUPPO</button>
        </form>

        <h1 class="u-custom-font u-text u-text-body-color u-text-2">Partecipanti.</h1>

        <form action="${pageContext.servletContext.contextPath}/logged_in/gruppo/" method="post">
           <button type="submit" name="button_pushed" value="aggiungi_partecipante" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-3">AGGIUNGI PARTECIPANTE</button>
        </form>

        <div class="u-container-style u-expanded-width u-group u-palette-5-light-2 u-radius-30 u-shape-round u-group-1">
          <div class="u-container-layout u-container-layout-1">
            <c:forEach var="par" items="${partecipanti}">
              <div class="u-container-style u-expanded-width u-group u-radius-30 u-shape-round u-white u-group-2">
                <div class="u-container-layout u-container-layout-2">
                  <p class="u-custom-font u-text u-text-3">
                    <span style="font-size: 1.5rem; font-weight: 400;" class="u-text-palette-5-dark-3">${par.nome} ${par.cognome}</span>
                  </p>
                  <form action="${pageContext.servletContext.contextPath}/logged_in/gruppo/" method="post">
                    <button type="submit" name="id_partecipante" value="${par.id}" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-4">RIMUOVI</button>
                  </form>
                </div>
              </div>
              <div class="u-expanded-width u-palette-5-light-2 u-shape u-shape-rectangle u-shape-1"></div>
            </c:forEach>
          </div>
        </div>

        <h1 class="u-custom-font u-text u-text-body-color u-text-4">Liste condivise.</h1>

        <div class="u-container-style u-expanded-width u-group u-palette-5-light-2 u-radius-30 u-shape-round u-group-3">
          <div class="u-container-layout u-container-layout-3">
            <c:forEach var="lista" items="${gruppo.listeCondivise}">
              <div class="u-container-style u-expanded-width u-group u-radius-30 u-shape-round u-white u-group-4">
                <div class="u-container-layout u-container-layout-4">
                  <p class="u-custom-font u-text u-text-5">
                    <span style="font-size: 1.5rem; font-weight: 400;" class="u-text-palette-5-dark-3">${lista.nome}</span>
                  </p>
                  <form action="${pageContext.servletContext.contextPath}/logged_in/gruppo/" method="post">
                    <button type="submit" name="rimuovi_id_lista" value="${lista.id}" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-5">RIMUOVI</button>
                  </form>
                  <p class="u-custom-font u-text u-text-6">${lista.proprietario.nome} ${lista.proprietario.cognome}</p>
                  <form action="${pageContext.servletContext.contextPath}/logged_in/gruppo/" method="post">
                    <button type="submit" name="visualizza_id_lista" value="${lista.id}" class="u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-6">VISUALIZZA</button>
                  </form>
                </div>
              </div>
              <div class="u-palette-5-light-2 u-shape u-shape-rectangle u-shape-2"></div>
            </c:forEach>
          </div>
        </div>

      </div>
    </section>


    <footer class="u-align-center u-clearfix u-footer u-palette-5-light-1 u-footer" id="sec-6768"><div class="u-clearfix u-sheet u-valign-middle u-sheet-1">
        <p class="u-small-text u-text u-text-variant u-text-1">Esame di Secure System Design.</p>
        <p class="u-small-text u-text u-text-variant u-text-2">Anno accademico 2020/21.</p>
        <p class="u-small-text u-text u-text-variant u-text-3">Francesco Pietrantonio. Carmine Marra. Francesco Papulino.</p>
      </div></footer>
  </body>
</html>
