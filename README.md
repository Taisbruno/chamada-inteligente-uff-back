# Backend do Projeto de Chamada Inteligente - UFF

O projeto Chamada Inteligente trata-se de um sistema desenvolvido na disciplina de Gerência de Projeto de Software - UFF para gerenciamento de uma chamada/lista de presença que está sendo desenvolvido para fins de um requisito parcial de avaliação, assim como concorre para ser implementado na Superintendência de Tecnologia da Informação (STI/UFF). 

* **Objetivo do projeto**:
  Fomentar o aprendizado de novas arquiteturas, tecnologias e métodos de desenvolvimento de produtos digitais através da experimentação e aplicação de conceitos aprendidos em aula.

* Data: 05/11/2023
* Versão atual: 1.0

## 1. Pré-Requisitos

### 1.1. Para desenvolvimento

* Windows 10 ou 11.
* [IntelliJ Community na última versão](https://www.jetbrains.com/idea/download/#section=windows)
* [Java 17.0.1](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Distribuição: Oracle
* Springboot 2.7.5 (Obtido diretamente pela IDE em conjunto com os subitens abaixo)
  * SpringFox 2 v. 3.0.0 
  * Outras dependências configuradas no build do Maven (POM.xml)
* [Maven 3.8.6](https://maven.apache.org/download.cgi)
* [PostgreSQL na última versão](https://www.postgresql.org/download/)

### 1.2. Somente para compilar e executar

* Em Sistema Operacional Windows
  * [Java 17.0.1](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Distribuição: Oracle
  * [Maven 3.8.6](https://maven.apache.org/download.cgi)
* [PostgreSQL na última versão](https://www.postgresql.org/download/)

## 2. Instruções de construção, execução e uso da API

Antes de executar certifique-se de que o PostgreSQL está funcionando corretamente com o banco de dados montado (ver Seção [3](#3-banco-de-dados-do-projeto) deste documento).

Antes de construir o JAR para distribuição certifique-se de que o PostgreSQL esteja **sem autenticação** e com o banco de dados `smart_rolls` criado corretamente, pois os testes unitários são executados automaticamente quando compila-se o JAR para distribuição. Caso contrário, você deve desativar o processo de testes automáticos durante compilação do JAR.

### 2.1. Compilando o JAR para distribuição

Recomenda-se utilizar este modo de compilação para implantar o back-end em ambientes externos de produção, desenvolvidmento, controle de qualidade e etc. A compilação do pacote pode ser feita através do IntelliJ no Windows ou em qualquer ambiente com o comando:

```
mvn install
```

O executável da API será gerado no em `./target/smart-roll-<versao>.jar`. Automaticamente o maven irá agregar também a pasta de assets junto do `.jar`. 

### 2.2. Construindo e Executando diretamente

**Atenção:** Como mencionado anteriormente, certifique-se de que o banco `smart_rolls` existe no MongoDB instalado no ambiente e que esteja **sem autenticação**. Veja a seção abaixo de banco de dados para ver instruções de construção do banco.

Apesar do projeto ser facilmente construído e executado através do Maven, este modo de compilação e execução somente é recomendado para desenvolvimento local. Dessa forma, o desenvolvedor pode usar o IntelliJ para executar em modo de desenvolvimento com ou sem debugger direto na própria interface da IDE. Você também pode compilar e executar de uma vez através do terminal no **diretório do POM.xml**:

```
mvn clean compile exec:java
```

### 2.3. Usando a API

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

## 4. Estrutura de Pastas do Repositório

Este repositório está estruturado da seguinte forma:

* src - Código fonte da aplicação de integração
  * src/main/ - Fonte principal da aplicação
  * src/test/ - Fonte do teste de unidade
* target - Contém os arquivos temporários gerados pelo maven durante construção da API.
* .idea - Contém os arquivos relacionados a IDE (IntelliJ)

## 5. Boas práticas para desenvolvimento

* Padronizar todos os nomes de variáveis e métodos em inglês.
* Documentação em português. Os métodos e classes devem ser devidamente documentados.
* Nome de grandes entidades (como User, Roll, Presence) devem ficar em inglês. As classes que usam o nome dessas entidades devem estar em inglês.
* As respostas em JSON da RestAPI devem estar com *keys* em inglês.
* Na hora de importar bibliotecas para o código, deve seguir a ordem: PRIMEIRO imports de classes internas do projeto e SEGUNDO imports de classes de bibliotecas padrões do JAVA e TERCEIRO bibliotecas de pacotes externos. Colocar um caracter de linha nova entre cada grupo de import.
* Manter a padronização de identanção e blocos (chaves, colchetes) de acordo com a cultura local do arquivo.
* As classes devem iniciar com nomes maiúsculos seguindo padrão SnakeCase.
* Os atriutos e métodos devem iniciar com nomes minúsculos seguindo padrão camelCase.
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

## 6. Links relacionados

Repositório referente ao Frontend do Projeto: https://github.com/Taisbruno/chamada-inteligente-uff

Link do Github Issues do Projeto: https://github.com/users/Taisbruno/projects/1

