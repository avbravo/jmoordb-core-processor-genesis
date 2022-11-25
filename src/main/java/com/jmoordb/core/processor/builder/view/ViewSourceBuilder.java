package com.jmoordb.core.processor.builder.view;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.PING;
import com.jmoordb.core.processor.builder.*;
import com.jmoordb.core.annotation.view.View;
import com.jmoordb.core.processor.internal.MethodProcessorAux;
import com.jmoordb.core.processor.methods.view.ViewMethod;
import com.jmoordb.core.processor.model.view.ViewData;
import java.util.*;

/**
 * This class only works if we add elements in proper sequence.
 */
public class ViewSourceBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";

    private StringBuilder builder = new StringBuilder();
    private String className;
    private Map<String, String> fields = new LinkedHashMap<>();

    ViewSourceBuilderUtil sourceUtilBuilder = new ViewSourceBuilderUtil();

    public ViewSourceBuilder() {

    }

    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder init(View view, ViewData viewData, List<ViewMethod> viewMethodList, String database, String collection)">
       public ViewSourceBuilder init(View view, ViewData viewData, List<ViewMethod> viewMethodList) {
        builder.append(sourceUtilBuilder.definePackage(viewData.getPackageOfView()));
        builder.append(sourceUtilBuilder.generateImport(view, viewData));
        builder.append(sourceUtilBuilder.addApplicationScoped());
        builder.append(sourceUtilBuilder.defineClass(viewData.getInterfaceName() + "Impl", " implements " + viewData.getInterfaceName()));

        builder.append(sourceUtilBuilder.inject(view, viewData));

        /**
         * Generar los metodos encontrados
         */
        if (viewMethodList.isEmpty()) {
         //   MessagesUtil.warning("No hay información de los métodos");
        } else {
           
            for (ViewMethod viewMethod : viewMethodList) {
                switch (viewMethod.getAnnotationType()) {
                    case VFORM:
                        builder.append(VFormBuilder.save(viewData, viewMethod));
                        break;
                    case PING:

                     
                }


            }
        }
        builder.append(VFormBuilder.saveOfCrud(viewData));
       
       //Aqui agregar VTemplateBuiñder., VMenuBuilder
 
            
        return this;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addEditorFoldStart(String desc) ">
    /**
     *
     * @param desc. -Utiloce \" si necesita incluir " en el texto
     * @return inserta un editor fold que sirve como ayuda a NetBeans IDE
     */
    public ViewSourceBuilder addEditorFoldStartx(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addFields(LinkedHashMap<String, String> identifierToTypeMap)">
    /**
     *
     * @param identifierToTypeMap
     * @return
     */
    public ViewSourceBuilder addFields(LinkedHashMap<String, String> identifierToTypeMap) {
        for (Map.Entry<String, String> entry : identifierToTypeMap.entrySet()) {
            addField(entry.getValue(), entry.getKey());
        }
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addField(String type, String identifier)">
    public ViewSourceBuilder addField(String type, String identifier) {
        fields.put(identifier, type);
        builder.append("private ")
                .append(type)
                .append(" ")
                .append(identifier)
                .append(";")
                .append(LINE_BREAK);

        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addConstructor(String accessModifier, List<String> fieldsToBind) ">
    /**
     *
     * @param accessModifier
     * @param fieldsToBind
     * @return
     */
    public ViewSourceBuilder addConstructor(String accessModifier, List<String> fieldsToBind) {
        builder.append(LINE_BREAK)
                .append(accessModifier)
                .append(" ")
                .append(className)
                .append("(");

        for (int i = 0; i < fieldsToBind.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            String name = fieldsToBind.get(i);
            builder.append(fields.get(name))
                    .append(" ")
                    .append(name);
        }
        builder.append(") {");
        for (int i = 0; i < fieldsToBind.size(); i++) {
            builder.append(LINE_BREAK);

            String name = fieldsToBind.get(i);
            builder.append("this.")
                    .append(name)
                    .append(" = ")
                    .append(name)
                    .append(";");
        }
        builder.append(LINE_BREAK);
        builder.append("}");
        builder.append(LINE_BREAK);

        return this;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addConstructor(String accessModifier, boolean bindFields)">
    public ViewSourceBuilder addConstructor(String accessModifier, boolean bindFields) {
        addConstructor(accessModifier,
                bindFields ? new ArrayList(fields.keySet())
                        : new ArrayList<>());
        return this;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addMethod(MethodProcessorAux method)">

    public ViewSourceBuilder addMethod(MethodProcessorAux method) {
        builder.append(LINE_BREAK)
                .append(method.end())
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder createSetterForField(String name)">

    public ViewSourceBuilder createSetterForField(String name) {
        if (!fields.containsKey(name)) {
            throw new IllegalArgumentException("Field not found for setter: " + name);
        }
        addMethod(new MethodProcessorAux()
                .defineSignature("public", false, "void")
                .name("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1))
                .defineBody(" this." + name + " = " + name + ";"));
        return this;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder createGetterForField(String name)">

    public ViewSourceBuilder createGetterForField(String name) {
        if (!fields.containsKey(name)) {
            throw new IllegalArgumentException("Field not found for Getter: " + name);
        }
        addMethod(new MethodProcessorAux()
                .defineSignature("public", false, fields.get(name))
                .name("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1))
                .defineBody(" return this." + name + ";"));
        return this;
    }
    // </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="end()">

    /**
     *
     * @return
     */
    public String end() {
        builder.append(LINE_BREAK + "}");
        return builder.toString();

    }
    // </editor-fold>
   // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addEditorFoldStart(String desc) ">
    /**
     *
     * @param desc. -Utiloce \" si necesita incluir " en el texto
     * @return inserta un editor fold que sirve como ayuda a NetBeans IDE
     */
    public ViewSourceBuilder addEditorFoldStart(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc="ViewSourceBuilder addNestedClass(ViewSourceBuilder jClass)">

    public ViewSourceBuilder addNestedClass(ViewSourceBuilder jClass) {
        builder.append(LINE_BREAK);
        builder.append(jClass.end());
        builder.append(LINE_BREAK);
        return this;
    }
// </editor-fold>
}
