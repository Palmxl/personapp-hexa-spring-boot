package co.edu.javeriana.as.personapp.mongo.mapper;
import java.time.LocalDate;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;
import lombok.NonNull;
import java.util.ArrayList;
@Mapper
public class EstudiosMapperMongo {
	public EstudiosDocument fromDomainToAdapter(Study study) {
		EstudiosDocument estudio = new EstudiosDocument();
		estudio.setId(study.getPerson().getIdentification() + "-" + study.getProfession().getIdentification());
		estudio.setPrimaryPersona(buildPersonaDocument(study.getPerson()));
		estudio.setPrimaryProfesion(buildProfesionDocument(study.getProfession()));
		estudio.setFecha(study.getGraduationDate());
		estudio.setUniver(study.getUniversityName() != null ? study.getUniversityName() : "");
		return estudio;
	}
	private PersonaDocument buildPersonaDocument(@NonNull Person person) {
		PersonaDocument doc = new PersonaDocument();
		doc.setId(person.getIdentification());
		doc.setNombre(person.getFirstName() != null ? person.getFirstName() : "");
		doc.setApellido(person.getLastName() != null ? person.getLastName() : "");
		doc.setGenero(person.getGender() != null ? (person.getGender() == Gender.FEMALE ? "F" : "M") : "M");
		doc.setEdad(person.getAge());
		return doc;
	}
	private ProfesionDocument buildProfesionDocument(@NonNull Profession profession) {
		ProfesionDocument doc = new ProfesionDocument();
		doc.setId(profession.getIdentification());
		doc.setNom(profession.getName() != null ? profession.getName() : "");
		doc.setDes(profession.getDescription() != null ? profession.getDescription() : "");
		return doc;
	}
	public Study fromAdapterToDomain(EstudiosDocument estudiosDocument) {
		Study study = new Study();
		study.setPerson(buildPerson(estudiosDocument.getPrimaryPersona()));
		study.setProfession(buildProfession(estudiosDocument.getPrimaryProfesion()));
		study.setGraduationDate(estudiosDocument.getFecha());
		study.setUniversityName(estudiosDocument.getUniver() != null ? estudiosDocument.getUniver() : "");
		return study;
	}
	private Person buildPerson(PersonaDocument doc) {
		Person person = new Person();
		if (doc != null && doc.getId() != null) {
			person.setIdentification(doc.getId());
			person.setFirstName(doc.getNombre() != null ? doc.getNombre() : "");
			person.setLastName(doc.getApellido() != null ? doc.getApellido() : "");
			person.setGender("F".equals(doc.getGenero()) ? Gender.FEMALE : Gender.MALE);
			person.setAge(doc.getEdad());
		} else {
			person.setIdentification(0);
			person.setGender(Gender.OTHER);
		}
		person.setStudies(new ArrayList<>());
		person.setPhoneNumbers(new ArrayList<>());
		return person;
	}
	private Profession buildProfession(ProfesionDocument doc) {
		Profession profession = new Profession();
		if (doc != null && doc.getId() != null) {
			profession.setIdentification(doc.getId());
			profession.setName(doc.getNom() != null ? doc.getNom() : "");
			profession.setDescription(doc.getDes() != null ? doc.getDes() : "");
		} else {
			profession.setIdentification(0);
		}
		profession.setStudies(new ArrayList<>());
		return profession;
	}
}
