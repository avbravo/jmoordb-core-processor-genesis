/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierBuilderUtil;
import com.jmoordb.core.processor.viewentity.supplier.embedded.ViewEntitySupplierEmbeddedBuilder;
import com.jmoordb.core.processor.viewentity.supplier.generate.util.ViewEntitySupplierReferencedUtil;
import com.jmoordb.core.processor.viewentity.supplier.generate.util.ViewEntitySupplierViewReferencedUtil;

/**
 *
 * @author avbravo
 */
public interface ViewEntitySupplierGenerateToUpdate {
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdate(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element)">
    public static StringBuilder toUpdate(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\n";
            String caracterComa=",";
            for (ViewEntityField viewEntityField : viewEntityFieldList) {
                 if((count +1)== viewEntityFieldList.size()){
                     caracterComa="";
                    
                 }
                 
                switch (viewEntityField.getAnnotationType()) {
                   
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + ViewEntitySupplierEmbeddedBuilder.toUpdate(viewEntityData, viewEntityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            

                           sentence += " " + coma + ViewEntitySupplierReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.TRUE);
                        } else {
                           
                            sentence += " " + coma + ViewEntitySupplierReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            
                           
                           sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.TRUE);
                        } else {
                           

                            sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                            String sentenceToString="";
                         if (viewEntityField.getReturnType().equals(ReturnType.UUID)) {
                             sentenceToString=".toString()";
                             
                         }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()"+sentenceToString;
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdate(viewEntityData) + "\n\n"
                    + "    public Bson toUpdate(" + viewEntityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ") {\n"
                    + "        Bson update_ = Filters.empty();\n"
                    + "        try {\n"
                    + "        update_ = Updates.combine(\n"
                    + sentence + "\n"
                    + "        );" + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return update_;\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
   
    
    
    
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdateList(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element)">
    public static StringBuilder toUpdateList(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);


            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
            String lower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());

        
      
            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";
                String caracterComa=",";
            for (ViewEntityField viewEntityField : viewEntityFieldList) {
                 if((count + 1) == viewEntityFieldList.size()){
                     caracterComa="";
                   
                 }
                switch (viewEntityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + ViewEntitySupplierEmbeddedBuilder.toUpdate(viewEntityData, viewEntityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                          sentence += " " + coma + ViewEntitySupplierReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.TRUE);
                        } else {
                            
                            sentence += " " + coma + ViewEntitySupplierReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                          
                          sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.TRUE);
                        } else {
                            sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.toUpdate(viewEntityData, viewEntityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                            String sentenceToString="";
                         if (viewEntityField.getReturnType().equals(ReturnType.UUID)) {
                             sentenceToString=".toString()";
                             
                         }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()"+sentenceToString;
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }


            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdateList(viewEntityData) + "\n\n"
                    + "    public List<Bson> toUpdate(List<" + viewEntityData.getEntityName()+ "> " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + "List) {\n"
                    + "        List<Bson> bsonList_ = new ArrayList<>();\n"
                    + "        try {\n"
                    + "\t for(" + upper + " " + lower + " : " + lower + "List){\n"
                    + "\t\t Bson update_ = Filters.empty();\n"
                    + "\t\t\tupdate_ = Updates.combine(\n"
                    + sentence + "\n"
                       + "        );" + "\n"
                    + "\t\tbsonList_.add(update_);\n "+ "\n"
                    + "       }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return bsonList_;\n"
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
