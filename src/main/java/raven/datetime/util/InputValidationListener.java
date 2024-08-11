package raven.datetime.util;

import java.time.LocalDate;

public interface InputValidationListener {

    boolean isValidation();

    void inputChanged(boolean isValid);

    boolean checkDateSelectionAble(LocalDate date);
}
