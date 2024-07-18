/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.documentembeddable.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.VIEWREFERENCED;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.documentembeddable.supplier.DocumentEmbeddableSupplierBuilderUtil;
import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.documentembeddable.supplier.embedded.DocumentEmbeddableSupplierEmbeddedBuilder;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.util.DocumentEmbeddableSupplierReferencedUtil;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.util.DocumentEmbeddableSupplierViewReferencedUtil;

/**
 *
 * @author avbravo
 */
public interface DocumentEmbeddableSupplierGenerateToUpdate {

     // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdate(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder toUpdate(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierBuilderUtil.haveReferenced(documentEmbeddableFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
//            String coma = "\\n \\\"";
            String coma = "\n ";
                 String caracterComa=",";
            for (DocumentEmbeddableField entityField : documentEmbeddableFieldList) {
                   if((count + 1) == documentEmbeddableFieldList.size()){
                     caracterComa="";
                   
                 }
                 
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {

                            coma = "\n";
                        }
                        sentence += coma + DocumentEmbeddableSupplierEmbeddedBuilder.toUpdate(documentEmbeddableData, entityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            
                            sentence += " " + coma +DocumentEmbeddableSupplierReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.TRUE);
                        } else {
                            

                            sentence += " " + coma + DocumentEmbeddableSupplierReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                        
                        
                          case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            
                           
                           sentence += " " + coma +DocumentEmbeddableSupplierViewReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.TRUE);
                        } else {
                           
                         
                            sentence += " " + coma + DocumentEmbeddableSupplierViewReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdate(documentEmbeddableData) + "\n\n"
                    + "    public Bson toUpdate(" + documentEmbeddableData.getDocumentEmbeddableName() + " " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ") {\n"
                    + "       Bson update_ = Filters.empty();\n"
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
    
    
    
        // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdateList(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder toUpdateList(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierBuilderUtil.haveReferenced(documentEmbeddableFieldList);


            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
            String lower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());

        
      
            String cast = "";
            String getMethod = "";
            Integer count = 0;
//            String coma = "\\n \\\"";
            String coma = "\n ";
                String caracterComa=",";
            for (DocumentEmbeddableField entityField : documentEmbeddableFieldList) {
                 if((count + 1) == documentEmbeddableFieldList.size()){
                     caracterComa="";
                   
                 }
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + DocumentEmbeddableSupplierEmbeddedBuilder.toUpdate(documentEmbeddableData, entityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                             sentence += " " + coma + DocumentEmbeddableSupplierReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.TRUE);
                        } else {
                            
                            sentence += " " + coma+ DocumentEmbeddableSupplierReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                             sentence += " " + coma + DocumentEmbeddableSupplierViewReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.TRUE);
                        } else {
                            
                            sentence += " " + coma+ DocumentEmbeddableSupplierViewReferencedUtil.toUpdate(documentEmbeddableData, entityField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }


            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdateList(documentEmbeddableData) + "\n\n"
                    + "    public List<Bson> toUpdate(List<" + documentEmbeddableData.getDocumentEmbeddableName() + "> " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + "List) {\n"
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
