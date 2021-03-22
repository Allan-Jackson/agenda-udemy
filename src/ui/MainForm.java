package ui;

import java.util.List;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private String mName = ""; //permite ao nome ser acessado por mais de um método
    private String mPhone = ""; //idem

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

        mContactBusiness = new ContactBusiness(); //seta o valor para a propriedade 'mContactBusiness'

        setMnemonics();
        setListeners();
        loadContacts();
    }

    //seta os atalhos do teclado para os componentes da janela
    private void setMnemonics(){
        buttonNewContact.setMnemonic((int)'\n'); //Alt + 'enter'
        buttonRemove.setMnemonic((int)'\033'); //Alt + 'esc'
    }
    //carrega a tabela de contatos existentes no MainForm
    private void loadContacts(){

        //pega a lista de contatos
        List<ContactEntity> contactList = mContactBusiness.getContactList();

        //cria um modelo para a tabela
        String[] columnNames = {"nome", "telefone"}; //criando um array de String
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        //insere os dados dos contatos na tabela
        for(ContactEntity contact : contactList){ //for necessário para adicionar todos os contatos como linhas da tabela
            Object[] o = new Object[2]; //cria um array de objeto de tamanho 2 para ser uma linha da tabela
            o[0] = contact.getName(); //faz a primeira posição receber o nome do contato
            o[1] = contact.getPhone(); //faz a segunda posição receber o telefone do contato
            model.addRow(o); //adiciona uma linha na tabela, passando o nosso array que contém o nome e o telefone, cada valor será associado a uma coluna
        }

        //faz a tabela receber o modelo com os valores inseridos
        tableContacts.clearSelection(); //retira a seleção para evitar problemas, se por acaso algo tiver ficado selecionado antes de carregar a tabela e coisa do tipo
        tableContacts.setModel(model);


        //seta o texto da label para mostrar o número de contatos
        int contactCount = mContactBusiness.getContactCount();
        labelContactCounter.setText(contactCount + " " + (contactCount == 1 || contactCount == 0 ?"contato" : "contatos"));
    }

    //adiciona os listeners para os botões 'NewContact' e 'Remove'
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

               try{
                   mContactBusiness.delete(mName,mPhone);
                   mName = ""; //limpa os valores da variável para não ficar armazenada na próxima deleção
                   mPhone = "";
                   loadContacts(); //recarrega a lista de contatos para atualizá-la
               }catch (IllegalArgumentException excp){
                   JOptionPane.showMessageDialog(
                           null,
                           "É necessário selecionar um contato para remover!",
                           "Contato não encontrado",
                           JOptionPane.WARNING_MESSAGE);
               }catch(Exception excp){
                   JOptionPane.showMessageDialog(
                           null,
                           "Um erro inesperado ocorreu:(",
                           "ERRO",
                           JOptionPane.ERROR_MESSAGE);
               }
           }
       });
       tableContacts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
           @Override
           public void valueChanged(ListSelectionEvent e) {
               if(e.getValueIsAdjusting()){ //verifica se o valor ainda está sendo ajustado ou não - se já foi - para evitar pegar um valor que ainda não foi atualizado

                   //só executa esse if se a linha selecionada existir
                   if(tableContacts.getSelectedRow() != -1){ //verifica se a linha selecionada não existe, ou seja, se não existem linhas na tabela ou se nenhuma delas está selecionada
                       mName = tableContacts.getValueAt(tableContacts.getSelectedRow(), 0).toString(); //coluna 0 é o nome
                       mPhone = tableContacts.getValueAt(tableContacts.getSelectedRow(), 1).toString(); //coluna 1 é o telefone
                   }
               }
           }
       });
    }
}
