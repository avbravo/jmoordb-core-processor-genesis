/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.util;

import com.jmoordb.core.model.Pagination;
import static com.jmoordb.core.util.MessagesUtil.nameOfClass;
import static com.jmoordb.core.util.MessagesUtil.nameOfMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author avbravo
 */
public interface JmoordbCorePageUtil {
    // <editor-fold defaultstate="collapsed" desc="Pagination last(Pagination pagination)">
    public default Pagination last(Pagination pagination) {
        try {

          pagination.setPage(pagination.getSize());

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
        return pagination;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pagination first(Pagination pagination)">
    public default Pagination first(Pagination pagination) {
        try {

            pagination.setPage(1);

        
   
              } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
     
        }
        return pagination;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pagination next(Pagination pagination)">
    public default Pagination next(Pagination pagination) {
        try {

            if (pagination.getPage() < pagination.getSize()) {
                pagination.setPage(pagination.getPage() + 1);
            }

     
         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
        return pagination;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pagination back(Pagination pagination)">
    public default Pagination back(Pagination pagination) {
        try {

            if (pagination.getPage() > 1) {

                pagination.setPage(pagination.getPage() - 1);

            }

  
         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
       return pagination;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pagination skip(Pagination pagination)">
    /**
     * componentes <jmoordbjsf:pagination> toma el numero de pagina y lo mueve
     *
     * @param field
     * @param value
     * @return
     */
    public default Pagination skip(Pagination pagination) {
        try {

            pagination.setPage(pagination.getPage());

             

         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
       return pagination;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="move(Pagination pagination)">
    public default void move(Pagination pagination) {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Integer numberOfPages(Integer rows,Integer rowForPage)">
    default Integer numberOfPages(Integer rows, Integer rowForPage) {
        Integer numberOfPage = 1;
        try {

            if (rows > 0) {
                numberOfPage = rows / rowForPage;
                if ((rows % rowForPage) > 0) {
                    numberOfPage++;
                }
            }
         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
        return numberOfPage;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Integer> arrayListOfPage(Integer numberOfPage) ">
    /**
     * Devuele un array list en base al numero de paginas pasadaas
     *
     * @param rowsForPage
     * @param doc
     * @return
     */
    default public List<Integer> arrayListOfNumber(Integer numberOfPage) {
        List<Integer> pages = new ArrayList<>();
        try {

            pages = IntStream.range(1, numberOfPage + 1)
                    .boxed()
                    .collect(Collectors.toList());

            return pages;

         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
        return pages;
    }
    // </editor-fold>

    
   

    // <editor-fold defaultstate="collapsed" desc="Pagination loadPagination(Pagination pagination)">
    default public Pagination loadPagination(Pagination pagination) {
        return pagination;
    }

    // </editor-fold>

    
}
