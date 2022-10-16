package com.example.franco.portfolioprueba.services;

import com.example.franco.portfolioprueba.entitites.Estado;
import com.example.franco.portfolioprueba.entitites.Proyecto;
import com.example.franco.portfolioprueba.entitites.Usuario;
import com.example.franco.portfolioprueba.repositories.ProyectoRepository;
import com.example.franco.portfolioprueba.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService implements BaseService<Usuario> {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public List<Usuario> getAll() throws Exception {

        try{
            List<Usuario> usuarios=this.usuarioRepository.findAll();
            return usuarios;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario getById(Long id) throws Exception {
        try{
            Optional<Usuario> opt=this.usuarioRepository.findById(id);
            Usuario usuario=opt.get();
            return usuario;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario entity) throws Exception {
        try{
            Usuario usuario=this.usuarioRepository.save(entity);
            return usuario;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario update(Usuario entity, Long id) throws Exception {
        try{
            Optional<Usuario> opt=this.usuarioRepository.findById(id);
            if(opt.isPresent()){
                Usuario usuario=this.usuarioRepository.save(entity);
                return usuario;
            }else{
                throw new Exception("No existe un usuario con el id: " + id);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean delete(Long id) throws Exception {
        try{
            Optional<Usuario> opt=this.usuarioRepository.findById(id);
            if(!opt.isEmpty()){
                Usuario usuario=opt.get();
                if(usuario.getEstado().equals(Estado.ACTIVO)){
                    usuario.setEstado(Estado.INACTIVO);
                }
            }else {
                throw new Exception("No existe un usuario con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
