package com.mallaudin.oxygeroid;


import com.mallaudin.annotations.FactoryType;
import com.mallaudin.annotations.ResourcePackage;
import com.mallaudin.annotations.ViewFactory;
import com.mallaudin.oxygeroid.core.FactoryBuilder;
import com.mallaudin.oxygeroid.core.NodeParser;
import com.mallaudin.oxygeroid.model.ViewModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created on 2016-12-24 12:58.
 *
 * @author M.Allaudin
 */

public class OxyProcessor extends AbstractProcessor {

    private String resourcePackage;
    private String module;
    private boolean firstRound = true;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if(firstRound){

            for(Element e: roundEnv.getElementsAnnotatedWith(ResourcePackage.class)){
                ResourcePackage anno =  e.getAnnotation(ResourcePackage.class);

                resourcePackage = anno.value();
                module = anno.module();

                if(Utils.isEmpty(resourcePackage)){
                    printError(e, "%s can not be empty.", ResourcePackage.class.getSimpleName());
                    return false;
                }
            } // end for


            for(Element e: roundEnv.getElementsAnnotatedWith(ViewFactory.class)){
                ViewFactory anno = e.getAnnotation(ViewFactory.class);
                String layoutFile =  anno.value();

                if(Utils.isEmpty(layoutFile)){
                    printError(e, "%s can not be empty.", ResourcePackage.class.getSimpleName());
                    return false;
                }
                generateViewFactoryClass(e, anno);
            } // end for

        } // firstRound

        firstRound = false;

        return false;
    } // process

    private boolean generateViewFactoryClass(Element element, ViewFactory anno) {

        String rootName, rootPackage;

        String layoutFile = anno.value();
        FactoryType type = anno.type();

        if(type.equals(FactoryType.VIEW)){
            rootName = "View";
            rootPackage = "android.view";
        }else {
            rootName = element.getSimpleName().toString();
            rootPackage = Utils.extractPackage(((TypeElement) element).getQualifiedName().toString());
        }

        ClassName rootClass = ClassName.get(rootPackage, rootName);
        ClassName resourceClass = ClassName.get(resourcePackage, "R");

        String xmlFileName = String.format("./%s/src/main/res/layout/%s.xml", module, layoutFile);

        DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
        Document document;

        try {
            DocumentBuilder builder =instance.newDocumentBuilder();
            document = builder.parse(new File(xmlFileName));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            printError(element, "%s", e.getMessage());
            return true;
        }

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        List<ViewModel> viewModels = NodeParser.newInstance().parse(nodeList);

        String factoryClassName;
        if(Utils.isEmpty(anno.className())){
            factoryClassName = String.format("%sVF", element.getSimpleName());
        }else {
            factoryClassName = anno.className();
        }

        TypeSpec typeSpec = FactoryBuilder.builder().factoryName(factoryClassName)
                .resourceClass(resourceClass)
                .rootClass(rootClass).build().generate(viewModels);

        writeToFiler(element, typeSpec);

        return false;
    }

    private boolean writeToFiler(Element element, TypeSpec typeSpec) {
        try {
            JavaFile.builder("com.mallaudin.water", typeSpec).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            printError(element, "%s", e.getMessage());
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new LinkedHashSet<String>(){{
            add("com.mallaudin.annotations.ResourcePackage");
            add("com.mallaudin.annotations.ViewFactory");
        }};
    } // getSupportedAnnotationTypes

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    } // getSupportedSourceVersion

    private void printError(Element e, String format, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(format, args), e);
    } // printError

} // OxyProcessor
