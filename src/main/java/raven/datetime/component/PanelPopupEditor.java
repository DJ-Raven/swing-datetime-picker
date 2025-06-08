package raven.datetime.component;

import com.formdev.flatlaf.FlatClientProperties;
import raven.datetime.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public abstract class PanelPopupEditor extends JPanel {

    protected JFormattedTextField editor;
    protected JPopupMenu popupMenu;

    protected boolean editorValidation = true;
    protected boolean isValid;
    protected boolean validationOnNull;
    protected String defaultPlaceholder;
    protected Point popupSpace = new Point(1, 1);

    protected LookAndFeel oldThemes = UIManager.getLookAndFeel();

    public PanelPopupEditor() {
    }

    public void showPopup() {
        showPopup(editor);
    }

    public void showPopup(Component component) {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            popupMenu.putClientProperty(FlatClientProperties.STYLE, "" +
                    "borderInsets:1,1,1,1");
            popupMenu.add(this);
        }
        if (UIManager.getLookAndFeel() != oldThemes) {
            // component in popup not update UI when change themes
            // so need to update when popup show
            SwingUtilities.updateComponentTreeUI(popupMenu);
            oldThemes = UIManager.getLookAndFeel();
        }
        Point point = Utils.adjustPopupLocation(popupMenu, component, popupSpace);
        popupOpen();
        popupMenu.show(component, point.x, point.y);
    }

    public void closePopup() {
        if (popupMenu != null) {
            popupMenu.setVisible(false);
            repaint();
        }
    }

    public boolean isEditorValidation() {
        return editorValidation;
    }

    public void setEditorValidation(boolean editorValidation) {
        if (this.editorValidation != editorValidation) {
            this.editorValidation = editorValidation;
            if (editor != null) {
                if (editorValidation) {
                    validChanged(editor, isValid);
                } else {
                    validChanged(editor, true);
                }
            }
        }
    }

    public boolean isValidationOnNull() {
        return validationOnNull;
    }

    public void setValidationOnNull(boolean validationOnNull) {
        if (this.validationOnNull != validationOnNull) {
            this.validationOnNull = validationOnNull;
            commitEdit();
        }
    }

    protected void checkValidation(boolean status) {
        boolean valid = status || isEditorValidationOnNull();
        if (isValid != valid) {
            isValid = valid;
            if (editor != null) {
                if (editorValidation) {
                    validChanged(editor, valid);
                }
            }
        }
    }

    protected void validChanged(JFormattedTextField editor, boolean isValid) {
        String style = isValid ? null : FlatClientProperties.OUTLINE_ERROR;
        editor.putClientProperty(FlatClientProperties.OUTLINE, style);
    }

    protected boolean isEditorValidationOnNull() {
        if (validationOnNull) {
            return false;
        }
        return editor != null && editor.getText().equals(getDefaultPlaceholder());
    }

    protected void commitEdit() {
        if (editor != null && editorValidation) {
            try {
                editor.commitEdit();
            } catch (ParseException ignored) {
            }
        }
    }

    public JFormattedTextField getEditor() {
        return editor;
    }

    public Point getPopupSpace() {
        return popupSpace;
    }

    public void setPopupSpace(Point popupSpace) {
        this.popupSpace = popupSpace;
    }

    protected abstract String getDefaultPlaceholder();

    protected abstract void popupOpen();
}
