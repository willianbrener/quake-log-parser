# LEIA-ME #

* Este documento visa explicar o projeto como um todo e resolver algumas dúvidas sobre a execução.

### Para que serve esse projeto? ###
* O projeto visa realizar a leitura de um arquivo .log gerado pelo servidor do jogo Quake 3 e através desta leitura verifica alguns eventos durante os jogos. Aqui é usado ​​como linguagem de programação o Java, em um projeto Maven para o caso de dependências e testes. Usa o arquivo comum que lê a biblioteca Java Dickey com expressão regular. Versão 0.0.1

### Como faço para configurar? ###
* Basicamente para "executar" o aplicativo principal (Parser), basta clicar em Run Java Application, levando em consideração o fato de que estamos usando o Eclipse ou qualquer IDE semelhante. Se você não usar, basta ter o Java instalado no seu computador e digitando a seguinte linha de comando "javac ReadWithScanner.java" e depois "java ReadWithScanner.class", visto que o caminho do .log deve ser especificado na variável filePathOfLog ReadWithScanner class.

### Configuração ###
* Java 
* >Jdk 7 ou mais atualizado.
* >Eclipse ou semelhante.
* Dependências
* >Sem.
* Configuração do banco de dados
* >Sem.
* Testes
* >mvn test.

--------------------------------------------------------------------------------------------------------------------------------
# README #

* This document aims to explain the project as a whole and address some doubts about the execution.

### What is this repository for? ###
* The project aims to carry out the reading of a file .log generator for the game Quake 3 and through this reading check some events during the games.
Here are used as the Java programming language , in a Maven project due to the fact that there is the occurrence of downloading dependencies, it will be easier.
It used the common file reading library Java Dickey with Regular Expression.
Version *0.0.1*

### How do I get set up? ###
* Basically to " run " the main application ( Parser ) , just click on Run Java Application taking into account the fact that we are using the Eclipse IDE. If you do not use , just have Java installed on your computer and by typing the following command line "javac ReadWithScanner.java" and then "java ReadWithScanner.class" , always seeking the path of .log must be specified in the variable filePathOfLog ReadWithScanner class.

### Configuration ###
* >Jdk 7 or higher.
* >Eclipse.
* Dependencies
* >Without.
* Database configuration
* >Without.
* How to run tests
* >mvn test.
