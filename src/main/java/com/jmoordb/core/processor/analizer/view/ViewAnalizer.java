package com.jmoordb.core.processor.analizer.view;

import com.jmoordb.core.processor.analizer.*;
import com.jmoordb.core.annotation.date.ExcludeTime;
import com.jmoordb.core.annotation.date.IncludeTime;
import com.jmoordb.core.annotation.enumerations.CaseSensitive;
import com.jmoordb.core.annotation.enumerations.TypeOrder;
import com.jmoordb.core.annotation.enumerations.AnnotationType;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.view.VForm;
import com.jmoordb.core.util.ProcessorUtil;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import com.jmoordb.core.processor.fields.ParamTypeElement;
import com.jmoordb.core.processor.fields.WhereDescomposed;
import com.jmoordb.core.processor.methods.view.ViewMethod;

/**
 * Converts getters to field
 */
public class ViewAnalizer {
// <editor-fold defaultstate="collapsed" desc="fields()">

    private Messager messager;
    private StringBuilder builderMethods = new StringBuilder();

//    private final LinkedHashMap<String, String> fields;
//    private final List<String> mandatoryFields;
//    public ViewAnalizer(LinkedHashMap<String, String> fields, List<String> mandatoryFields) {
//
//        this.fields = fields;
//        this.mandatoryFields = mandatoryFields;
//    }
    // </editor-fold>
    public ViewAnalizer() {
    }

// <editor-fold defaultstate="collapsed" desc="set/get">
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="ViewAnalizer get(Element element, Messager messager, String database, TypeMirror typeEntity, List<ViewMethod> viewMethodList)">
    /**
     * Procesa los metodos definidos en la interface
     *
     * @param element
     * @return
     */
       public static ViewAnalizer get(Element element, Messager messager, Boolean generate, TypeMirror typeEntity, List<ViewMethod> viewMethodList) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        List<String> mandatoryFields = new ArrayList<>();

        for (ExecutableElement executableElement : ElementFilter.methodsIn(element.getEnclosedElements())) {
            //metodo que almacena la información del repositorio

            if (executableElement.getKind() != ElementKind.METHOD) {
                continue;
            }
            /**
             * Obtiene el nombre del metodo
             */

            String methodName = ProcessorUtil.nameOfMethod(executableElement);
            //Parametros
            List<ParamTypeElement> paramTypeElementList = new ArrayList<>();
            List<String> tokenWhere = new ArrayList<>();

            List<String> lexemas = new ArrayList<>();
            List<String> worldAndToken = new ArrayList<>();
            /**
             * Definición inicial
             */
            ViewMethod viewMethod = new ViewMethod.Builder()
                    .nameOfMethod(methodName)
                    .caseSensitive(CaseSensitive.NONE)
                    .annotationType(AnnotationType.NONE)
                    .paramTypeElement(paramTypeElementList)
                    .returnType(ReturnType.NONE)
                    .returnTypeValue("")
                    .typeOrder(TypeOrder.NONE)
                    .where("")
                    .tokenWhere(tokenWhere)
                    .whereDescomposed(new WhereDescomposed())
                    .havePagination(Boolean.FALSE)
                    .haveSorted(Boolean.FALSE)
                    .nameOfParametersPagination("")
                    .nameOfParametersSorted("")
                    .lexemas(lexemas)
                    .worldAndToken(worldAndToken)
                    .build();

            if (methodName.equals("findByPKOfEntity")) {
                messager.printMessage(Diagnostic.Kind.ERROR, "The method name findByPKOfEntity() is reserved for internal framework use, please rename your method.", element);
            }
            /**
             * Obtengo el valor de retorno convierte a ReturnType.
             */
            TypeMirror returnTypeOfMethod = executableElement.getReturnType();

            viewMethod.setReturnTypeValue(returnTypeOfMethod.toString());
            viewMethod.setReturnType(ProcessorUtil.convertToReturnType(returnTypeOfMethod.toString(), typeEntity));

            List<? extends VariableElement> parameters = executableElement.getParameters();
            if (parameters.size() <= 0) {

            } else {
                /**
                 * Se cargan los parámetros del metodo.
                 */
                Boolean isIncludeTime = Boolean.FALSE;
                Boolean isExcludeTime = Boolean.FALSE;

                for (int i = 0; i < parameters.size(); i++) {

                    VariableElement param = parameters.get(i);

                    TypeMirror secondArgumentType = param.asType();
                    

                    if (parameters.get(i).getAnnotation(IncludeTime.class) != null) {
                    
                        isIncludeTime = Boolean.TRUE;
                    } else {

                        if (parameters.get(i).getAnnotation(ExcludeTime.class) != null) {
                    
                            isExcludeTime = Boolean.TRUE;
                        } else {
                            isIncludeTime = Boolean.FALSE;
                            isExcludeTime = Boolean.FALSE;
                        }
                    }
                   
                    ParamTypeElement paramTypeElement = new ParamTypeElement.Builder()
                            .type(param.asType().toString())
                            .name(param.getSimpleName().toString())
                            .isExcludeTime(isExcludeTime)
                            .isIncludeTime(isIncludeTime)
                            .build();

                    paramTypeElementList.add(paramTypeElement);

                }
                viewMethod.setParamTypeElement(paramTypeElementList);

            }

            /*
             *
             * Verifica si es una anotación valida
             *
             */
            if (!ProcessorUtil.isValidAnnotationOfView(executableElement)) {
                // No tiene anotaciones validas
                messager.printMessage(Diagnostic.Kind.ERROR, "Methods " + methodName + " without declaring valid annotation for a View interface", element);

                return new ViewAnalizer();
            }

            /**
             * Verifico si el metodo tiene anotación VForm.classs E invoco el
             * analizador de la anotación
             */
           VForm vForm = executableElement.getAnnotation(VForm.class);
            if (vForm != null) {

                if (!VFormAnalizer.analizer(vForm, element, executableElement, typeEntity, viewMethod)) {
                    messager.printMessage(Diagnostic.Kind.ERROR, VFormAnalizer.getMessage(), element);
                }
                viewMethod.setAnnotationType(AnnotationType.QUERY);

            }
           
           
          

            /**
             * Almacena la informacion del view
             */
            viewMethodList.add(viewMethod);

            /**
             * Falta aqui verificar cuando un metodo no tiene anotaciones enviar
             * un mensaje de error
             *
             */
        }

//        return new ViewAnalizer(fields, mandatoryFields);
        return new ViewAnalizer();
    }
// </editor-fold>

}
