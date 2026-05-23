package co.edu.javeriana.as.personapp.terminal.mapper;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.EstudiosModelCli;
@Mapper
public class EstudiosMapperCli {
	public EstudiosModelCli fromDomainToAdapterCli(Study study) {
		EstudiosModelCli model = new EstudiosModelCli();
		model.setCcPer(study.getPerson().getIdentification());
		model.setIdProf(study.getProfession().getIdentification());
		model.setFecha(study.getGraduationDate() != null ? study.getGraduationDate().toString() : "");
		model.setUniver(study.getUniversityName());
		return model;
	}
}
