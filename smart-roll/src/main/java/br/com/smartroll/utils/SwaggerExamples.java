package br.com.smartroll.utils;

public class SwaggerExamples {
    /**
     * HomeController.
     * Página html utilizada para o redirecionamento ao Swagger da API.
     */
    public static final String REDIRECTPAGE = """
            <!DOCTYPE html>
            	<html lang="en">
            		<head>
            		<meta http-equiv="refresh" content="1; URL=/swagger-ui/">
            		</head>
            		<body bgcolor="#7FFFD4">
                    <center><h1><p>Você será redirecionado automaticamente, aguarde...<p>&#8987</p><a href="doxygen/html/index.html"</a></p></h1></center>
            		</body>
            	</html>
            """;

    public final static String GETENROLLEDSTUDENTS = """
            {
              "classmates": [
                {
                  "registration": "117008301",
                  "name": "Tais Bruno"
                },
                {
                  "registration": "117008302",
                  "name": "Natalia Bruno"
                }
              ]
            }           
            """;
}
