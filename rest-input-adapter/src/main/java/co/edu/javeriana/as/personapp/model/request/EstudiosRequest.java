package co.edu.javeriana.as.personapp.model.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudiosRequest {
	private String ccPer;
	private String idProf;
	private String fecha;
	private String univer;
	private String database;
}
