package com.martinpc.projects;

import com.martinpc.projects.persistence.integration.audiodb.ApiConfig;
import com.martinpc.projects.persistence.integration.audiodb.model.Book;
import com.martinpc.projects.persistence.integration.audiodb.model.BookReponse;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiconsumerApplication implements CommandLineRunner {

  private final ApiConfig apiConfig;
  private final Scanner scanner = new Scanner(System.in);

  public ApiconsumerApplication(ApiConfig apiConfig) {
    this.apiConfig = apiConfig;
  }

  public static void main(String[] args) {
    SpringApplication.run(ApiconsumerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    BookReponse data = apiConfig.obtenerData(null, BookReponse.class);
    System.out.println("10 Libros mas Descargados");
    data.results().stream()
        .sorted(Comparator.comparing(Book::downloads).reversed())
        .limit(10)
        .map(l -> l.title().toUpperCase())
        .forEach(System.out::println);

    // Libros con Autores con nacidos despues de 1890
    System.out.println();
    data.results().stream()
        .filter(a -> a.autors().stream()
            .anyMatch(f -> {
              try {
                return Integer.parseInt(f.birthDate()) > 1890;
              } catch (NumberFormatException e) {
                return false;
              }
            }))
        .forEach(System.out::println);

    System.out.println();

    DoubleSummaryStatistics est = data.results().stream()
        .filter(d -> d.downloads() > 0)
        .collect(Collectors.summarizingDouble(Book::downloads));
    System.out.println();

    System.out.println("Cantidad media de Descargas : " + est.getAverage());
    System.out.println("Cantidad maxima de Descargas: " + est.getMax());
    System.out.println("Cantidad minima de Descargas: " + est.getMin());
    System.out.println("Cantidad de libros consultados: " + est.getCount());

    System.out.println();

    System.out.print("Libro a buscar: ");
    String bookName = scanner.nextLine();
    BookReponse searchResult = apiConfig.obtenerData("search=" + bookName.replace(" ", "+"),
        BookReponse.class);
    Optional<Book> bookSearch = searchResult.results().stream()
        .filter(l -> l.title().toUpperCase().contains(bookName.toUpperCase()))
        .findFirst();
    if (bookSearch.isPresent()) {
      System.out.print("Libro Encontrado: " + bookSearch.get());
    } else {
      System.out.println("Libro no encontrado");
    }
  }
}
