package com.jmoordb.core.processor.entity.supplier.generate;

import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import static com.jmoordb.core.processor.builder.castconverter.SupplierCastConverterBuilder.castConverter;
import com.jmoordb.core.processor.entity.model.EntityData;
import com.jmoordb.core.processor.entity.supplier.EntitySupplierBuilderUtil;
import com.jmoordb.core.processor.entity.supplier.embedded.EntitySupplierEmbeddedGetBuilder;

import com.jmoordb.core.processor.fields.EntityField;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.entity.supplier.referenced.EntitySupplierReferencedGetBuilder;

public class EntitySupplierGenerateGet implements EntitySupplierGenerateToDocument {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder get(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder get(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierBuilderUtil.haveReferenced(entityFieldList);

            String sentence = "\t";
            String cast = "";
            for (EntityField entityField : entityFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        sentence += EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                        break;
                    case REFERENCED:

                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += EntitySupplierReferencedGetBuilder.referencedProcessGet(entityData, entityField, element);
                        } else {
                            sentence += EntitySupplierReferencedGetBuilder.referencedProcessGet(entityData, entityField, element);
                        }

                        break;

                    case VIEWREFERENCED:

                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += EntitySupplierReferencedGetBuilder.viewReferencedProcessGet(entityData, entityField, element);
                        } else {
                            sentence += EntitySupplierReferencedGetBuilder.viewReferencedProcessGet(entityData, entityField, element);
                        }
                        break;
                    case ID:

                        if (entityField.getNameOfMethod().toLowerCase().trim().equals("_id")) {

                            /**
                             * Convierte _id to id para las llaves primarias de
                             * tipo ObjectId _id
                             */
                            cast = castConverter(entityField.getReturnTypeValue(), entityField.getNameOfMethod());
                            sentence += "\n\t " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "((" + cast + "));\n";
                        } else {
                            if (entityField.getReturnTypeValue().equals("java.util.UUID")) {
                                cast = castConverter(entityField.getReturnTypeValue(), entityField.getNameOfMethod());
                                sentence += "\n\t " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "(UUID.fromString(" + cast + "));\n";
                            } else {
                                cast = castConverter(entityField.getReturnTypeValue(), entityField.getNameOfMethod());
                                sentence += "\n\t " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "(" + cast + ");\n";
                            }

                        }

                        break;
                    case COLUMN:

                        if (entityField.getReturnType().equals(ReturnType.DATE)) {
                            cast = castConverter(entityField.getReturnTypeValue(), entityField.getNameOfMethod());
                            sentence += "\t" + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "(" + cast + ");\n";
                        } else {
                            if (entityField.getReturnType().equals(ReturnType.OBJECTID)) {
                                cast = castConverter(entityField.getReturnTypeValue(), entityField.getNameOfMethod());
                                sentence += "\t" + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "(" + cast + ");\n";
                            } else {
                               
                                cast = castConverter(entityField.getReturnTypeValue(), entityField.getNameOfMethod());
                                sentence += "\t" + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "(" + cast + ");\n";
                              
                            }

                        }

                        break;

                }

            }

            String code
                    = ProcessorUtil.editorFold(entityData) + "\n\n"
                    + "    public " + entityData.getEntityName() + " get(Supplier<? extends " + entityData.getEntityName() + "> s, Document document_, Boolean... showError) {\n"
                    + "        " + JmoordbCoreUtil.letterToUpper(entityData.getEntityName()) + " " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + "= s.get(); \n"
                    + "            Boolean show = true;\n"
                    + "        try {\n"
                    + "            if (showError.length != 0) {\n"
                    + "                show = showError[0];\n"
                    + "            }\n"
                    + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "             if (show) {\n"
                    + "                MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "             }\n"
                    + "         }\n"
                    + "         return " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ";\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
}
