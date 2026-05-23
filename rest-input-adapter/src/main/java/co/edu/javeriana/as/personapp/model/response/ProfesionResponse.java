package co.edu.javeriana.as.personapp.model.response;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
public class ProfesionResponse extends ProfesionRequest {
	private String status;
	public ProfesionResponse(String id, String nom, String des, String database, String status) {
		super(id, nom, des, database);
		this.status = status;
	}
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}
