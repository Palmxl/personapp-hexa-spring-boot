package co.edu.javeriana.as.personapp.mariadb.mapper;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import lombok.NonNull;
@Mapper
public class TelefonoMapperMaria {
	public TelefonoEntity fromDomainToAdapter(Phone phone) {
		TelefonoEntity telefonoEntity = new TelefonoEntity();
		telefonoEntity.setNum(phone.getNumber());
		telefonoEntity.setOper(phone.getCompany());
		telefonoEntity.setDuenio(validateDuenio(phone.getOwner()));
		return telefonoEntity;
	}
	private PersonaEntity validateDuenio(@NonNull Person owner) {
		PersonaEntity personaEntity = new PersonaEntity();
		personaEntity.setCc(owner.getIdentification());
		personaEntity.setNombre(owner.getFirstName() != null ? owner.getFirstName() : "");
		personaEntity.setApellido(owner.getLastName() != null ? owner.getLastName() : "");
		personaEntity.setGenero(owner.getGender() != null ? (owner.getGender() == co.edu.javeriana.as.personapp.domain.Gender.FEMALE ? 'F' : 'M') : 'M');
		return personaEntity;
	}
	public Phone fromAdapterToDomain(TelefonoEntity telefonoEntity) {
		Phone phone = new Phone();
		phone.setNumber(telefonoEntity.getNum());
		phone.setCompany(telefonoEntity.getOper());
		phone.setOwner(validateOwner(telefonoEntity.getDuenio()));
		return phone;
	}
	private @NonNull Person validateOwner(PersonaEntity duenio) {
		if (duenio == null) return new Person();
		Person person = new Person();
		person.setIdentification(duenio.getCc());
		person.setFirstName(duenio.getNombre());
		person.setLastName(duenio.getApellido());
		person.setGender(duenio.getGenero() != null && duenio.getGenero() == 'F' ? Gender.FEMALE : Gender.MALE);
		return person;
	}
}
