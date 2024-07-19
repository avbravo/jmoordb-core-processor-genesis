package com.jmoordb.core.processor;

import com.jmoordb.core.annotation.ViewEntity;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.*;

import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.model.ViewEntityDataSupplier;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.viewentity.analizer.ViewEntityAnalizer;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierBuilder;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes(
        {"com.jmoordb.core.annotation.ViewEntity"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ViewEntityProcessor extends AbstractProcessor {

    private Messager messager;
    private ViewEntityDataSupplier viewEntityDataSupplier = new ViewEntityDataSupplier();

    // <editor-fold defaultstate="collapsed" desc=" boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)">
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            MessagesUtil.box("Iniciando proceso de analisis de @ViewEntity");

            if (annotations.size() == 0) {
                return false;
            }
            /**
             * Lee los elementos que tengan la anotacion @ViewEntity
             */
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ViewEntity.class);

            List<String> uniqueIdCheckList = new ArrayList<>();

            for (Element element : elements) {
                ViewEntity viewEntity = element.getAnnotation(ViewEntity.class);

                if (element.getKind() != ElementKind.CLASS) {

                    error("The annotation @ViewEntity can only be applied on class: ",
                            element);

                } else {
                    boolean error = false;
                    /**
                     * Obtener datos del viewEntity para ViewEntityData
                     */

                    ViewEntityData viewEntityData = viewEntityDataSupplier.get(ViewEntityData::new, element);
                    String nameOfViewEntity = "";


/**
 * Permitimos que multiples @ViewEntity hagan referencia a la misma coleccion
 */
//                    if (uniqueIdCheckList.contains(viewEntityData.getCollection())) {
//                        error("ViewEntity has should be uniquely defined", element);
//                        error = true;
//                    }

                    error = !checkIdValidity(viewEntityData.getCollection(), element);
                    if (!error) {
                        uniqueIdCheckList.add(viewEntityData.getCollection());
                        try {

                            builderClass(viewEntity, viewEntityData, element);

                        } catch (Exception e) {
                            error(e.getMessage(), null);
                        }
                    }
                }
            }
            MessagesUtil.box("Proceso de analisis finalizado");
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());

        }
        return false;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="builderClass(ViewEntity viewEntity, ViewEntityData viewEntityData,Element element, TypeMirror typeViewEntity)">
    private void builderClass(ViewEntity viewEntity, ViewEntityData viewEntityData, Element element)
            throws Exception {
        try {

            /**
             *
             */

            /**
             * List<ViewEntityField almacena la información de los atributos de los
             * viewEntity
             */
            List<ViewEntityField> viewEntityFieldList = new ArrayList<>();
            /**
             *
             * Procesa el contenido de la interface
             */
            ViewEntityAnalizer viewEntityAnalizer = ViewEntityAnalizer.get(element, messager, viewEntityData.getDatabase(), viewEntityFieldList, viewEntityData);


            /**
             * Construye la clase Supplier
             */
            ViewEntitySupplierBuilder viewEntitySupplierSourceBuilder = new ViewEntitySupplierBuilder();

            viewEntitySupplierSourceBuilder.init(viewEntity, viewEntityData, viewEntityFieldList, viewEntityData.getDatabase(), viewEntityData.getCollection(),element);

            /**
             * SupplierServices
             */
            /**
             * Crea el archivo
             */
            generateJavaFile(viewEntityData.getPackageOfViewEntity() + "." + viewEntityData.getEntityName() + "Supplier", viewEntitySupplierSourceBuilder.end());

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="generateJavaFile(String qfn, String end)">
    private void generateJavaFile(String qfn, String end) throws IOException {
        try {

            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(qfn);
            Writer writer = sourceFile.openWriter();
            writer.write(end);
            writer.close();

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="boolean checkIdValidity(String name, Element e)">
    /**
     * Checking if the class to be generated is a valid java identifier Also the
     * name should be not same as the target interface
     */
    private boolean checkIdValidity(String name, Element e) {
        boolean valid = true;
        try {

            for (int i = 0; i < name.length(); i++) {
                if (i == 0 ? !Character.isJavaIdentifierStart(name.charAt(i))
                        : !Character.isJavaIdentifierPart(name.charAt(i))) {
                    error("ViewEntity as should be valid java "
                            + "identifier for code generation: " + name, e);

                    valid = false;
                }
            }
            if (name.equals(ProcessorUtil.getTypeName(e))) {
                error("as should be different than the Interface name ", e);
            }
        } catch (Exception ex) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + ex.getLocalizedMessage());

        }
        // testMsg("valid = " + valid, false);
        return valid;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="error(String msg, Element e)">
    private void error(String msg, Element e) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="init(ProcessingEnvironment processingEnvironment)">
    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
    }
// </editor-fold>    
// <editor-fold defaultstate="collapsed" desc="printError(Element element, String message)">

    private void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
// </editor-fold>

}
