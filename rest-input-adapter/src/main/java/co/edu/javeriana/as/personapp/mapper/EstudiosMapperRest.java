package co.edu.javeriana.as.personapp.mapper;
import java.time.LocalDate;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
import co.edu.javeriana.as.personapp.model.response.EstudiosResponse;
@Mapper
public class EstudiosMapperRest {
	public EstudiosResponse fromDomainToAdapterRestMaria(Study study) {
		return fromDomainToAdapterRest(study, "MariaDB");
	}
	public EstudiosResponse fromDomainToAdapterRestMongo(Study study) {
		return fromDomainToAdapterRest(study, "MongoDB");
	}
	public EstudiosResponse fromDomainToAdapterRest(Study study, String database) {
		return new EstudiosResponse(
				study.getPerson().getIdentification()+"",
				study.getProfession().getIdentification()+"",
				study.getGraduationDate() != null ? study.getGraduationDate().toString() : "",
				study.getUniversityName(),
				database, "OK");
	}
	public Study fromAdapterToDomain(EstudiosRequest request) {
		Study study = new Study();
		Person person = new Person();
		person.setIdentification(Integer.parseInt(request.getCcPer()));
		Profession profession = new Profession();
		profession.setIdentification(Integer.parseInt(request.getIdProf()));
		study.setPerson(person);
		study.setProfession(profession);
		study.setUniversityName(request.getUniver());
		study.setGraduationDate(request.getFecha() != null && !request.getFecha().isEmpty() ?
				LocalDate.parse(request.getFecha()) : null);
		return study;
	}
}
