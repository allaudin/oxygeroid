package io.github.allaudin.oxygeroid.core;

import io.github.allaudin.oxygeroid.model.ViewModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * Created on 2016-12-25 12:00.
 *
 * @author M.Allaudin
 */

public class FactoryBuilder {

    private String factoryName;
    private ClassName rootClass;
    private ClassName resourceClass;

    public FactoryBuilder(String factoryName, ClassName rootClass, ClassName resourceClass) {
        this.factoryName = factoryName;
        this.rootClass = rootClass;
        this.resourceClass = resourceClass;
    }

    private MethodSpec getFactoryMethod(){
        return  MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(rootClass, "root", Modifier.FINAL)
                .addStatement("return new $L($L)", factoryName, "root")
                .returns(ClassName.get("", factoryName))
                .build();
    } // getConstructorMethod

    private MethodSpec getConstructor(List<ViewModel> viewModels){
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(Modifier.PRIVATE);
        builder.addParameter(rootClass, "root", Modifier.FINAL);

        for (ViewModel model: viewModels){
            ClassName viewType = ClassName.get(model.getPackageName(), model.getType());
            builder.addStatement("$L = ($T) $L.findViewById($T.id.$L)", model.getName(), viewType, "root", resourceClass, model.getId());
        }

        return builder.build();
    } // getConstructor

    private List<FieldSpec> getFieldSpecs(List<ViewModel> viewModels){
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        for (ViewModel model: viewModels){
                ClassName viewClass = ClassName.get(model.getPackageName(), model.getType());
                FieldSpec view = FieldSpec.builder(viewClass, model.getName(), Modifier.PUBLIC, Modifier.FINAL).build();
                fieldSpecs.add(view);
        } // end for
        return fieldSpecs;
    } // getField

    public TypeSpec generate(List<ViewModel> viewModels){

        return TypeSpec.classBuilder(factoryName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getConstructor(viewModels))
                .addFields(getFieldSpecs(viewModels))
                .addMethod(getFactoryMethod())
                .build();
    } // generate


} // FactoryBuilder
