/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmoordb.core.processor.model;

import com.jmoordb.core.annotation.Projection;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.function.Supplier;
import javax.lang.model.element.Element;

/**I
 *
 * @author avbravo
 */
public class ProjectionDataSupplier {
// <editor-fold defaultstate="collapsed" desc="ProjectionData get(Supplier<? extends ProjectionData> s, Element element)">

    public ProjectionData get(Supplier<? extends ProjectionData> s, Element element) {
        ProjectionData projectionData = s.get();
        try {

            Projection projection = element.getAnnotation(Projection.class);
    
            String database=   "{mongodb.database}";    
            if (projection.database() == null  || projection.database().equals("")) {
                database = "{mongodb.database}";
          
            } else {
     
                database = projection.database().replace("{", "").replace("}", "");
            }
            String collection ="";
    
           if (projection.collection() == null ||  projection.collection().trim().equals("")){
                    collection =JmoordbCoreUtil.letterToLower(ProcessorUtil.getTypeName(element));
                
            }else{
               collection = projection.collection();
           }
          
            projectionData = new ProjectionData.Builder()
                    .collection(collection)
                    .jakartaSource(projection.jakartaSource())
                    .database(database)                    
                    .packageOfProjection(ProcessorUtil.getPackageName(element))
                     .projectionName(ProcessorUtil.getTypeName(element))
                    .build();
            
            

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return projectionData;
    }
    // </editor-fold>

}
