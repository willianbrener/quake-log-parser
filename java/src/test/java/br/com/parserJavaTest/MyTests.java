package br.com.parserJavaTest;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.parserJava.ReadWithScanner;

public class MyTests {

  
  @Test
  public void filePathNull() {

    
    ReadWithScanner tester = new ReadWithScanner(null);

    assertEquals("Caminho do arquivo nulo.", null, tester);
  }
  
  @Test
  public void filePathInvalid() {

    
    ReadWithScanner tester = new ReadWithScanner("C:\\Invalidqpweqwpeqwe");

    assertEquals("Caminho do arquivo de log inválido.", null, tester);
  }
  

} 

