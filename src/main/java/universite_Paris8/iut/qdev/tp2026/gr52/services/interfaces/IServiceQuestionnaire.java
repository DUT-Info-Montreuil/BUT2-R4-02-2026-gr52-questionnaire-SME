package universite_Paris8.iut.qdev.tp2026.gr52.services.interfaces;

import java.util.List;
import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionnaireDTO;

public interface IServiceQuestionnaire {

    List<QuestionnaireDTO> chargerFichier(String cheminFichier);

    List<QuestionnaireDTO> fournirListeQuestionnaire();
}
