package ui;

import java.util.List;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainForm extends JFrame{

    /* Os elementos adicionados na GUI ficam como  atributos privados da classe*/
    private JPanel rootPanel;
    private JButton buttonNewContact;
    private JButton buttonRemove;
    private JTable tableContacts;
    private JLabel labelContactCounter;

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
        String[] columnNames = {"nome", "telefone"}; //criando um array de String
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for(ContactEntity contact : contactList){ //for necessário para adicionar todos os contatos como linhas da tabela
            Object[] o = new Object[2]; //cria um array de objeto de tamanho 2 para ser uma linha da tabela
            o[0] = contact.getName(); //faz a primeira posição receber o nome do contato
            o[1] = contact.getPhone(); //faz a segunda posição receber o telefone do contato
            model.addRow(o); //adiciona uma linha na tabela, passando o nosso array que contém o nome e o telefone, cada valor será associado a uma coluna
        }
        tableContacts.setModel(model); //faz a tabela receber o model com os valores inseridos

        int contactCount = mContactBusiness.getContactCount();
        labelContactCounter.setText(contactCount + " " + (contactCount == 1 || contactCount == 0 ?"contato" : "contatos"));
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
