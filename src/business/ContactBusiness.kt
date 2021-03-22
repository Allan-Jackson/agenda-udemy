package business

import entity.ContactEntity
import repository.ContactRepository

//um padrão para nome de pacote que vai conter as regras de negócio

class ContactBusiness {
    fun save(name: String, phone: String){
        if(!validate(name,phone)) throw
        IllegalArgumentException("Nome e telefone são obrigatórios.") //faz parte do if, está apenas com quebra de linha
        var contact = ContactEntity(name, phone)
        ContactRepository.save(contact) //salva o contato no "banco"
    }
    private fun validate(name: String, phone: String): Boolean{ //privada pois a camada business é quem valida
        if(name == "" || phone == ""){
            return false
        }
        return true
    }
    fun delete(name: String, phone: String){
        if(!validate(name,phone)) throw
        IllegalArgumentException("É necessário selecionar um contato para remover.")
        var contact = ContactEntity(name, phone)
        ContactRepository.delete(contact)
    }
    fun getContactList(): List<ContactEntity>{
        return ContactRepository.getContactList()
    }
    fun getContactCount(): Int{
        return getContactList().size;
    }
}