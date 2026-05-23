package co.edu.javeriana.as.personapp.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.as.personapp.adapter.EstudiosInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
import co.edu.javeriana.as.personapp.model.response.EstudiosResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/v1/estudios")
public class EstudiosControllerV1 {
	@Autowired
	private EstudiosInputAdapterRest estudiosInputAdapterRest;
	@ResponseBody
	@GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EstudiosResponse> estudios(@PathVariable String database) {
		log.info("Into estudios REST API");
		return estudiosInputAdapterRest.historial(database.toUpperCase());
	}
	@ResponseBody
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EstudiosResponse crearEstudio(@RequestBody EstudiosRequest request) {
		log.info("Into crearEstudio REST API");
		return estudiosInputAdapterRest.crearEstudio(request);
	}
}
