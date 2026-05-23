package co.edu.javeriana.as.personapp.mariadb.mapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;
@Mapper
public class EstudiosMapperMaria {
	public EstudiosEntity fromDomainToAdapter(Study study) {
		EstudiosEntityPK estudioPK = new EstudiosEntityPK();
		estudioPK.setCcPer(study.getPerson().getIdentification());
		estudioPK.setIdProf(study.getProfession().getIdentification());
		EstudiosEntity estudio = new EstudiosEntity();
		estudio.setEstudiosPK(estudioPK);
		estudio.setFecha(validateFecha(study.getGraduationDate()));
		estudio.setUniver(validateUniver(study.getUniversityName()));
		return estudio;
	}
	private Date validateFecha(LocalDate graduationDate) {
		return graduationDate != null
				? Date.from(graduationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
				: null;
	}
	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}
	public Study fromAdapterToDomain(EstudiosEntity estudiosEntity) {
		Study study = new Study();
		study.setPerson(buildPerson(estudiosEntity.getPersona(), estudiosEntity.getEstudiosPK().getCcPer()));
		study.setProfession(buildProfession(estudiosEntity.getProfesion(), estudiosEntity.getEstudiosPK().getIdProf()));
		study.setGraduationDate(validateGraduationDate(estudiosEntity.getFecha()));
		study.setUniversityName(validateUniversityName(estudiosEntity.getUniver()));
		return study;
	}
	private Person buildPerson(PersonaEntity personaEntity, Integer fallbackId) {
		Person person = new Person();
		if (personaEntity != null) {
			person.setIdentification(personaEntity.getCc());
			person.setFirstName(personaEntity.getNombre());
			person.setLastName(personaEntity.getApellido());
			person.setGender(personaEntity.getGenero() != null && personaEntity.getGenero() == 'F' ? Gender.FEMALE : Gender.MALE);
			person.setAge(personaEntity.getEdad());
			person.setStudies(new ArrayList<>());
			person.setPhoneNumbers(new ArrayList<>());
		} else {
			person.setIdentification(fallbackId);
			person.setGender(Gender.OTHER);
			person.setStudies(new ArrayList<>());
			person.setPhoneNumbers(new ArrayList<>());
		}
		return person;
	}
	private Profession buildProfession(ProfesionEntity profesionEntity, Integer fallbackId) {
		Profession profession = new Profession();
		if (profesionEntity != null) {
			profession.setIdentification(profesionEntity.getId());
			profession.setName(profesionEntity.getNom());
			profession.setDescription(profesionEntity.getDes());
			profession.setStudies(new ArrayList<>());
		} else {
			profession.setIdentification(fallbackId);
			profession.setStudies(new ArrayList<>());
		}
		return profession;
	}
	private LocalDate validateGraduationDate(Date fecha) {
		return fecha != null ? ((java.sql.Date) fecha).toLocalDate() : null;
	}
	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}
}
