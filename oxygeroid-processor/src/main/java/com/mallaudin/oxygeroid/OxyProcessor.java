package com.mallaudin.oxygeroid;


import com.mallaudin.annotations.FactoryType;
import com.mallaudin.annotations.ResourcePackage;
import com.mallaudin.annotations.ViewFactory;
import com.mallaudin.oxygeroid.core.FactoryBuilder;
import com.mallaudin.oxygeroid.core.LayoutBuilder;
import com.mallaudin.oxygeroid.core.NodeParser;
import com.mallaudin.oxygeroid.model.ViewModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created on 2016-12-24 12:58.
 *
 * @author M.Allaudin
 */

public class OxyProcessor extends AbstractProcessor {

    private String resourcePackage;
    private String module;


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            return true;
        }


        for (Element e : roundEnv.getElementsAnnotatedWith(ResourcePackage.class)) {
            ResourcePackage anno = e.getAnnotation(ResourcePackage.class);

            resourcePackage = anno.value();
            module = anno.module();

            if (Utils.isEmpty(resourcePackage)) {
                printError(e, "%s can not be empty.", ResourcePackage.class.getSimpleName());
                return false;
            }
        } // end for


        for (Element e : roundEnv.getElementsAnnotatedWith(ViewFactory.class)) {
            ViewFactory anno = e.getAnnotation(ViewFactory.class);
            String layoutFile = anno.value();

            if (Utils.isEmpty(layoutFile)) {

                printError(e, "%s can not be empty.", ResourcePackage.class.getSimpleName());
                return false;
            }
            generateViewFactoryClass(e, anno);
        } // end for


        return false;
    } // process

    private boolean generateViewFactoryClass(Element element, ViewFactory anno) {

        String rootName, rootPackage;

        String layoutFile = anno.value();
        FactoryType type = anno.type();

        if (type.equals(FactoryType.VIEW)) {
            rootName = "View";
            rootPackage = "android.view";
        } else {
            rootName = element.getSimpleName().toString();
            rootPackage = Utils.extractPackage(((TypeElement) element).getQualifiedName().toString());
        }

        ClassName rootClass = ClassName.get(rootPackage, rootName);
        ClassName resourceClass = ClassName.get(resourcePackage, "R");

        NodeList nodeList = new LayoutBuilder(module, layoutFile).getNodeList();
        List<ViewModel> viewModels = NodeParser.newInstance(module).parse(nodeList);

        String factoryClassName;
        if (Utils.isEmpty(anno.className())) {
            factoryClassName = String.format("%sViews", element.getSimpleName());
        } else {
            factoryClassName = anno.className();
        }


        TypeSpec typeSpec = new FactoryBuilder(factoryClassName, rootClass, resourceClass).generate(viewModels);

        writeToFiler(element, typeSpec);

        return false;
    }

    private boolean writeToFiler(Element element, TypeSpec typeSpec) {
        try {
            JavaFile.builder("io.github.allaudin.oxygeroid", typeSpec).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            printError(element, "%s", e.getMessage());
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> annoSet = new LinkedHashSet<>();
        annoSet.add(ResourcePackage.class.getCanonicalName());
        annoSet.add(ViewFactory.class.getCanonicalName());
        return annoSet;
    } // getSupportedAnnotationTypes

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    } // getSupportedSourceVersion

    private void printError(Element e, String format, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(format, args), e);
    } // printError

} // OxyProcessor
