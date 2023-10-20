package com.sima.intranet.Service;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Interface.EmpleadoInterface;
import com.sima.intranet.Interface.ImportacionInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ImportacionServiceImpl implements ImportacionInterface {
    private Logger logger = LoggerFactory.getLogger(ImportacionServiceImpl.class);

    @Autowired
    private EmpleadoInterface empledoService;
    public static final List<String> FORMATO_BEJERMAN_NOMINA = List.of(
            "txtLegNum", "txtApeNom", "per_celular", "per_dom", "per_piso", "per_dpto", "per_torre", "per_sector",
            "per_escalera", "per_telef", "per_CP", "per_prov", "per_entrecalles", "per_mail", "per_loc", "per_nest",
            "per_codarea", "per_ttel", "per_tmail", "per_locms", "per_sexo", "per_fNac", "per_estc", "per_tDoc", "per_ndoc",
            "per_nac", "per_cuil", "lab_cat", "lab_sec", "lab_pto", "lab_car", "lab_cla", "lab_basico", "lab_Ese",
            "lab_fingT", "lab_FIng", "lab_FIngR", "lab_FEgrT", "lab_fEgr", "lab_TNov", "lab_Meg", "lab_SitR", "lab_cco",
            "lab_domexp", "lab_puesto", "lab_FTelRenun", "lab_ale", "lab_sitms", "lab_ltra", "lab_ncons", "lab_fdes", "lab_lpg",
            "liq_FPag", "liq_Bco", "liq_tcta", "liq_cta", "liq_Situacion", "liq_Condicion", "liq_Actividad", "liq_Mpr",
            "liq_FVenc", "liq_FDes", "liq_fHas", "liq_OSo", "liq_porad", "liq_sin", "liq_afjp", "liq_tme", "liq_sinies", "liq_obs"
    );

   /* public static final List<String> FORMATO_GLOBAL_LEGAJO_NOMINA = List.of(
            "Legajo", "Apellido", "Nombre/s", "Calle", "Número", "Piso", "Departamento",
            "Entre calle", "y calle", "Barrio", "Localidad", "Partido", "Provincia",
            "Código postal", "Teléfono", "Celular", "eMail", "Fecha de nacimiento",
            "Lugar de nacimiento", "Edad", "Sexo", "Estado civil", "Nacionalidad",
            "Documento de identidad", "Fecha de ingreso", "Ingreso anterior 1",
            "Sueldo básico", "Categoría", "Sucursal", "Sección", "Objetivo", "Zona de pago",
            "Supervisor", "C.U.I.L.", "Obra social"
    );*/

    public static final List<String> FORMATO_SUELDO_NOMINA = List.of(
            "Legajo", "Apellido", "Nombre/s", "C.U.I.L.", "Sucursal", "Sección", "Objetivo",
            "Zona de pago", "Supervisor", "Ingreso", "Baja", "Banco"
    );

    /*public static final List<String> FORMATO_GROUP_LEGAJO_NOMINA = List.of(
            "Legajo", "Apellido", "Nombre/s", "Calle", "Número", "Piso", "Departamento",
            "Entre calle", "y calle", "Barrio", "Localidad", "Partido", "Provincia",
            "Código postal", "Teléfono", "Celular", "eMail", "Fecha de nacimiento",
            "Lugar de nacimiento", "Edad", "Sexo", "Estado civil", "Nacionalidad",
            "Documento de identidad", "Fecha de ingreso", "Ingreso anterior 1",
            "Sueldo básico", "Categoría", "Sucursal", "Sección", "Objetivo", "C.U.I.L.",
            "Modalidad de contratación", "Situación", "Condición", "Actividad", "CCT",
            "Seguro de vida", "Obra social"
    );*/

    public static final List<String> FORMATO_LEGAJO_NOMINA = List.of(
            "Legajo", "Apellido", "Nombre/s", "Calle", "Número", "Piso", "Departamento",
            "Entre calle, y calle", "Barrio", "Localidad", "Partido", "Provincia",
            "Código postal", "Teléfono", "Celular", "eMail", "Fecha de nacimiento",
            "Lugar de nacimiento", "Edad", "Sexo", "Estado civil", "Nacionalidad",
            "Documento de identidad", "Fecha de ingreso", "Ingreso anterior 1",
            "Sueldo básico", "Categoría", "Sucursal", "Sección", "Objetivo", "Zona de pago",
            "Supervisor", "C.U.I.L.", "Modalidad de contratación", "Situación", "Condición",
            "Actividad", "Zona geográfica", "CCT", "Seguro de vida", "Obra social"
    );


    @Async
    public void procesarImportacionNomina(String ruta) {
        try {
            FileInputStream excelFile = new FileInputStream(ruta);
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheetAt(0);

            List<String> cabezera = new ArrayList<>();

            for (Cell cell : sheet.getRow(0)) {
                cabezera.add(cell.getStringCellValue());
            }
            System.out.println(cabezera);
            if (FORMATO_BEJERMAN_NOMINA.containsAll(cabezera)) {
                logger.info("Actualiacion de FORMATO BEGERMAN iniciada.");
                insertarFormatoBejerman(sheet);
            } else if (cabezera.containsAll(FORMATO_SUELDO_NOMINA)) {
                logger.info("Actualiacion de GLOBAL_SUELDO o GROUP SUELDO iniciada.");
                insertarFormatoSueldo(sheet);
            } else if(FORMATO_LEGAJO_NOMINA.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO LEGAJO");
                insertarFormatoLegajo(sheet);
            } else{
                logger.info("El formato de este excel no esta implementado.");
            }
            workbook.close();
            excelFile.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("Error al intentar realizar la importacion.");
        }

    }

  /*  private void insertarFormatoGroupLegajo(Sheet sheet) {
        List<Empleado> lista = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Empleado empleado = null;
            for (Cell cell : row) {

                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            empleado = empledoService.findByLegajo(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue())).orElse(new Empleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue())));
                            break;
                        case 1:
                            empleado.setApellidoEmpleado(cell.getStringCellValue());
                            break;
                        case 2:
                            empleado.setNombreEmpleado(cell.getStringCellValue());
                            break;
                        case 3:
                            empleado.setDireccionEmpleado(cell.getStringCellValue());
                            break;
                        case 4:
                            cell.setCellType(CellType.STRING);
                            empleado.setDireccionEmpleado(empleado.getDireccionEmpleado() + " " + cell.getStringCellValue());
                            break;
                        case 13:
                            cell.setCellType(CellType.STRING);
                            empleado.setCodigoPostalEmpleado(cell.getStringCellValue());
                            break;
                        case 15:
                            cell.setCellType(CellType.STRING);
                            empleado.setTelefonoEmpleado(cell.getStringCellValue());
                            break;
                        case 16:
                            empleado.setEmailEmpleado(cell.getStringCellValue());
                            break;
                        case 17:
                            empleado.setFechaNascimentoEmpleado(cell.getDateCellValue());
                            break;
                        case 23:
                            cell.setCellType(CellType.STRING);
                            if (cell.getStringCellValue() != null) {
                                empleado.setDNIEmpleado(cell.getStringCellValue());
                            }
                            break;
                        case 24:
                            empleado.setFechaAltaEmpleado(cell.getDateCellValue());
                            break;
                        case 26:
                            empleado.setSueldoTotal(new BigDecimal(cell.getNumericCellValue()));
                            break;
                        case 27:
                            empleado.setCargoEmpleado(cell.getStringCellValue());
                            break;
                        case 30:
                            empleado.setObjetivoEmpleado(cell.getStringCellValue());
                            break;

                        default:
                            //No me sirve el dato.
                    }

                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                }
            }
            if(empleado!=null){
                lista.add(empleado);
            }
        }
        empledoService.saveAll(lista);
    }
*/

    private void insertarFormatoSueldo(Sheet sheet) {
        List<Empleado> lista = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() >= 0 && row.getRowNum() <= 3) {
                continue;
            }
            Double sueldoTotal = Double.valueOf(0);
            Cell celdaDni = row.getCell(3);
            String dni = celdaDni.getStringCellValue().substring(3, 11);
            if(dni.isEmpty()){
                continue;
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni));
            for (Cell cell : row) {

                try {
                    if (cell.getColumnIndex() < 12) {
                        switch (cell.getColumnIndex()) {
                            case 0:
                                empleado = empledoService.findByLegajo(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue())).orElse(new Empleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue())));
                                break;
                            case 1:
                                empleado.setApellidoEmpleado(cell.getStringCellValue());
                                break;
                            case 2:
                                empleado.setNombreEmpleado(cell.getStringCellValue());
                                break;
                            case 3:
                                empleado.setDNIEmpleado(cell.getStringCellValue().substring(3, 11));
                                break;
                            case 4:

                                break;
                            case 9:
                                empleado.setFechaAltaEmpleado(cell.getDateCellValue());
                                break;
                            case 6:
                                empleado.setObjetivoEmpleado(cell.getStringCellValue());
                                break;
                            default:
                                //No me sirve el dato.
                        }
                    } else {
                        try {
                            sueldoTotal += cell.getNumericCellValue();
                        } catch (Exception e) {
                            logger.error("Dato invalido para sumar al sueldo total");
                        }

                    }

                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                }
            }
            if(empleado!=null){
                empleado.setSueldoTotal(new BigDecimal(sueldoTotal));
                lista.add(empleado);
            }

        }
        empledoService.saveAll(lista);

    }

    private void insertarFormatoLegajo(Sheet sheet) {
        List<Empleado> lista = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(24);
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue();
            if(dni.isEmpty()){
                continue;
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni));

            for (Cell cell : row) {

                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            empleado.setLegajoEmpleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue()));
                            break;
                        case 1:
                            empleado.setApellidoEmpleado(cell.getStringCellValue());
                            break;
                        case 2:
                            empleado.setNombreEmpleado(cell.getStringCellValue());
                            break;
                        case 3:
                            empleado.setDireccionEmpleado(cell.getStringCellValue());
                            break;
                        case 4:
                            cell.setCellType(CellType.STRING);
                            empleado.setDireccionEmpleado(empleado.getDireccionEmpleado() + " " + cell.getStringCellValue());
                            break;
                        case 13:
                            empleado.setCodigoPostalEmpleado(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 15:
                            cell.setCellType(CellType.STRING);
                            empleado.setTelefonoEmpleado(String.valueOf(cell.getStringCellValue()));
                            break;
                        case 17:
                            empleado.setFechaNascimentoEmpleado(cell.getDateCellValue());
                            break;
                        case 23:
                            cell.setCellType(CellType.STRING);
                            if (cell.getStringCellValue() != null) {
                                empleado.setDNIEmpleado(cell.getStringCellValue());
                            }
                            break;
                        case 24:
                            empleado.setFechaAltaEmpleado(cell.getDateCellValue());
                            break;
                        case 27:
                            empleado.setCargoEmpleado(cell.getStringCellValue());
                            break;
                        case 30:
                            empleado.setObjetivoEmpleado(cell.getStringCellValue());
                            break;
                        default:
                            //No me sirve el dato.
                    }
                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                }
            }
            if(empleado!=null){
                lista.add(empleado);
            }
        }
        empledoService.saveAll(lista);
    }

    private void insertarFormatoBejerman(Sheet sheet) {
        List<Empleado> lista = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(24);
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue();
            if(dni.isEmpty()){
                continue;
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni));

            for (Cell cell : row) {
                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            empleado.setLegajoEmpleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue()));
                            break;
                        case 1:
                            empleado.setNombreEmpleado(getNomnbreApellidoSplit(cell.getStringCellValue(), 1));
                            empleado.setApellidoEmpleado(getNomnbreApellidoSplit(cell.getStringCellValue(), 0));
                            break;
                        case 2:
                            cell.setCellType(CellType.STRING);
                            empleado.setTelefonoEmpleado(getStringSinEspacios(cell.getStringCellValue()));
                            break;
                        case 3:
                            empleado.setDireccionEmpleado(cell.getStringCellValue());
                            break;
                        case 10:
                            empleado.setCodigoPostalEmpleado(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 21:
                            empleado.setFechaNascimentoEmpleado(cell.getDateCellValue());
                            break;
                        case 24:
                            cell.setCellType(CellType.STRING);
                            empleado.setDNIEmpleado(cell.getStringCellValue());
                            break;
                        case 28:
                            empleado.setObjetivoEmpleado(cell.getStringCellValue());
                            break;
                        case 30:
                            empleado.setCargoEmpleado(cell.getStringCellValue());
                            break;
                        default:
                            //No me sirve el dato.
                    }
                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                }
            }
            if(empleado!=null){
                lista.add(empleado);
            }

        }
        empledoService.saveAll(lista);
    }

    /**
     * Para obetener nombre o apellido, el index puede ser 0(Apellido) o 1 (Nombre);
     *
     * @param completo
     * @param index
     * @return
     */
    private String getNomnbreApellidoSplit(String completo, Integer index) { //RIOS, ALAN GONZALO
        String[] parts = completo.split(",");
        String resultado = "";
        if (parts.length >= 2 && (index.equals(0) || index.equals(1))) {
            resultado = parts[index].trim().replace(",", "");
        }
        return resultado;
    }

    /**
     * Retornar el string sin espacion en todo el string.
     *
     * @param data
     * @return
     */
    private String getStringSinEspacios(String data) {
        return data.replace(" ", "");
    }

    /**
     * Retorna el valor de la celda detendiento de tipo de celda.
     *
     * @param cell
     * @return
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "";
        }
    }

    /**
     * Retorna el nombre de la celda del index indicado.
     *
     * @param sheet
     * @param columnIndex
     * @return
     */
    private static String getColumnName(Sheet sheet, int columnIndex) {
        Row headerRow = sheet.getRow(0);
        Cell headerCell = headerRow.getCell(columnIndex);
        return getCellValueAsString(headerCell);
    }
}
