package co.edu.javeriana.as.personapp.model.response;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
public class EstudiosResponse extends EstudiosRequest {
	private String status;
	public EstudiosResponse(String ccPer, String idProf, String fecha, String univer, String database, String status) {
		super(ccPer, idProf, fecha, univer, database);
		this.status = status;
	}
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}
