# Backend do Projeto de Chamada Inteligente - UFF

O projeto Chamada Inteligente trata-se de um sistema desenvolvido na disciplina de Gerência de Projeto de Software - UFF para gerenciamento de uma chamada/lista de presença que está sendo desenvolvido para fins de um requisito parcial de avaliação, assim como concorre para ser implementado na Superintendência de Tecnologia da Informação (STI/UFF). 

* **Objetivo do projeto**:
  Fomentar o aprendizado de novas arquiteturas, tecnologias e métodos de desenvolvimento de produtos digitais através da experimentação.

* Data: 12/10/2023
* Versão atual: 1.0

## 1. Pré-Requisitos

### 1.1. Para desenvolvimento

* Windows 10 ou 11.
* [IntelliJ Community na última versão](https://www.jetbrains.com/idea/download/#section=windows)
* [Java 17.0.1](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Distribuição: Oracle
* Springboot 2.7.5 (Obtido diretamente pela IDE em conjunto com os subitens abaixo)
  * Mockito 4.6.1 
  * JUnit 5 
  * SpringFox 2 v. 3.0.0 
  * Outras menores configuradas no build do Maven (POM.xml)
* [Maven 3.8.6](https://maven.apache.org/download.cgi)
* [PostGreSQL VER VERSAO](VER LINK)

### 1.2. Somente para compilar e executar

* Em Sistema Operacional Windows
  * [Java 17.0.1](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Distribuição: Oracle
  * [Maven 3.8.6](https://maven.apache.org/download.cgi)
  * [Python 3.9.12](https://www.python.org/downloads/)
* [PostgreSQL VER VERSAO](VER LINK)

## 2. Instruções de construção, execução e uso da API

Antes de executar certifique-se de que o PostgreSQL está funcionando corretamente com o banco de dados montado (ver Seção [3](#3-banco-de-dados-do-projeto) deste documento).

Antes de construir o JAR para distribuição certifique-se de que o PostgreSQL esteja **sem autenticação** e com o banco de dados `smart_rolls` criado corretamente, pois os testes unitários são executados automaticamente quando compila-se o JAR para distribuição. Caso contrário, você deve desativar o processo de testes automáticos durante compilação do JAR.

### 2.1. Compilando o JAR para distribuição

Recomenda-se utilizar este modo de compilação para implantar o back-end em ambientes externos de produção, desenvolvidmento, controle de qualidade e etc. A compilação do pacote pode ser feita através do IntelliJ no Windows ou em qualquer ambiente com o comando:

```
mvn install
```

O executável da API será gerado no em `./target/smart-roll-<versao>.jar`. Automaticamente o maven irá agregar também a pasta de assets junto do `.jar`. Se você deseja contruir o projeto sem realizar os testes, então utilize:

```
mvn install -DskipTests
```

### 2.3. Construindo e Executando diretamente

**Atenção:** Como mencionado anteriormente, certifique-se de que o banco `smart_rolls` existe no MongoDB instalado no ambiente e que esteja **sem autenticação**. Veja a seção abaixo de banco de dados para ver instruções de construção do banco.

Apesar do projeto ser facilmente construído e executado através do Maven, este modo de compilação e execução somente é recomendado para desenvolvimento local. Dessa forma, o desenvolvedor pode usar o IntelliJ para executar em modo de desenvolvimento com ou sem debugger direto na própria interface da IDE. Você também pode compilar e executar de uma vez através do terminal no **diretório do POM.xml**:

```
mvn clean compile exec:java
```

### 2.4. Contruindo e Executando os testes unitários

O código fonte dos testes unitários está em `./src/test/`. Você pode executar diretamente pelo IntelliJ com ou sem o debugger (recomendado). Porém, se você deseja compilar e executar os testes unitários através do console, execute o seguinte comando:

```
mvn clean test-compile
```

Para executar uma classe inteira do teste de unitário utilize o seguinte comando, onde `[CLASSE]` é a respectiva classe implementada dos testes:

```
mvn -Dtest=[CLASSE] test
```

Para executar um método específico de alguma classe dos testes unitários, utilize o seguinte comando, onde `[CLASSE]` é a respectiva classe e `[METODO]` o respectivo método do Junit.

```
mvn -Dtest=[CLASSE]$[METODO] test
```

### 2.5. Usando a API

Uma vez com a API levantada no sistema, o uso dela se da através de HTTP ou HTTPS dependendo da configuração utilizada. Se a configuração não for de produção, acesse a página `/swagger-ui/` para visualizar os comandos da API.

```
<https/http>://<ip da api>:<porta da api>/swagger-ui/
```

O formato do comando deve estar documentado no próprio swagger.

## 3. Banco de Dados do Projeto

O banco de dados relacional deste projeto é hospedado no PostgreSQL. Para desenvolvimento, recomenda-se hospedar o banco de dados localmente na porta 5432 com autenticação desligada, objetivando homogenizar a configuração padrão de execução da API para desenvolvimento. O nome utilizado por padrão é `smart_rolls`, devendo ser alterado para qualquer outro nos ambientes externos que hospedam versões de produção ou controle de qualidade.

```
postgresql://<user>:<password>@<host>:<port>/<database-name>
```

Uma vez com o PostgreSQL implantado localmente ou em máquina externa, o banco de dados do projeto pode ter a criação de tabelas através biblioteca JPA por meio das anotações nas entidades definidas em ./src/main/java/repository/entity/, porém, certifique-se de antes ter rodado o script de criação do banco `smart_rolls`, bem como criação do usuário superuser padrão do projeto com usuário `smart` e senha `smart2552` contido em ./tools/database-scripts.sql.

Vale a pena enfatizar que o script de criação do banco de dados agrega um usuário administrador para o banco com a seguinte credencial:

* username: smart
* senha: smart2552


## 4. Testes Unitário do Projeto

O código deste projeto é testado através de uma componente de teste unitário que fazem a cobertura parcial das componentes presentes no sistema. O teste de unidade utiliza a biblioteca do JUnit integrada no SpringBoot e todo seu código se encontra em `src/test`. A documentação que fornece a modelagem e auxilia no planejamento dos testes é feita diretamente no código-fonte dos testes.

Antes de executar os testes, certifique-se que o serviço do PostgreSQL está ligado **sem autenticação** e que o banco de dados `smart_rolls` **não** existe.

Os testes unitários são executados automaticamente na construção de qualquer JAR para a distribuição do projeto pelo *script* agregado. Qualquer outro detalhe para execução dos testes unitários pode ser vista na respectiva seção de execução dos testes relacionada anteriormente neste documento. Toda a implementação dos testes devem estar documentadas, seguindo fielmente a estrutura dos demais testes. Os seguintes princípios de boas práticas devem ser aplicados:

* Os testes devem utilizar o código source da aplicação como caixa preta.
  * As unidades são os métodos de alguma classe testada do código-fonte.
  * Cada método do código-fonte deve ser testado como caixa preta, visando o esquema de entrada/saída.
  * Fluxo extraordinário (exceções) devem ser testados com a injeção de um componente mockado na classe original testada.
* Todos os testes devem:
  * Ser independentes entre si;
  * Ser determinísticos;
  * Introduzir pouco overhead de tempo de execução e uso de memória comparado ao código fonte testado.
  * Quando necessário, utilizar por padrão um log do sistema apontado para o arquivo `JUnit.log`. **TODO**
* Uma classe do teste unitário deve implementar uma feature. Cada feature deve:
  * Ter uma chave única. Exemplo: F-UserController, F-CallController e F-StudentRepository.
  * Cobrir uma respectiva classe do código-fonte.
  * Possuir um título e descrição.
  * Ser dividida em cenários.
    * Cada cenário deve ter uma chave única dentro da **feature**. Exemplo: C001, C002 e C003.
    * Deve ser implementada através de um único método.
    * Deve possuir uma descrição com uma tabela que relaciona as entradas usadas e o resultado que está sendo validado.
* Não é necessário usar JavaDoc.
* Qualquer teste que necessite utilizar uma informação externa deve:
  * Utilizar somente arquivos no disco localizados no diretório `./src/test/resources/`;
  * Utilizar somente informações presentes no banco de dados `smart_rolls` do PostgreSQL, populando corretamente antes da execução do teste e limpando apropriadamente após a execução.

## 7. Estrutura de Pastas do Repositório

Este repositório está estruturado da seguinte forma:

* src - Código fonte da aplicação de integração
  * src/main/ - Fonte principal da aplicação
  * src/test/ - Fonte do teste de unidade
* target - Contém os arquivos temporários gerados pelo maven durante construção da API.
* .idea - Contém os arquivos relacionados a IDE (IntelliJ)

## 8. Boas práticas para desenvolvimento

* Padronizar todos os nomes de variáveis e métodos em inglês.
* Documentação em português. Os métodos e classes devem ser devidamente documentados.
* Nome de grandes entidades (como Usuario, Validador, Formularios) devem ficar em português. As classes que usam o nome dessas entidades devem estar em português.
* As respostas em JSON da RestAPI devem estar com *keys* em português.
* Na hora de importar bibliotecas para o código, deve seguir a ordem: PRIMEIRO imports de classes internas do projeto e SEGUNDO imports de classes de bibliotecas padrões do JAVA e TERCEIRO bibliotecas de pacotes externos. Colocar um caracter de linha nova entre cada grupo de import.
* Manter a padronização de identanção e blocos (chaves, colchetes) de acordo com a cultura local do arquivo.
* Cobertura adequada de código pelo teste unitário.
* Os métodos e classes devem iniciar com nomes maiúsculos seguindo padrão SnakeCase.
* Os atriutos devem iniciar com nomes minúsculos seguindo padrão camelCase.
* Variáveis usadas localmente dentro de cada método têm padrão livre, podendo ser camelCase ou separado_por_underline, porém devem iniciar com leta minúscula.
* Todos nomes de classes, métodos, variáveis e atributos devem expressar intuitivamente o motivo de sua existência.
* Os atributos devem ser declados com escopo:
  * Private se ela é utilizada SOMENTE dentro da classe em que está declarada.
  * Protected se ela é utilizada SOMENTE dentro da classe em que está declarada e suas subclasses.
  * Private/Protected com o uso de getters/setters quando algum tipo de processamento é feito sobre elas pela classe responsável. Este processamento pode ser tanto durante o próprio setter ou getter quanto administrado em outro método.
  * Public se ela é utilizada fora da classe que está declarada, porém não tem nenhum tipo de processamento feito pela classe responsável.
* Os métodos devem ser declados com escopo:
  * Private se ela é utilizada somente dentro da classe em que está declarada.
  * Protected se ela é utilizada somente dentro da classem em que está declarada e suas subclasses.
  * Public se ela é utiliza fora da classe.
* Manter a implemetação o mais fiel possível à modelagem feita em UML presente no Confluence (ver link do Jira) deste projeto.


