package br.com.pic.folheto.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pic.folheto.dto.DevocionalNewDTO;
import br.com.pic.folheto.entidades.Devocional;
import br.com.pic.folheto.services.DevocionalService;

@RestController
@RequestMapping(value="/devocionais")
public class DevocionalController {

	@Autowired
	private DevocionalService devocionalService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todos os devocionais", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findAll() {
		return ResponseEntity.ok().body(devocionalService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar devocionais por semana", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/igreja/{idIgreja}/{idMembro}", method = RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findPorIgreja(@PathVariable final Integer idIgreja,@PathVariable final Integer idMembro) {
		return ResponseEntity.ok().body(devocionalService.buscarPorIgreja(idIgreja, idMembro));
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar devocional antigos por igreja", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/antigos/igreja/{idIgreja}/{idMembro}", method = RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findDevocionaisAntigosPorIgreja(@PathVariable final Integer idIgreja,@PathVariable final Integer idMembro) {
		return ResponseEntity.ok().body(devocionalService.buscarDevocionaisAntigosPorIgreja(idIgreja, idMembro));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Salvar devocional", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody final DevocionalNewDTO dto) {
		final Devocional obj = devocionalService.salvar(dto);
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Delete devocional", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable final Integer id) {
		devocionalService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}