package co.edu.javeriana.as.personapp.mapper;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.model.request.TelefonoRequest;
import co.edu.javeriana.as.personapp.model.response.TelefonoResponse;
@Mapper
public class TelefonoMapperRest {
	public TelefonoResponse fromDomainToAdapterRestMaria(Phone phone) {
		return fromDomainToAdapterRest(phone, "MariaDB");
	}
	public TelefonoResponse fromDomainToAdapterRestMongo(Phone phone) {
		return fromDomainToAdapterRest(phone, "MongoDB");
	}
	public TelefonoResponse fromDomainToAdapterRest(Phone phone, String database) {
		return new TelefonoResponse(
				phone.getNumber(),
				phone.getCompany(),
				phone.getOwner().getIdentification()+"",
				database, "OK");
	}
	public Phone fromAdapterToDomain(TelefonoRequest request) {
		Phone phone = new Phone();
		phone.setNumber(request.getNum());
		phone.setCompany(request.getOper());
		Person owner = new Person();
		owner.setIdentification(Integer.parseInt(request.getDuenio()));
		owner.setGender(Gender.OTHER);
		owner.setFirstName("");
		owner.setLastName("");
		phone.setOwner(owner);
		return phone;
	}
}
