package com.jmoordb.core.processor.viewentity.supplier;

import com.jmoordb.core.annotation.ViewEntity;
import com.jmoordb.core.annotation.enumerations.AnnotationType;
import com.jmoordb.core.annotation.enumerations.JakartaSource;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import java.util.List;
import javax.lang.model.element.Element;

public class ViewEntitySupplierBuilderUtil {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="String numberOfParametersOfMethod(RepositoryMethod viewEntityMethod)">
    /**
     *
     * @param viewEntityMethod
     * @return Los parametros del metodo como una cadena
     */
    private Integer numberOfParametersOfMethod(RepositoryMethod viewEntityMethod) {
        Integer number = 0;
        try {

            number = viewEntityMethod.getParamTypeElement().size();

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return number;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder definePackage(String packageName)">
    /**
     *
     * @param packageName
     * @return inserta en package en la clase
     */
    public StringBuilder definePackage(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";
            if (packageName != null) {
                code = "package " + packageName + ";\n";
            }
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder defineClass(String startPart, String name, String extendPart)">
    /**
     *
     * @param startPart
     * @param name
     * @param extendPart
     * @return
     */
    public StringBuilder defineClass(String name, String extendPart) {
        className = name;
        StringBuilder builder = new StringBuilder();
        try {
            String code = "public class " + name;
            if (extendPart != null) {
                code += " " + extendPart;
            }
            code += "{\n";
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder addApplicationScoped()">
    /**
     *
     * @param annotation
     * @return agrega anotaciones
     */
    public StringBuilder addRequestScoped() {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";
            builder.append("@RequestScoped\n");
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder generateImport(ViewEntity viewEntity, ViewEntityData viewEntityData)">
    public StringBuilder generateImport(ViewEntity viewEntity, ViewEntityData viewEntityData, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";

            code += "// <editor-fold defaultstate=\"collapsed\" desc=\"imports\">\n\n";
            if (viewEntity.jakartaSource() == JakartaSource.JAVAEE_LEGACY) {
                /*
            Java EE
                 */
                code += "import javax.enterprise.context.RequestScoped;\n"
                        + "import javax.inject.Inject;\n";

            } else {
                /**
                 * Jakarta EE
                 */
                code += "import jakarta.enterprise.context.RequestScoped;\n"
                        + "import jakarta.inject.Inject;\n";
                        
            }
            /**
             * Microprofile
             */

            code += "import java.io.Serializable;\n"
                    + "/**\n"
                    + "* Java\n"
                    + "*/\n"
                    + "import java.time.LocalDateTime;\n"
                    + "import java.lang.annotation.Annotation;\n"
                    + "import java.util.ArrayList;\n"
                    + "import java.util.List;\n"
                    + "import java.util.Optional;\n"
                    + "import java.util.function.Supplier;\n"
                    + "import java.util.UUID;\n"
                    + "/**\n"
                    + "* Jmoordb\n"
                    + "*/\n"
                    + "import com.jmoordb.core.util.MessagesUtil;\n"
                    + "import com.jmoordb.core.util.JmoordbCoreDateUtil;\n"
                    + "import com.jmoordb.core.annotation.Referenced;\n"
                    + "import com.jmoordb.core.annotation.enumerations.TypePK;\n"
                    + "import com.jmoordb.core.annotation.enumerations.TypeReferenced;\n"
                    + "/**\n"
                    + "* MongoDB\n"
                    + "*/\n"
                    + "import org.bson.Document;\n"
                    + "import org.bson.types.ObjectId;\n"
                    + "import com.mongodb.client.model.Filters;\n"
                    + "import com.mongodb.client.model.Updates;\n"
                    + "import org.bson.conversions.Bson;\n"
                    + "import " + viewEntityData.getPackageOfViewEntity() + "." + viewEntityData.getEntityName() + ";\n"
                    + "import " + viewEntityData.getPackageOfViewEntity() + ".*;\n\n\n"
                    + "// </editor-fold>\n";
            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder inject(ViewEntity viewEntity, ViewEntityData viewEntityData, String database, String collection, List<ViewEntityField> viewEntityFieldList, Element element, Boolean haveReferenced, Boolean haveEmbedded, Boolean haveViewReferenced)">
    public StringBuilder inject(ViewEntity viewEntity, ViewEntityData viewEntityData, String database, String collection, List<ViewEntityField> viewEntityFieldList, Element element, Boolean haveReferenced, Boolean haveEmbedded, Boolean haveViewReferenced) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";

            code += "// <editor-fold defaultstate=\"collapsed\" desc=\"inject\">\n\n";

            if (haveReferenced || haveViewReferenced) {
                for (ViewEntityField ef : viewEntityFieldList) {
                    if (ef.getAnnotationType().equals(AnnotationType.REFERENCED)
                            || ef.getAnnotationType().equals(AnnotationType.VIEWREFERENCED)) {

                        String packagePath = JmoordbCoreFileUtil.packageOfFileInProject(element, JmoordbCoreUtil.letterToUpper(ef.getNameOfMethod()) + "Repository.java");
                        code += "    @Inject\n"
                                + "   " + packagePath + JmoordbCoreUtil.letterToUpper(ef.getNameOfMethod()) + "Repository " + ef.getNameOfMethod() + "Repository ;\n";
                        code += "    @Inject\n"
                                + "   " + JmoordbCoreUtil.letterToUpper(ef.getNameOfMethod()) + "Supplier " + ef.getNameOfMethod() + "Supplier ;\n";
                    }
                }
            }
            if (haveEmbedded) {
                for (ViewEntityField ef : viewEntityFieldList) {
                    if (ef.getAnnotationType().equals(AnnotationType.EMBEDDED)) {      
                        code += "    @Inject\n"
                                + "   " + JmoordbCoreUtil.letterToUpper(ef.getNameOfMethod()) + "Supplier " + ef.getNameOfMethod() + "Supplier ;\n";
                    }
                }
            }
            code += "// </editor-fold>\n";
            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc=" Boolean haveEmbedded(List<ViewEntityField> viewEntityFieldList)">
    /**
     * Verifica si tiene un Embedded definido
     *
     * @param viewEntityFieldList
     * @return
     */
    public static Boolean haveEmbedded(List<ViewEntityField> viewEntityFieldList) {
        Boolean result = Boolean.FALSE;
        try {
            for (ViewEntityField ef : viewEntityFieldList) {
                if (ef.getAnnotationType().equals(AnnotationType.EMBEDDED)) {
                    result = Boolean.TRUE;
                    break;
                }
            }
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc=" Boolean haveEmbedded(List<ViewEntityField> viewEntityFieldList)">
    /**
     * Verifica si tiene un Embedded definido
     *
     * @param viewEntityFieldList
     * @return
     */
    public static Boolean haveReferenced(List<ViewEntityField> viewEntityFieldList) {
        Boolean result = Boolean.FALSE;
        try {
            for (ViewEntityField ef : viewEntityFieldList) {
                if (ef.getAnnotationType().equals(AnnotationType.REFERENCED)) {
                    result = Boolean.TRUE;
                    break;
                }
            }
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Boolean haveViewReferenced(List<ViewEntityField> viewEntityFieldList)">
    /**
     * Verifica si tiene un Embedded definido
     *
     * @param viewEntityFieldList
     * @return
     */
    public static Boolean haveViewReferenced(List<ViewEntityField> viewEntityFieldList) {
        Boolean result = Boolean.FALSE;
        try {
            for (ViewEntityField ef : viewEntityFieldList) {
                if (ef.getAnnotationType().equals(AnnotationType.VIEWREFERENCED)) {
                    result = Boolean.TRUE;
                    break;
                }
            }
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;

    }

// </editor-fold>
}
