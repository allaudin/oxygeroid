package com.mallaudin.oxygeroid.core;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created on 6/24/17.
 *
 * @author M.Allaudin
 */

public class LayoutBuilder {

    private String layoutPath;

    public LayoutBuilder(String module, String layoutPath) {
        this.layoutPath = String.format("./%s/src/main/res/layout/%s.xml", module, layoutPath);
    }

    public NodeList getNodeList() {
        DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
        Document document;

        try {
            DocumentBuilder builder = instance.newDocumentBuilder();
            document = builder.parse(new File(layoutPath));
        } catch (ParserConfigurationException | SAXException | IOException e) {
//            messager.printMessage(Diagnostic.Kind.ERROR, "" + e.getMessage());
            return null;
        }

        return document.getDocumentElement().getChildNodes();
    }
}
