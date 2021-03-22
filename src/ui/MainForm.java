package ui;

import java.util.List;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainForm extends JFrame{

    /* Os elementos adicionados na GUI ficam como  atributos privados da classe*/
    private JPanel rootPanel;
    private JButton buttonNewContact;
    private JButton buttonRemove;
    private JTable tableContacts;

    private ContactBusiness mContactBusiness; //esse 'm' na frente é uma convenção para variáveis criadas pelo
    //programador, diferente dessas acima que foram geradas pelo 'form'


    //construtor da classe para criar a interface e deixá-la visível quando a classe for instanciada
    public MainForm(){
        setContentPane(rootPanel);
        setSize(500,250);
        setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //pega o tamanho da tela do monitor
        //cria um objeto Point que possui os atributos inteiros x e y, por padrão ele cria um ponto na origem (0,0)
        Point meioDaTela = new Point(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        //setLocation recebe um x e y ou então um objeto do tipo Point
        setLocation(meioDaTela);
        //seta a operação que deve ser performada quando o frame for fechado
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mContactBusiness = new ContactBusiness();

        setListeners();
        loadContacts();
    }
    private void loadContacts(){
        List<ContactEntity> contactList = mContactBusiness.getContactList();
    }
    private void setListeners(){
       buttonNewContact.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new ContactForm(); //instancia o formulário de contato que, no seu construtor, constroi uma janela visível
               dispose(); //fecha este formulário para que ele não fique sob o outro que foi aberto
           }
       });
       buttonRemove.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });
    }


}
