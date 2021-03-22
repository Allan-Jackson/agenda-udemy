package ui;

import business.ContactBusiness;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ContactForm extends JFrame {
    private JPanel rootPanel;
    private JTextField textName;
    private JTextField textPhone;
    private JButton buttonSave;
    private JButton buttonCancel;

    private ContactBusiness mContactBusiness;

    public ContactForm(){
        setContentPane(rootPanel);
        setSize(500,250);
        setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( (dim.width/2 - getWidth()/2), (dim.height/2 - getHeight()/2) ); //seta no meio da tela
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mContactBusiness = new ContactBusiness(); //isso precisa ser feito antes do que vem abaixo
        setMnemonics();
        setListeners();

    }

    //seta os atalhos do teclado para os botões e outros componentes da janela
    private void setMnemonics(){
        buttonSave.setMnemonic((int)'\n'); //ao apertar Alt + 'enter' neste formulário, a ação de salvar vai ser executada
        buttonCancel.setMnemonic((int)'\033'); //ao apertar Alt + 'esc', o botão cancelar será clicado
    }
    private void setListeners(){
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm();
                dispose();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = textName.getText();
                String phone = textPhone.getText();
                try{
                    mContactBusiness.save(name,phone);
                    new MainForm(); //volta para o formulário principal
                    dispose(); //fecha o formulário de contato
                }catch (IllegalArgumentException excp){
                    JOptionPane.showMessageDialog(
                            null,
                            "Os dados de nome e telefone são obrigatórios!",
                            "Informações ausentes",
                            JOptionPane.WARNING_MESSAGE);
                }catch(Exception excp){
                    JOptionPane.showMessageDialog(
                            null,
                            "Um erro inesperado ocorreu:(",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                    new MainForm();
                    dispose();
                }
            }
        });
    }
}
