package io.github.allaudin.oxygeroid;

/**
 * Created on 2016-12-25 12:09.
 *
 * @author M.Allaudin
 */
public class Utils {

    private static final String SPLITTER = "_";

    private static String capitalizeWord(final String word){
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        return firstLetter + word.substring(1);
    } // capitalizeWord

    public static boolean isEmpty(String string){
        return string.trim().length() == 0;
    } // isEmpty

    public static String extractPackage(String qualifiedName){
        if(qualifiedName.equals("View")){
            return "android.view";
        }
        return qualifiedName.contains(".")? qualifiedName.substring(0, qualifiedName.lastIndexOf(".")): "";
    } // extractPackage

    public static String extractIdFromAndroidId(String xmlId){
        int splitIndex = xmlId.lastIndexOf("/") + 1;
        return xmlId.substring(splitIndex);
    } // extractIdFromAndroidId

    public static String getNameFromId(final String xmlId){

        String[] words = xmlId.split(SPLITTER);

        StringBuilder nameBuilder = new StringBuilder();

        nameBuilder.append(words[0]);

        for (int i = 1; i < words.length; i++) {
            nameBuilder.append(capitalizeWord(words[i]));
        }

        return nameBuilder.toString();
    } // getNameFromXmlId

    public static String getSimpleNameFromXmlView(String viewName){
        return viewName.contains(".")? viewName.substring(viewName.lastIndexOf(".") + 1): viewName;
    } // getSimpleNameFromXmlView

} // Utils
