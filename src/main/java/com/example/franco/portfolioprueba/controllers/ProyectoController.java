package com.example.franco.portfolioprueba.controllers;

import com.example.franco.portfolioprueba.entitites.Estado;
import com.example.franco.portfolioprueba.entitites.Proyecto;
import com.example.franco.portfolioprueba.services.ProyectoService;
import com.example.franco.portfolioprueba.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Controller
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/inicio")
    public String inicio(Model model) {
        try {
            List<Proyecto> proyectos=this.proyectoService.getAll();
            Proyecto ultimoProyecto=this.proyectoService.getByLastId();
            model.addAttribute("ultimo_proyecto",ultimoProyecto);
            model.addAttribute("proyectos", proyectos);
            return "inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/crud")
    public String crudProyectos(Model model){
        try{
            List<Proyecto> proyectos=this.proyectoService.getAll();
            model.addAttribute("proyectos", proyectos);
            return "views/crud";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "error";
        }
    }
    @GetMapping("/editar/{id}")
    public String editarProyecto(Model model,@PathVariable("id")Long id){
        try{
            if(id==0){
                model.addAttribute("proyecto",new Proyecto());
            }else{
                model.addAttribute("proyecto",this.proyectoService.getById(id));
            }
            return "views/formulario/proyecto";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "error";
        }
    }
    @PostMapping("/guardar/{id}")
    public String guardarProyecto(@RequestParam("archivo") MultipartFile archivo,
                                  @Valid @ModelAttribute("proyecto") Proyecto proyecto,
                                  BindingResult result,
                                  Model model,@PathVariable("id") Long id){
        try{

            if(result.hasErrors()){
                return "views/formulario/proyecto";
            }
            String rutaAbsoluta ="C://Proyecto//imagenes";
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + archivo.getOriginalFilename());

            if (id == 0) {
                if(archivo.isEmpty()){
                    model.addAttribute("errorImagenMsg","La imagen es requerida");
                    return "views/formulario/proyecto";
                }
                if(archivo.getSize()>15000000){
                    model.addAttribute("errorImagenMsg","El peso excede 15MB");
                    return "views/formulario/proyecto";
                }
                Files.write(rutaCompleta,archivo.getBytes());
                proyecto.setEstado(Estado.ACTIVO);
                proyecto.setImagen(archivo.getOriginalFilename());
                this.proyectoService.save(proyecto);
            }else{
                if(!archivo.isEmpty()){
                    if(archivo.getSize() >= 15000000){
                        model.addAttribute("errorImagenMsg","El peso excede 15MB");
                        return "views/formulario/proyecto";
                    }
                    proyecto.setImagen(archivo.getOriginalFilename());
                    Files.write(rutaCompleta,archivo.getBytes());
                }
                this.proyectoService.update(proyecto,id);
            }

            return "redirect:/crud";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "error";
        }
    }

}
