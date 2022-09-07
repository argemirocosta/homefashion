package br.com.homefashion.util;

import br.com.homefashion.model.Endereco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CEPWebService {

	private static String pesquisarCepNaViaCep(String cep) {
		String json;

		try {
			URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder jsonSb = new StringBuilder();

			br.lines().forEach(l -> jsonSb.append(l.trim()));

			json = jsonSb.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return json;
	}

	public static Endereco buscarCep(String cep) {
		String json = pesquisarCepNaViaCep(cep);

		Map<String,String> mapa = new HashMap<>();

		Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
		while (matcher.find()) {
			String[] group = matcher.group().split(":");
			mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
		}

		Endereco endereco = new Endereco();
		endereco.setEstado(mapa.get("uf"));
		endereco.setCidade(mapa.get("localidade"));
		endereco.setBairro(mapa.get("bairro"));
		endereco.setLogradouro(mapa.get("logradouro"));
		endereco.setCodIBGE(Integer.parseInt(mapa.get("ibge")));

		return endereco;
	}

}