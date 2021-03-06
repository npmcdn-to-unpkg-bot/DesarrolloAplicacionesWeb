package es.urjc.code.daw.library.equipo;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping("/concejales")
public class ConcejalController {
	interface ConcejalDetalle extends Concejal.Basico,Concejal.Detalle{};
	
	private static final Logger log = LoggerFactory.getLogger(ConcejalController.class);

	@Autowired
	private ConcejalRepository repository;

	@JsonView(Concejal.Basico.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<Concejal> getConcejales() {
		return repository.findAll();
	}
	
	@JsonView(ConcejalDetalle.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Concejal> getConcejal(@PathVariable long id) {

		log.info("Get concejal {}", id);

		Concejal persona = repository.findOne(id);
		if (persona != null) {
			return new ResponseEntity<>(persona, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Concejal nuevoConcejal(@RequestBody Concejal persona) {

		repository.save(persona);

		return persona;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Concejal> actulizaConcejal(@PathVariable long id, @RequestBody Concejal updatedConcejal) {

		Concejal persona = repository.findOne(id);
		if (persona != null) {

			updatedConcejal.setId(id);
			repository.save(updatedConcejal);

			return new ResponseEntity<>(updatedConcejal, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Concejal> borraConcejal(@PathVariable long id) {

		if (repository.exists(id)) {
			repository.delete(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
