//package com.alogmed.clinica.repository;
//
//import com.alogmed.clinica.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);
//    User findByCpf(String cpf);
//}
//

package com.alogmed.clinica.repository;

import com.alogmed.clinica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // Importe o Optional

public interface UserRepository extends JpaRepository<User, Long> {

    // ALTERAÇÃO CRÍTICA: O método agora retorna um Optional<User>
    // Isso diz ao Spring: "Eu sei que este método pode não encontrar um usuário,
    // então me devolva o resultado dentro de uma 'caixa' segura (Optional)".
    Optional<User> findByEmail(String email);

    // O findByCpf pode continuar como está, mas o ideal seria usar Optional também
    User findByCpf(String cpf);
}