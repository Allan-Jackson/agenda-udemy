package business //um padrão para nome de pacote que vai conter as regras de negócio

class ContactBusiness {
    fun save(name: String, phone: String){
        if(!validate(name,phone)) throw
        IllegalArgumentException("Nome e telefone são obrigatórios.")
    }
    fun validate(name: String, phone: String): Boolean{
        if(name == "" || phone == ""){
            return false
        }
        return true
    }
    fun delete(name: String, phone: String){
        if(!validate(name,phone)) throw
        IllegalArgumentException("É necessário selecionar um contato para remover.")
    }
}