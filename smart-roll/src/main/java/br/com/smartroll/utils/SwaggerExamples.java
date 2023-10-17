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

  public final static String GETENROLLEDSTUDENTS = """
      {
        "classmates": [
          {
            "registration": "117008301",
            "name": "Tais Bruno"
          },
          ...
          {
            "registration": "117008302",
            "name": "Natalia Bruno"
          }
        ]
      }
      """;

  public final static String GETCLASSESBYUSER = """
      {
        "classrooms": [
          {
            "classCode": "code1",
            "disciplineCode": "dcode1",
            "discipline": "Matemática",
            "teacher": "Professor A",
            "semester": "2023.1",
            "total": 30
          },
          ...
          {
            "classCode": "code2",
            "disciplineCode": "dcode2",
            "discipline": "Física",
            "teacher": "Professor B",
            "semester": "2023.1",
            "total": 30
          }
        ]
      }
      """;
}
