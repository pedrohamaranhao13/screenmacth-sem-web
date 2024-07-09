package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=" ;
    private final  String APIKEY = "&apikey=6585022c";

    public void exibeMenu() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(
        ENDERECO + nomeSerie.replace(" " ,"+" ) + APIKEY
        );
        DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dados);


        List<DadosTemporada> dadosTemporadas = new ArrayList<>();
		for (int i = 1; i<= dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" " ,"+" ) + "&season=" + i +  APIKEY);
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
			dadosTemporadas.add(dadosTemporada);
		}
		dadosTemporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = dadosTemporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        dadosTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

    }

}
