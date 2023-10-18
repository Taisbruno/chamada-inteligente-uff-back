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

  public final static String GETUSER = """
      {
        "registration": "1111",
        "name": "Hugo",
        "email": "hugo@example.com",
        "type": "student"
      }
        """;

  public final static String POSTLOGIN = """
          {
            "cpf": "1111",
            "password": "hugopassword"
          }""";

  public final static String POSTLOGINEXAMPLE = """
          {
            "cpf": "string",
            "password": "string"
          }""";

  public final static String GETENROLLEDSTUDENTS = """
      {
        "classmates": [
          {
            "registration": "string",
            "name": "string"
          },
          ...
          {
            "registration": "string",
            "name": "string"
          }
        ]
      }
      """;

  public final static String GETCLASSESBYUSER = """
      {
        "classrooms": [
          {
            "classCode": "string",
            "disciplineCode": "string",
            "discipline": "string",
            "teacher": "string",
            "semester": "string",
            "total": int
          },
          ...
          {
            "classCode": "string",
            "disciplineCode": "string",
            "discipline": "string",
            "teacher": "string",
            "semester": "string",
            "total": int
          }
        ]
      }
      """;
}
