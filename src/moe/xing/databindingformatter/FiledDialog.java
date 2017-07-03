package moe.xing.databindingformatter;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FiledDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList fileds;
    private PsiClass psiClass;
    private PsiField[] classFields;
    private FiledDialog.OnConfirmListener listener;

    public FiledDialog(PsiClass psiClass) {
        setContentPane(contentPane);
        setModal(true);
        this.psiClass = psiClass;
        classFields = psiClass.getFields();

        String[] filedNames = new String[classFields.length];

        for (int i = 0; i < classFields.length; i++) {
            filedNames[i] = classFields[i].getName();
        }
        fileds.setListData(filedNames);

        fileds.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setLocationRelativeTo(null);
        this.setTitle("select Filed To generator databinding getter setter");
    }

    private void onOK() {
        // add your code here
        if (listener != null) {
            listener.onConfirm(fileds.getSelectedIndices());
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog() {
        this.pack();
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);
        this.setVisible(true);
    }

    public void setListener(OnConfirmListener listener) {
        this.listener = listener;
    }

    public interface OnConfirmListener {
        void onConfirm(int[] indexs);
    }
}