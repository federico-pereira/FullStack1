package duoc.proyect.assemblers;

import duoc.proyect.controller.CuponDescuentoControllerV2;
import duoc.proyect.model.CuponDescuento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CuponDescuentoModelAssembler implements RepresentationModelAssembler<CuponDescuento, EntityModel<CuponDescuento>> {

    @Override
    public EntityModel<CuponDescuento> toModel(CuponDescuento cupon) {
        return EntityModel.of(cupon,
                linkTo(methodOn(CuponDescuentoControllerV2.class).getCuponById(cupon.getId())).withSelfRel(),
                linkTo(methodOn(CuponDescuentoControllerV2.class).getCupones()).withRel("todos-cupones"),
                linkTo(methodOn(CuponDescuentoControllerV2.class).getCuponesByDescuento(cupon.getDescuento())).withRel("mismo-descuento"),
                linkTo(methodOn(CuponDescuentoControllerV2.class).deleteCuponById(cupon.getId())).withRel("delete")
        );
    }
}