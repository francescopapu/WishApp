/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.44
 * Generated at: 2021-02-16 20:30:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.logged_005fin.dashboard;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Dashboard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("jar:file:/Users/frapiet/.gradle/caches/modules-2/files-2.1/javax.servlet/jstl/1.2/74aca283cd4f4b4f3e425f5820cda58f44409547/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153377882000L));
    _jspx_dependants.put("file:/Users/frapiet/.gradle/caches/modules-2/files-2.1/javax.servlet/jstl/1.2/74aca283cd4f4b4f3e425f5820cda58f44409547/jstl-1.2.jar", Long.valueOf(1610909352000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html style=\"font-size: 16px;\">\n");
      out.write("  <head>\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <meta name=\"keywords\" content=\"Learn, The Science of Gastronomy, 01, 02, 03, 04, Online Cooking Classes, Course structure, Cooking Vegetables, Chefs Boot Camp for Policy and Changeâ, Healthy food, Contact us\">\n");
      out.write("    <meta name=\"description\" content=\"\">\n");
      out.write("    <meta name=\"page_type\" content=\"np-template-header-footer-from-plugin\">\n");
      out.write("    <title>Dashboard</title>\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/logged_in/dashboard/nicepage.css\" media=\"screen\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/logged_in/dashboard/Dashboard.css\" media=\"screen\">\n");
      out.write("    <meta name=\"generator\" content=\"Nicepage 3.5.3, nicepage.com\">\n");
      out.write("    <link id=\"u-theme-google-font\" rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i|Archivo+Black:400\">\n");
      out.write("    <link id=\"u-page-google-font\" rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Archivo+Black:400\">\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("    <script type=\"application/ld+json\">{\n");
      out.write("\t\t\"@context\": \"http://schema.org\",\n");
      out.write("\t\t\"@type\": \"Organization\",\n");
      out.write("\t\t\"name\": \"\",\n");
      out.write("\t\t\"url\": \"index.html\"\n");
      out.write("}</script>\n");
      out.write("    <meta property=\"og:title\" content=\"Dashboard\">\n");
      out.write("    <meta property=\"og:type\" content=\"website\">\n");
      out.write("    <meta name=\"theme-color\" content=\"#478ac9\">\n");
      out.write("    <link rel=\"canonical\" href=\"index.html\">\n");
      out.write("    <meta property=\"og:url\" content=\"index.html\">\n");
      out.write("  </head>\n");
      out.write("  <body class=\"u-body u-stick-footer\"><header class=\"u-clearfix u-header u-header\" id=\"sec-ddeb\"><div class=\"u-clearfix u-sheet u-sheet-1\"></div></header>\n");
      out.write("    <section class=\"u-clearfix u-section-1\" id=\"sec-36aa\">\n");
      out.write("      <div class=\"u-clearfix u-sheet u-valign-middle u-sheet-1\">\n");
      out.write("        <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/logout\" class=\"u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-1\">LOGOUT</a>\n");
      out.write("      </div>\n");
      out.write("    </section>\n");
      out.write("    <section class=\"u-clearfix u-section-2\" id=\"sec-91ba\">\n");
      out.write("      <div class=\"u-clearfix u-sheet u-sheet-1\">\n");
      out.write("        <h1 class=\"u-custom-font u-text u-text-body-color u-text-1\">Benvenuto nella tua dashboard.</h1>\n");
      out.write("        <h1 class=\"u-custom-font u-text u-text-body-color u-text-2\">Le tue liste.</h1>\n");
      out.write("\n");
      out.write("        <form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/logged_in/dashboard/\" method=\"post\">\n");
      out.write("           <button type=\"submit\" name=\"button_pushed\" value=\"nuova_lista\" class=\"u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-1\">CREA NUOVA</button>\n");
      out.write("        </form>\n");
      out.write("\n");
      out.write("        <div class=\"u-container-style u-expanded-width u-group u-palette-5-light-2 u-radius-30 u-shape-round u-group-1\">\n");
      out.write("          <div class=\"u-container-layout u-container-layout-1\">\n");
      out.write("            ");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <h1 class=\"u-custom-font u-text u-text-body-color u-text-4\">I tuoi gruppi.</h1>\n");
      out.write("\n");
      out.write("        <form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/logged_in/dashboard/\" method=\"post\">\n");
      out.write("           <button type=\"submit\" name=\"button_pushed\" value=\"nuovo_gruppo\" class=\"u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-3\">CREA NUOVO</button>\n");
      out.write("        </form>\n");
      out.write("\n");
      out.write("        <div class=\"u-container-style u-expanded-width u-group u-palette-5-light-2 u-radius-30 u-shape-round u-group-3\">\n");
      out.write("          <div class=\"u-container-layout u-container-layout-3\">\n");
      out.write("            ");
      if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("      </div>\n");
      out.write("    </section>\n");
      out.write("\n");
      out.write("    <footer class=\"u-align-center u-clearfix u-footer u-palette-5-light-1 u-footer\" id=\"sec-6768\"><div class=\"u-clearfix u-sheet u-valign-middle u-sheet-1\">\n");
      out.write("        <p class=\"u-small-text u-text u-text-variant u-text-1\">Esame di Secure System Design.</p>\n");
      out.write("        <p class=\"u-small-text u-text u-text-variant u-text-2\">Anno accademico 2020/21.</p>\n");
      out.write("        <p class=\"u-small-text u-text u-text-variant u-text-3\">Francesco Pietrantonio. Carmine Marra. Francesco Papulino.</p>\n");
      out.write("      </div></footer>\n");
      out.write("  </body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    boolean _jspx_th_c_005fforEach_005f0_reused = false;
    try {
      _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f0.setParent(null);
      // /logged_in/dashboard/Dashboard.jsp(49,12) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setVar("lista");
      // /logged_in/dashboard/Dashboard.jsp(49,12) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/logged_in/dashboard/Dashboard.jsp(49,12) '${requestScope.liste}'",_jsp_getExpressionFactory().createValueExpression(_jspx_page_context.getELContext(),"${requestScope.liste}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
      int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
        if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\n");
            out.write("              <div class=\"u-expanded-width u-palette-5-light-2 u-shape u-shape-rectangle u-shape-1\"></div>\n");
            out.write("              <div class=\"u-container-style u-expanded-width u-group u-radius-30 u-shape-round u-white u-group-2\">\n");
            out.write("                <div class=\"u-container-layout u-container-layout-2\">\n");
            out.write("                  <p class=\"u-custom-font u-text u-text-3\">\n");
            out.write("                    <span style=\"font-size: 1.5rem; font-weight: 400;\" class=\"u-text-palette-5-dark-3\">");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${lista.nome}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("</span>\n");
            out.write("                  </p>\n");
            out.write("                  <form action=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("/logged_in/dashboard/\" method=\"post\">\n");
            out.write("                       <button type=\"submit\" name=\"id_lista\" value=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${lista.id}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("\" class=\"u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-2\">VISUALIZZA</button>\n");
            out.write("                  </form>\n");
            out.write("                </div>\n");
            out.write("              </div>\n");
            out.write("              <div class=\"u-expanded-width u-palette-5-light-2 u-shape u-shape-rectangle u-shape-2\"></div>\n");
            out.write("            ");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
      _jspx_th_c_005fforEach_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fforEach_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fforEach_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    boolean _jspx_th_c_005fforEach_005f1_reused = false;
    try {
      _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f1.setParent(null);
      // /logged_in/dashboard/Dashboard.jsp(74,12) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f1.setVar("gruppo");
      // /logged_in/dashboard/Dashboard.jsp(74,12) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f1.setItems(new org.apache.jasper.el.JspValueExpression("/logged_in/dashboard/Dashboard.jsp(74,12) '${requestScope.gruppi}'",_jsp_getExpressionFactory().createValueExpression(_jspx_page_context.getELContext(),"${requestScope.gruppi}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
      int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
        if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\n");
            out.write("              <div class=\"u-expanded-width u-palette-5-light-2 u-shape u-shape-rectangle u-shape-3\"></div>\n");
            out.write("              <div class=\"u-container-style u-expanded-width u-group u-radius-30 u-shape-round u-white u-group-4\">\n");
            out.write("                <div class=\"u-container-layout u-container-layout-4\">\n");
            out.write("                  <p class=\"u-custom-font u-text u-text-5\">\n");
            out.write("                    <span style=\"font-size: 1.5rem; font-weight: 400;\" class=\"u-text-palette-5-dark-3\">");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gruppo.nome}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("</span>\n");
            out.write("                  </p>\n");
            out.write("                  <form action=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.servletContext.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("/logged_in/dashboard/\" method=\"post\">\n");
            out.write("                       <button type=\"submit\" name=\"id_gruppo\" value=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gruppo.id}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("\" class=\"u-border-2 u-border-hover-white u-border-palette-2-base u-btn u-btn-round u-button-style u-hover-palette-2-base u-none u-radius-50 u-text-hover-white u-text-palette-2-base u-btn-4\">VISUALIZZA</button>\n");
            out.write("                  </form>\n");
            out.write("                </div>\n");
            out.write("              </div>\n");
            out.write("              <div class=\"u-expanded-width u-palette-5-light-2 u-shape u-shape-rectangle u-shape-4\"></div>\n");
            out.write("            ");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f1[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f1.doFinally();
      }
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
      _jspx_th_c_005fforEach_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fforEach_005f1, _jsp_getInstanceManager(), _jspx_th_c_005fforEach_005f1_reused);
    }
    return false;
  }
}
