package repository

import entity.ContactEntity

class ContactRepository {

    //não precisamos fazer validação porque assumi-se que para salvar os dados
    //eles já terão sido validados por outra classe

    //criação de métodos e atributo estático para que eles permaneçam independente da criação de instâncias
    companion object{

        private val contactList = mutableListOf<ContactEntity>()

        fun save(contact: ContactEntity){ //recebe uma data class 'ContactEntity' ao invés dos vários parâmetros
            contactList.add(contact) //adicionando um novo contato à lista
        }
        fun delete(contact: ContactEntity){

        }
        fun getContactList(): List<ContactEntity>{
            return contactList
        }
    }

}