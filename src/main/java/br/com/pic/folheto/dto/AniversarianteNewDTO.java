package br.com.pic.folheto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AniversarianteNewDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String dataNascimento;
	
	private Integer idIgreja;

}