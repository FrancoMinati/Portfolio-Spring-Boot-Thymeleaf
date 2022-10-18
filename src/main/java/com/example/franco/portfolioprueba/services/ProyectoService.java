package com.example.franco.portfolioprueba.services;

import com.example.franco.portfolioprueba.entitites.Estado;
import com.example.franco.portfolioprueba.entitites.Proyecto;
import com.example.franco.portfolioprueba.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService implements BaseService<Proyecto>{
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    @Transactional
    public List<Proyecto> getAll() throws Exception {

        try{
            List<Proyecto> proyectos=this.proyectoRepository.findAll();
            return proyectos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Proyecto getById(Long id) throws Exception {
        try{
            Optional<Proyecto> opt=this.proyectoRepository.findById(id);
            Proyecto proyecto=opt.get();
            return proyecto;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Proyecto save(Proyecto entity) throws Exception {
        try{
            Proyecto proyecto=this.proyectoRepository.save(entity);
            return proyecto;
        }catch (Exception e){
            throw new Exception(e.getMessage()+"gordo puto");
        }
    }

    @Override
    @Transactional
    public Proyecto update(Proyecto entity, Long id) throws Exception {
        try{
            Optional<Proyecto> opt=this.proyectoRepository.findById(id);
            if(opt.isPresent()){
                Proyecto proyecto=this.proyectoRepository.save(entity);
                return proyecto;
            }else{
                throw new Exception("No existe un proyecto con el id: " + id);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean delete(Long id) throws Exception {
        try{
            Optional<Proyecto> opt=this.proyectoRepository.findById(id);
            if(!opt.isEmpty()){
                Proyecto proyecto=opt.get();
                if(proyecto.getEstado().equals(Estado.ACTIVO)){
                    proyecto.setEstado(Estado.INACTIVO);
                }
            }else {
                throw new Exception("No existe un proyecto con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public Boolean delete_hard(Long id) throws Exception {
        try{
            Optional<Proyecto> opt=this.proyectoRepository.findById(id);
            if(!opt.isEmpty()){
                this.proyectoRepository.deleteById(id);
            }else {
                throw new Exception("No existe un proyecto con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public List<Proyecto> getAllByActive() throws Exception{
        try {
            List<Proyecto> entities = this.proyectoRepository.getAllByActive();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Proyecto getByNombreAndActivo(String nombre) throws Exception {
        try {
            Optional<Proyecto> opt = this.proyectoRepository.getByNombreAndActivo(nombre);
            if(opt.isPresent()){
                Proyecto proyecto=opt.get();
                return proyecto;
            }else{
                throw new Exception("No existe un proyecto con ese nombre: " + nombre);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public Proyecto getByLastId() throws Exception{
        try{
            Optional<Proyecto> opt=this.proyectoRepository.getByLastId();
            if(opt.isPresent()){
                Proyecto proyecto=opt.get();
                return proyecto;
            }else{
                throw new Exception("No se encuentran proyectos");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
