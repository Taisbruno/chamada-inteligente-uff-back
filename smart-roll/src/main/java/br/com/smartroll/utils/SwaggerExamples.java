package br.com.smartroll.utils;

/**
 * Classe que armazena os exemplos para anotações do swagger da API.
 */
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

  /**
   * LoginController.
   * Exemplo de retorno de json de login.
   */
  public final static String GETUSER = """
      {
        "registration": "string",
        "name": "string",
        "cpf": "string",
        "email": "string",
        "type": "string"
      }
        """;

  /**
   * LoginController.
   * Exemplo de corpo de json entrada para getHistoricRollsFromClass.
   */
  public final static String POSTLOGIN = """
          {
            "cpf": "3333",
            "password": "hugopassword"
          }""";

  /**
   * RollController.
   * Exemplo de corpo de json entrada para postRollByClass.
   */
  public final static String POSTROLL = """
      {
        "latitude": "-23.550520",
        "longitude": "-46.633308",
        "class_code": "1"
      }
        """;

  /**
   * RollController.
   * Exemplo de retorno de json para getRoll.
   */
  public final static String GETROLLEXAMPLE = """
          {
            "id": "string",
            "longitude": "string",
            "latitude": "string",
            "classCode": "string",
            "createdAt": "string",
            "finishedAt": "string"
            "isOpen": boolean
          }
          """;

  /**
   * RollController.
   * Exemplo de retorno de json para getRollsFromClass.
   */
  public final static String GETROLLSFROMCLASSEXAMPLE = """
          {
            "rolls": [
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string"
                "isOpen": boolean
              }
              ...
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string"
                "isOpen": boolean
              }
            ]
          }
          """;

  /**
   * RollController.
   * Exemplo de retorno de json para getHistoricRollsFromClass.
   */
  public final static String GETROLLSHISTORICEXAMPLE = """
          {
            "rolls": [
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string",
                "isOpen": boolean,
                "studentsPresent": int,
                "presencePercentage": int,
                "presenceTimeAvarage": "string",
                "presences": [
                  {
                    "id": "string",
                    "registration": "string",
                    "name": "string",
                    "medicalCertificate": "string",
                    "message": "string",
                    "isPresent": boolean,
                    "entryTime": "string",
                    "exitTime": "string",
                    "timePresent": "string",
                    "frequency": double,
                    "failed": boolean
                  },
                  ...
                  {
                    "id": "string",
                    "registration": "string",
                    "name": "string",
                    "medicalCertificate": "string",
                    "message": "string",
                    "isPresent": boolean,
                    "entryTime": "string",
                    "exitTime": "string",
                    "timePresent": "string",
                    "frequency": double,
                    "failed": boolean
                  }
                ]
              },
              ...
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string",
                "isOpen": boolean,
                "studentsPresent": int,
                "presencePercentage": int,
                "presenceTimeAvarage": "string",
                "presences": [
                  {
                    "id": "string",
                    "registration": "string",
                    "name": "string",
                    "medicalCertificate": "string",
                    "message": "string",
                    "isPresent": boolean,
                    "entryTime": "string",
                    "exitTime": "string",
                    "timePresent": "string",
                    "frequency": double,
                    "failed": boolean
                  },
                  ...
                  {
                    "id": "string",
                    "registration": "string",
                    "name": "string",
                    "medicalCertificate": "string",
                    "message": "string",
                    "isPresent": boolean,
                    "entryTime": "string",
                    "exitTime": "string",
                    "timePresent": "string",
                    "frequency": double,
                    "failed": boolean
                  }
                ]
              }
            ]
          }
          """;

  /**
   * StudentController.
   * Exemplo de retorno de json para getEnrolledStudentsByClass.
   */
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

  /**
   * ClassController.
   * Exemplo de retorno de json para getClassesByUser.
   */
  public final static String GETCLASSESBYUSER = """
      {
        "classrooms": [
          {
            "classCode": "string",
            "disciplineCode": "string",
            "discipline": "string",
            "teacher": "string",
            "semester": "string",
            "total": int,
            "rolls": [
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string",
                "isOpen": boolean
              },
              ...
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string",
                "isOpen": boolean
              }
            ]
          },
          ...
          {
            "classCode": "string",
            "disciplineCode": "string",
            "discipline": "string",
            "teacher": "string",
            "semester": "string",
            "total": int,
            "rolls": [
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string",
                "isOpen": boolean
              },
              ...
              {
                "id": "string",
                "longitude": "string",
                "latitude": "string",
                "classCode": "string",
                "createdAt": "string",
                "finishedAt": "string",
                "isOpen": boolean
              }
            ]
          }
        ]
      }
      """;

  /**
   * PresenceController.
   * Exemplo de corpo de json entrada para getClassesByUser.
   */
    public static final String POSTPRESENCE = """
            {
              "studentRegistration": "2",
              "rollId": "1",
              "message": "Estava presente"
            }
      """;

  /**
   * PresenceController.
   * Exemplo de corpo de json entrada para getClassesByUser.
   */
  public static final String PUTCERTIFICATE = """
              {
                "id": "1",
                "certificate": "iVBORw0KGgoAAAANSUhEUgAAAXAAAAIECAIAAACPIOIiAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQA"
              }
          """;

    public static final String POSTSCHEDULE = """
            {
                "classCode": "1",
                "dayOfWeek": 3,
                "startTime": "08:00:00",
                "endTime": "10:00:00",
                "longitude": "-49.060644",
                "latitude": "-26.922570"
            }    
            """;
}
