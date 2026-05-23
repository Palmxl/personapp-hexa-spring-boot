package co.edu.javeriana.as.personapp.terminal.mapper;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
@Mapper
public class TelefonoMapperCli {
	public TelefonoModelCli fromDomainToAdapterCli(Phone phone) {
		TelefonoModelCli model = new TelefonoModelCli();
		model.setNum(phone.getNumber());
		model.setOper(phone.getCompany());
		model.setDuenio(phone.getOwner().getIdentification());
		return model;
	}
}
