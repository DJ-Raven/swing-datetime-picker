package raven.datetime.util;

public interface InputValidationListener<T> {

    boolean isValidation();

    void inputChanged(boolean isValid);

    boolean checkSelectionAble(T data);
}
