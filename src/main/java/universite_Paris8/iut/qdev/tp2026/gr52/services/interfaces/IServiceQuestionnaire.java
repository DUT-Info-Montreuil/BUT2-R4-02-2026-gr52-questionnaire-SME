package universite_Paris8.iut.qdev.tp2026.gr52.services.interfaces;

import java.io.File;
import java.util.List;
import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionnaireDTO;

public interface IServiceQuestionnaire {

    public File chargerFichier(String cheminFichier);

    public List<QuestionnaireDTO> fournirListeQuestionnaire(File csvFile);
}
