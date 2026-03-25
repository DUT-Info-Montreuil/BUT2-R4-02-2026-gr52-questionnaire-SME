package universite_Paris8.iut.qdev.tp2026.gr52.services.interfaces;

import java.io.InputStream;
import java.util.List;
import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions.*;

public interface IServiceQuestionnaire {

    InputStream chargerFichier(String cheminFichier) throws MissingFileException, WrongFileFormatException;

    List<QuestionnaireDTO> fournirListeQuestionnaire(InputStream csvFile) throws FileAccessDeniedException, EmptyQuestionnaireException, WrongDataTypeException, DuplicatedQuestionException;
}
