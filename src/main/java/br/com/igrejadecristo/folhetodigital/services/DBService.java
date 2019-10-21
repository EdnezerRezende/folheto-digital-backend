package br.com.igrejadecristo.folhetodigital.services;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoIgreja;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoMembro;
import br.com.igrejadecristo.folhetodigital.entidades.Estado;
import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;
import br.com.igrejadecristo.folhetodigital.respositories.CidadeRepository;
import br.com.igrejadecristo.folhetodigital.respositories.EstadoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.FolhetoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;

@Service
public class DBService {

//	@Autowired
//	private BCryptPasswordEncoder pe;
	
	@Autowired
	public MembroRepository membroRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private IgrejaRepository igrejaRepository;

	@Autowired
	private FolhetoRepository folhetoRepository;
	
	@Autowired
	private MensagemRepository mensagemRepository;
	
	public void instantiateTestDatabase() throws ParseException{
		Estado brasilia = new Estado(null, "Brasília");
		
		Cidade cidadeIgreja = new Cidade(null, "Taguatinha Sul", brasilia);
		
		Igreja igreja1 = new Igreja(null, "Primeira Igreja de Cristo","22782170000108");
		EnderecoIgreja enderecoIgreja = new EnderecoIgreja(null, "QSB 10/11 A.E. 09", "", "", "", "72015600", igreja1, cidadeIgreja);
		igreja1.setEndereco(enderecoIgreja);

		Membro membro1 = new Membro(null, "Teste", "teste@gmail.com", "12345678978","1234", igreja1);
		Membro membro2 = new Membro(null, "Teste 2", "teste2@gmail.com", "78945612378","4321", igreja1);
		membro1.addPerfil(Perfil.ADMIN);
		membro2.addPerfil(Perfil.PASTOR);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		membro1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		membro2.getTelefones().addAll(Arrays.asList("93883321", "34252625"));
		
		EnderecoMembro e1 = new EnderecoMembro(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", membro1, c1);
		EnderecoMembro e2 = new EnderecoMembro(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", membro1, c2);
		EnderecoMembro e3 = new EnderecoMembro(null, "Avenida Floriano", "2106", null, "Centro", "281777012", membro2, c2);
		EnderecoMembro e4 = new EnderecoMembro(null, "Avenida Floriano", "2244", null, "Centro", "281777012", membro2, c2);
		
		estadoRepository.saveAll(Arrays.asList(est1, est2, brasilia));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, cidadeIgreja));
		
		igrejaRepository.saveAll(Arrays.asList(igreja1));
		
		membro1.getEnderecos().addAll(Arrays.asList(e1, e2));
		membro2.getEnderecos().addAll(Arrays.asList(e3, e4));

		membroRepository.saveAll(Arrays.asList(membro1,membro2));
		
		Folheto folheto1 = new Folheto(null, LocalDate.now(), igreja1);
		folhetoRepository.saveAll(Arrays.asList(folheto1));
		
		Mensagem mensagem1 = new Mensagem(null, 
				"Costa Neto é pastor de uma das maiores igrejas de Fortaleza, a Comunidade Cristã Videira. "
				+ "Para se ter uma ideia, por final de semana, a igreja conta com o trabalho "
				+ "de cerca de 2.500 voluntários.", "Pra Renata Cabral", LocalDate.now(),folheto1 );
		mensagemRepository.save(mensagem1);
	}
}