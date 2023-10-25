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
        "registration": "string",
        "name": "string",
        "cpf": "string",
        "email": "string",
        "type": "string"
      }
        """;

  public final static String POSTROLL = """
      {
        "latitude": "-23.550520",
        "longitude": "-46.633308",
        "class_code": "1"
      }
        """;

  public final static String GETROLLEXAMPLE = """
          {
            "id": "string",
            "longitude": "string",
            "latitude": "string",
            "createdAt": "string",
            "finishedAt": "string"
          }
          """;

  public final static String GETROLLSFROMCLASSEXAMPLE = """
          {
            "rolls": [
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "createdAt": "string",
                "finishedAt": "string"
              }
              ...
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "createdAt": "string",
                "finishedAt": "string"
              }
            ]
          }
          """;

  public final static String GETROLLSHISTORICEXAMPLE = """
            {
              "rolls": [
                {
                  "id": "string",
                  "longitude": "string",
                  "latitude": "string",
                  "createdAt": "string",
                  "finishedAt": "string",
                  "studentsPresent": int,
                  "presencePercentage": double,
                  "presenceTimeAvarage": "string",
                  "presences": [
                    {
                      "registration": "string",
                      "name": "string",
                      "medicalCertificate": "string",
                      "message": "string",
                      "isPresent": boolean,
                      "timePresent": "string",
                      "frequency": int,
                      "failed": boolean
                    },
                    ...
                    {
                      "registration": "string",
                      "name": "string",
                      "medicalCertificate": "string",
                      "message": "string",
                      "isPresent": boolean,
                      "timePresent": "string",
                      "frequency": int,
                      "failed": boolean
                    }
                  ]
                },
                ...
                {
                  "id": "string",
                  "longitude": "string",
                  "latitude": "string",
                  "createdAt": "string",
                  "finishedAt": "string",
                  "studentsPresent": int,
                  "presencePercentage": double,
                  "presenceTimeAvarage": "string",
                  "presences": [
                    {
                      "registration": "string",
                      "name": "string",
                      "medicalCertificate": "string",
                      "message": "string",
                      "isPresent": boolean,
                      "timePresent": "string",
                      "frequency": int,
                      "failed": boolean
                    },
                    ...
                    {
                      "registration": "string",
                      "name": "string",
                      "medicalCertificate": "string",
                      "message": "string",
                      "isPresent": boolean,
                      "timePresent": "string",
                      "frequency": int,
                      "failed": boolean
                    }
                  ]
                }
              ]
            }
          """;

  public final static String POSTLOGIN = """
          {
            "cpf": "3333",
            "password": "hugopassword"
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

    public static final String POSTPRESENCE = """
            {
              "studentRegistration": "2",
              "rollId": "1",
              "message": "Estava presente"
            }
      """;

  public static final String PUTCERTIFICATE = """
              {
                "id": "1",
                "certificate": "iVBORw0KGgoAAAANSUhEUgAAAXAAAAIECAIAAACPIOIiAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQA"
              }
          """;
  public static final String POSTPRESENCEEXAMPLE1 = """ 
            {
              "studentRegistration": "string",
              "rollId": "string",
              "message": "string"
            }
          """;
  public static final String POSTPRESENCEEXAMPLE2 = """ 
            {
              "studentRegistration": "string",
              "rollId": "string",
              "certificate": "string",
              "message": "string"
            }
          """;
}
