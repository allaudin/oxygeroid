package com.mallaudin.oxygeroid.core;


import com.mallaudin.oxygeroid.Utils;
import com.mallaudin.oxygeroid.model.ViewModel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016-12-25 12:23.
 *
 * @author M.Allaudin
 */

public class NodeParser {

    private List<ViewModel> viewModels;
    private String module;

    private NodeParser(String module) {
        this.module = module;
        viewModels = new ArrayList<>();
    } // NodeParser

    public static NodeParser newInstance(String module) {
        return new NodeParser(module);
    } // newInstance

    public List<ViewModel> parse(NodeList nodeList) {

        for (int i = 0, len = nodeList.getLength(); i < len; i++) {
            parseNode(nodeList.item(i));
        }

        return viewModels;
    } // parse

    private void parseNode(Node node) {

        if (node.hasChildNodes()) {
            NodeList nl = node.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                parseNode(nl.item(i));
            }
        } // end if

        if (node.hasAttributes() && node.getAttributes().getNamedItem("layout") != null) {
            String xmlPath = node.getAttributes().getNamedItem("layout").getNodeValue();
            int lastIndex = xmlPath.lastIndexOf("/");
            xmlPath = xmlPath.substring(lastIndex + 1);
            NodeList nodeList = new LayoutBuilder(module, xmlPath).getNodeList();
            parse(nodeList);
        } else if (node.hasAttributes() && node.getAttributes().getNamedItem("android:id") != null) {
            final String value = node.getAttributes().getNamedItem("android:id").getNodeValue();
            ViewModel viewModel = new ViewModel();
            String pkgName = Utils.extractPackage(node.getNodeName());
            String xmlId = Utils.extractIdFromAndroidId(value);
            viewModel.setPackageName(pkgName.length() > 0 ? pkgName : "android.widget");
            viewModel.setId(xmlId);
            viewModel.setName(Utils.getNameFromId(xmlId));
            viewModel.setType(Utils.getSimpleNameFromXmlView(node.getNodeName()));

            if (viewModel.shouldInclude()) {
                viewModels.add(viewModel);
            }
        } // end if

    } // parseNode

} // NodeParser
